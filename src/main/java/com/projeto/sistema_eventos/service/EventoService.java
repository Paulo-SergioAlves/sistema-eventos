package com.projeto.sistema_eventos.service;

import com.projeto.sistema_eventos.entity.Categoria;
import com.projeto.sistema_eventos.entity.Evento;
import com.projeto.sistema_eventos.repository.EventoRepository;
import com.projeto.sistema_eventos.repository.InscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final CategoriaService categoriaService;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    public EventoService(
            EventoRepository eventoRepository,
            CategoriaService categoriaService) {

        this.eventoRepository = eventoRepository;
        this.categoriaService = categoriaService;
    }

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public Evento buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Evento não encontrado"));
    }

    private Categoria buscarCategoria(Evento evento) {
        if (evento.getCategoria() == null
                || evento.getCategoria().getId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe o ID da categoria");
        }

        return categoriaService.buscarPorId(
                evento.getCategoria().getId());
    }

    public Evento salvar(Evento evento) {
        Categoria categoria = buscarCategoria(evento);

        evento.setId(null);
        evento.setCategoria(categoria);

        return eventoRepository.save(evento);
    }

    public Evento atualizar(Long id, Evento evento) {
        Evento eventoExistente = buscarPorId(id);
        Categoria categoria = buscarCategoria(evento);

        eventoExistente.setNome(evento.getNome());
        eventoExistente.setDescricao(evento.getDescricao());
        eventoExistente.setCategoria(categoria);

        return eventoRepository.save(eventoExistente);
    }

    public void deletar(Long id) {
        Evento evento = buscarPorId(id);

        if (inscricaoRepository.existsByEvento_Id(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Remova as inscrições antes de excluir o evento");
        }

        eventoRepository.delete(evento);
    }
}