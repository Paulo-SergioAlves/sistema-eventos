package com.projeto.sistema_eventos.controller;

import com.projeto.sistema_eventos.entity.Perfil;
import com.projeto.sistema_eventos.service.PerfilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @GetMapping
    public List<Perfil> listarTodos() {
        return perfilService.listarTodos();
    }

    @GetMapping("/{id}")
    public Perfil buscarPorId(@PathVariable Long id) {
        return perfilService.buscarPorId(id);
    }

    @PostMapping
    public Perfil criar(@RequestBody Perfil perfil) {
        return perfilService.salvar(perfil);
    }

    @PutMapping("/{id}")
    public Perfil atualizar(
            @PathVariable Long id,
            @RequestBody Perfil perfil) {

        return perfilService.atualizar(id, perfil);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        perfilService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}