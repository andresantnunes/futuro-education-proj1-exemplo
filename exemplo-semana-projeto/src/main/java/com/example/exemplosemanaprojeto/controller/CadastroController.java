package com.example.exemplosemanaprojeto.controller;

import com.example.exemplosemanaprojeto.controller.dto.request.InserirUsuarioRequest;
import com.example.exemplosemanaprojeto.datasource.entity.UsuarioEntity;
import com.example.exemplosemanaprojeto.datasource.repository.PerfilRepository;
import com.example.exemplosemanaprojeto.datasource.repository.UsuarioRepository;
import com.example.exemplosemanaprojeto.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CadastroController {
    private final UsuarioService usuarioService;

    @PostMapping("/cadastro")
    public ResponseEntity<String> novoUsuario(
            @RequestBody InserirUsuarioRequest inserirUsuarioRequest
    ) {

        usuarioService.cadastraNovoUsuario(inserirUsuarioRequest);

        return ResponseEntity.ok("Usuario Salvo!");
    }

}
