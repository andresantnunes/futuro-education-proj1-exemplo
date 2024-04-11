package com.example.exemplosemanaprojeto.controller.dto.request;

import org.springframework.validation.annotation.Validated;

public record InserirTarefaRequest(
        String titulo,
        String descricao
) {
}
