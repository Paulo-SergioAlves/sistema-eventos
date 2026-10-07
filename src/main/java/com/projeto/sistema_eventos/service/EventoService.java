package com.projeto.sistema_eventos.service;

import com.projeto.sistema_eventos.entity.Categoria;
import com.projeto.sistema_eventos.entity.Evento;
import com.projeto.sistema_eventos.repository.EventoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final CategoriaService categoriaService;

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
                        HttpStatus.NOT_FOUND, "Evento não encontrado"));
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
        eventoRepository.delete(evento);
    }
}