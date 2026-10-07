package com.projeto.sistema_eventos.controller;

import com.projeto.sistema_eventos.entity.Evento;
import com.projeto.sistema_eventos.service.EventoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public List<Evento> listarTodos() {
        return eventoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Evento buscarPorId(@PathVariable Long id) {
        return eventoService.buscarPorId(id);
    }

    @PostMapping
    public Evento criar(@RequestBody Evento evento) {
        return eventoService.salvar(evento);
    }

    @PutMapping("/{id}")
    public Evento atualizar(
            @PathVariable Long id,
            @RequestBody Evento evento) {

        return eventoService.atualizar(id, evento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}