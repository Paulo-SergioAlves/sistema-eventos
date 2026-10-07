package com.projeto.sistema_eventos.repository;

import com.projeto.sistema_eventos.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}