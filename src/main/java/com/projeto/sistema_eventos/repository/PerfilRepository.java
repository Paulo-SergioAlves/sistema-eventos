package com.projeto.sistema_eventos.repository;

import com.projeto.sistema_eventos.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}