package com.projeto.sistema_eventos.repository;

import com.projeto.sistema_eventos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}