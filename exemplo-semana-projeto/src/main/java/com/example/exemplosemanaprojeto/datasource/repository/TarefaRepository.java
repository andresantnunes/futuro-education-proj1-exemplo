package com.example.exemplosemanaprojeto.datasource.repository;

import com.example.exemplosemanaprojeto.datasource.entity.TarefaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<TarefaEntity, Long> {

    @Query(
            " select t from TarefaEntity t " +
            " where t.usuario.id = :id"
    )
    List<TarefaEntity> findAllByUsuarioId(@Param("id") Long id);
    // As List não precisam de Optional,
    // pois se não houver itens compatíveis com o where a lista virá vazia
}
