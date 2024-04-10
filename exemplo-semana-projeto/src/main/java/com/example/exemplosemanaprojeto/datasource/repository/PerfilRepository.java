package com.example.exemplosemanaprojeto.datasource.repository;

import com.example.exemplosemanaprojeto.datasource.entity.PerfilEntity;
import com.example.exemplosemanaprojeto.datasource.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<PerfilEntity, Long> {
    Optional<PerfilEntity> findByNome(String nome); // o JPA vai criar uma query
}
