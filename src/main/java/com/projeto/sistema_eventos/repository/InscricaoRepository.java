package com.projeto.sistema_eventos.repository;

import com.projeto.sistema_eventos.entity.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscricaoRepository
        extends JpaRepository<Inscricao, Long> {

    boolean existsByUsuario_IdAndEvento_Id(
            Long usuarioId,
            Long eventoId
    );

    boolean existsByUsuario_IdAndEvento_IdAndIdNot(
            Long usuarioId,
            Long eventoId,
            Long id
    );

    boolean existsByUsuario_Id(Long usuarioId);

    boolean existsByEvento_Id(Long eventoId);
}