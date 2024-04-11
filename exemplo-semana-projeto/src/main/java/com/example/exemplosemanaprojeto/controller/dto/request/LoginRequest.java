package com.example.exemplosemanaprojeto.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LoginRequest (
        String nomeUsuario,
        String senha,
        @JsonFormat(pattern = "dd/MM/yyyy") //formatar a partir do Json
        LocalDate localDate,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") //formatar a partir do Json
        LocalDateTime localDateTime
){
}
