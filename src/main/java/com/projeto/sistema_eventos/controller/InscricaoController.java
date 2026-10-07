package com.projeto.sistema_eventos.controller;

import com.projeto.sistema_eventos.entity.Inscricao;
import com.projeto.sistema_eventos.service.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {

    @Autowired
    private InscricaoService inscricaoService;

    @PostMapping
    public Inscricao cadastrarInscricao(
            @RequestBody Inscricao inscricao) {

        return inscricaoService.cadastrarInscricao(inscricao);
    }

    @GetMapping
    public List<Inscricao> listarInscricoes() {
        return inscricaoService.listarInscricoes();
    }

    @GetMapping("/{id}")
    public Inscricao buscarInscricao(@PathVariable Long id) {
        return inscricaoService.buscarInscricao(id);
    }

    @PutMapping("/{id}")
    public Inscricao atualizarInscricao(
            @PathVariable Long id,
            @RequestBody Inscricao inscricao) {

        return inscricaoService.atualizarInscricao(id, inscricao);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarInscricao(@PathVariable Long id) {
        inscricaoService.deletarInscricao(id);
    }
}