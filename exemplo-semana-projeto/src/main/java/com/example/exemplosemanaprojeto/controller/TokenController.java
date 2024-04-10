package com.example.exemplosemanaprojeto.controller;

import com.example.exemplosemanaprojeto.controller.dto.request.LoginRequest;
import com.example.exemplosemanaprojeto.controller.dto.response.LoginResponse;
import com.example.exemplosemanaprojeto.datasource.entity.UsuarioEntity;
import com.example.exemplosemanaprojeto.datasource.repository.UsuarioRepository;
import com.example.exemplosemanaprojeto.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TokenController {

    private final TokenService tokenService;
    private static long TEMPO_EXPIRACAO = 36000L; //contante de tempo de expiração em segundos

    @PostMapping("/login") //post para gerar o token
    public ResponseEntity<LoginResponse> gerarToken(
            @RequestBody LoginRequest loginRequest
    ){

        LoginResponse response = tokenService.gerarToken(loginRequest);

        return ResponseEntity.ok( // Objeto usado para criar um corpo de resposta
                response // corpo de resposta é um objeto de LoginResponse
        );

    }

}
