package com.example.exemplosemanaprojeto.controller;

import com.example.exemplosemanaprojeto.controller.dto.request.InserirTarefaRequest;
import com.example.exemplosemanaprojeto.controller.dto.response.TarefaResponse;
import com.example.exemplosemanaprojeto.service.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    @GetMapping
    public ResponseEntity<List<TarefaResponse>> retornarTarefas(
            @RequestHeader(name = "Authorization") String token // Bearer ahsdjkahkjdahjksd
    ){
        return ResponseEntity.ok(
                tarefaService.retornaTarefas(token.substring(7)// remove o Bearer do token
                )
        );
    }

    @PostMapping
    public ResponseEntity<TarefaResponse> retornarTarefas(
            @RequestHeader(name = "Authorization") String token, // Bearer ahsdjkahkjdahjksd
            @RequestBody InserirTarefaRequest incluiTarefaRequest
    ){
        return ResponseEntity.ok(tarefaService.salvaTarefa(incluiTarefaRequest,token.substring(7)));
    }
}
