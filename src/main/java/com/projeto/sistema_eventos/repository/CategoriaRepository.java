package com.projeto.sistema_eventos.repository;

import com.projeto.sistema_eventos.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}