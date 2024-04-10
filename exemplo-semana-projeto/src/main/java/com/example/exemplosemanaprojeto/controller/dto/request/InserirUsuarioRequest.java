package com.example.exemplosemanaprojeto.controller.dto.request;

public record InserirUsuarioRequest(
        String nomeUsuario,
        String senha,
        String nomePerfil
) {
}
