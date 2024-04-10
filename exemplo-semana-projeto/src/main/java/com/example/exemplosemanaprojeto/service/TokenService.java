package com.example.exemplosemanaprojeto.service;

import com.example.exemplosemanaprojeto.controller.dto.request.LoginRequest;
import com.example.exemplosemanaprojeto.controller.dto.response.LoginResponse;
import com.example.exemplosemanaprojeto.datasource.entity.UsuarioEntity;
import com.example.exemplosemanaprojeto.datasource.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final BCryptPasswordEncoder bCryptEncoder; // decifrar senhas
    private final JwtEncoder jwtEncoder; // codificar um JWT
    private final JwtDecoder jwtDencoder; // decofica um JWT
    private final UsuarioRepository usuarioRepository;

    private static long TEMPO_EXPIRACAO = 36000L; //contante de tempo de expiração em segundos

    public LoginResponse gerarToken(
            @RequestBody LoginRequest loginRequest
    ){

        UsuarioEntity usuarioEntity = usuarioRepository
                .findByNomeUsuario(loginRequest.nomeUsuario()) // busca dados de usuario por nomeUsuario
                .orElseThrow(                                  // caso usuario não exista gera um erro
                        ()->{
                            log.error("Erro, usuário não existe");
                            return new RuntimeException("Erro, usuário não existe");
                        }
                );

        if(!usuarioEntity.senhaValida(loginRequest, bCryptEncoder)){ // valida se a senha recebida é a mesma que foi salva com BCrypt
            log.error("Erro, senha incorreta");                      // caso senha não bata, gera um erro
            throw new RuntimeException("Erro, senha incorreta");
        }

        Instant now = Instant.now();

        String scope = usuarioEntity.getPerfil().getNome();

        JwtClaimsSet claims = JwtClaimsSet.builder() // Conjunto de campos do JWT, incluindo os campos pré-definidos e campos customizados
                .issuer("projeto1") // autor do token
                .issuedAt(now)      // momento da criação do token
                .expiresAt(now.plusSeconds(TEMPO_EXPIRACAO)) // tempo de expiração
                .subject(usuarioEntity.getId().toString())   // sujeito do token ou dono do token
                .claim("scope", scope) // campo customizado, chamado scope que será adicionado ao token, alem dos campos anteriores
                .build(); // constroi o Objeto de JwtClaimsSet

        var valorJWT = jwtEncoder.encode(
                        JwtEncoderParameters.from(claims) // parametros para encode do token
                ) // token foi criado, porém está em uma classe que não tem o token puro, ele o token e várias coisas a mais
                .getTokenValue(); // pegar o valor do token em si

        return new LoginResponse(valorJWT, TEMPO_EXPIRACAO); // corpo de resposta é um objeto de LoginResponse


    }


    public String buscaCampo(String token, String claim) {
        return jwtDencoder
                .decode(token) // decifra o token
                .getClaims() // busca um campo especifico do token
                .get(claim)    // definindo o campo a ser buscado
                .toString(); // transforma a resposta em string
    }
}
