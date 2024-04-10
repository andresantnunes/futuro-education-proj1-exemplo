package com.example.exemplosemanaprojeto.datasource.repository;

import com.example.exemplosemanaprojeto.datasource.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByNomeUsuario(String nomeUsuario); // o JPA vai criar uma query
    // select * from usuario where nomeUsuario = :nomeUsuario
}
