package com.example.exemplosemanaprojeto.datasource.entity;

import com.example.exemplosemanaprojeto.controller.dto.request.LoginRequest;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@Entity
@Table(name="usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) //nome de usuário deve ser único
    private String nomeUsuario;
    private String senha;

    @ManyToOne
    private PerfilEntity perfil;

    //valida logins com a senha encriptada salva no banco
    public boolean senhaValida(
            LoginRequest loginRequest,
            BCryptPasswordEncoder bCryptEncoder
    ) {
        return bCryptEncoder.matches(
                loginRequest.senha(),
                this.senha
        );
    }
}
