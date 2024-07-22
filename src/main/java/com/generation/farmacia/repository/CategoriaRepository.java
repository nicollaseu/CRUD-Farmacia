package com.generation.farmacia.repository;

import com.generation.farmacia.model.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {

    List<CategoriaModel> findAllByNomeContainingIgnoreCase(@Param("tipo") String nome);
}
