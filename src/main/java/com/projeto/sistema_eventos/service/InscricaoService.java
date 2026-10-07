package com.projeto.sistema_eventos.service;

import com.projeto.sistema_eventos.entity.Evento;
import com.projeto.sistema_eventos.entity.Inscricao;
import com.projeto.sistema_eventos.entity.Usuario;
import com.projeto.sistema_eventos.repository.EventoRepository;
import com.projeto.sistema_eventos.repository.InscricaoRepository;
import com.projeto.sistema_eventos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public List<Inscricao> listarInscricoes() {
        return inscricaoRepository.findAll();
    }

    public Inscricao buscarInscricao(Long id) {
        return inscricaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Inscrição não encontrada"));
    }

    private Usuario buscarUsuario(Inscricao inscricao) {
        if (inscricao.getUsuario() == null
                || inscricao.getUsuario().getId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe o ID do usuário");
        }

        return usuarioRepository.findById(
                        inscricao.getUsuario().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"));
    }

    private Evento buscarEvento(Inscricao inscricao) {
        if (inscricao.getEvento() == null
                || inscricao.getEvento().getId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe o ID do evento");
        }

        return eventoRepository.findById(
                        inscricao.getEvento().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Evento não encontrado"));
    }

    public Inscricao cadastrarInscricao(Inscricao inscricao) {
        Usuario usuario = buscarUsuario(inscricao);
        Evento evento = buscarEvento(inscricao);

        boolean jaExiste =
                inscricaoRepository.existsByUsuario_IdAndEvento_Id(
                        usuario.getId(), evento.getId());

        if (jaExiste) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Usuário já inscrito neste evento");
        }

        inscricao.setId(null);
        inscricao.setUsuario(usuario);
        inscricao.setEvento(evento);
        inscricao.setDataInscricao(LocalDateTime.now());
        inscricao.setStatus("CONFIRMADA");

        return inscricaoRepository.save(inscricao);
    }

    public Inscricao atualizarInscricao(
            Long id,
            Inscricao inscricao) {

        Inscricao inscricaoExistente = buscarInscricao(id);

        Usuario usuario = buscarUsuario(inscricao);
        Evento evento = buscarEvento(inscricao);

        String status = inscricao.getStatus();

        if (!"CONFIRMADA".equals(status)
                && !"CANCELADA".equals(status)) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Status deve ser CONFIRMADA ou CANCELADA");
        }

        boolean jaExiste =
                inscricaoRepository
                        .existsByUsuario_IdAndEvento_IdAndIdNot(
                                usuario.getId(),
                                evento.getId(),
                                id);

        if (jaExiste) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Usuário já possui outra inscrição neste evento");
        }

        inscricaoExistente.setUsuario(usuario);
        inscricaoExistente.setEvento(evento);
        inscricaoExistente.setStatus(status);

        return inscricaoRepository.save(inscricaoExistente);
    }

    public void deletarInscricao(Long id) {
        Inscricao inscricao = buscarInscricao(id);
        inscricaoRepository.delete(inscricao);
    }
}