package com.projeto.sistema_eventos.service;

import com.projeto.sistema_eventos.entity.Perfil;
import com.projeto.sistema_eventos.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    public List<Perfil> listarTodos() {
        return perfilRepository.findAll();
    }

    public Perfil buscarPorId(Long id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Perfil não encontrado"));
    }

    public Perfil salvar(Perfil perfil) {
        perfil.setId(null);
        return perfilRepository.save(perfil);
    }

    public Perfil atualizar(Long id, Perfil perfil) {
        Perfil perfilExistente = buscarPorId(id);

        perfilExistente.setNome(perfil.getNome());
        perfilExistente.setDescricao(perfil.getDescricao());

        return perfilRepository.save(perfilExistente);
    }

    public void deletar(Long id) {
        Perfil perfil = buscarPorId(id);
        perfilRepository.delete(perfil);
    }
}