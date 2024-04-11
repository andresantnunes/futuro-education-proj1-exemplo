package com.example.exemplosemanaprojeto.service;


import com.example.exemplosemanaprojeto.controller.dto.request.InserirTarefaRequest;
import com.example.exemplosemanaprojeto.controller.dto.response.TarefaResponse;
import com.example.exemplosemanaprojeto.datasource.entity.TarefaEntity;
import com.example.exemplosemanaprojeto.datasource.entity.UsuarioEntity;
import com.example.exemplosemanaprojeto.datasource.repository.TarefaRepository;
import com.example.exemplosemanaprojeto.datasource.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;


    public List<TarefaResponse> retornaTarefas(String token) {

        Long usuarioId = Long.valueOf( // tranforma o valor do campo "sub" em Long
                tokenService.buscaCampo(token, "sub")
        );

        LocalDate.now();

        List<TarefaEntity> listaTarefas = tarefaRepository.findAllByUsuarioId(usuarioId);

        return listaTarefas.stream().map( // mapear a lista de TarefaEntity para uma lista de TarefaRespone
                // para cada Item da lista TarefaEntity será criada uma nova TarefaResponse
                t -> new TarefaResponse(t.getId(), t.getTitulo(), t.getDescricao(), t.getFinalizada())
        ).toList(); // cria uma lista de TarefaResponse


    }

    public TarefaResponse salvaTarefa(
            InserirTarefaRequest incluiTarefaRequest,
            String token
    ) {

        Long usuarioId = Long.valueOf( // tranforma o valor do campo "sub" em Long
                tokenService.buscaCampo(token, "sub")
        );

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        TarefaEntity tarefaEntity = new TarefaEntity();
        tarefaEntity.setUsuario(usuario);
        tarefaEntity.setTitulo(incluiTarefaRequest.titulo());
        tarefaEntity.setDescricao(incluiTarefaRequest.descricao());
        tarefaEntity.setFinalizada(false);

        TarefaEntity tarefaSalva = tarefaRepository.save(tarefaEntity);


        return new TarefaResponse(tarefaSalva.getId(),
                tarefaSalva.getTitulo(),
                tarefaSalva.getDescricao(),
                tarefaSalva.getFinalizada()
        );
    }
}
