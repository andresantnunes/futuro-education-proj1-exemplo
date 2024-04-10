package com.example.exemplosemanaprojeto.service;

import com.example.exemplosemanaprojeto.controller.dto.request.InserirUsuarioRequest;
import com.example.exemplosemanaprojeto.datasource.entity.UsuarioEntity;
import com.example.exemplosemanaprojeto.datasource.repository.PerfilRepository;
import com.example.exemplosemanaprojeto.datasource.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final BCryptPasswordEncoder bCryptEncoder;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;

    public void cadastraNovoUsuario(
            @RequestBody InserirUsuarioRequest inserirUsuarioRequest
    ) {
        boolean usuarioExsite = usuarioRepository
                .findByNomeUsuario(inserirUsuarioRequest.nomeUsuario())
                .isPresent(); // retorna um true se a entidade procurada existir, caso o contrário, retorna false

        if (usuarioExsite) {
            throw new RuntimeException("Usuario já existe");
        }

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNomeUsuario(inserirUsuarioRequest.nomeUsuario());
        usuario.setSenha(
                bCryptEncoder.encode(inserirUsuarioRequest.senha()) // codificar a senha
        );
        usuario.setPerfil(
                perfilRepository.findByNome(inserirUsuarioRequest.nomePerfil())
                        .orElseThrow(()-> new RuntimeException("Perfil inválido ou inexistente"))
        );

        usuarioRepository.save(usuario);
    }
}
