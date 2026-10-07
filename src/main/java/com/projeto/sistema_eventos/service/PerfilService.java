package com.projeto.sistema_eventos.service;

import com.projeto.sistema_eventos.entity.Perfil;
import com.projeto.sistema_eventos.repository.PerfilRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public PerfilService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    public List<Perfil> listarTodos() {
        return perfilRepository.findAll();
    }

    public Perfil buscarPorId(Long id) {
        return perfilRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Perfil Não Foi Localizado!"));
    }

    public Perfil salvar(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    public Perfil atualizar(Long id, Perfil perfil) {
        Perfil perfilExistente = buscarPorId(id);

        perfilExistente.setNome(perfil.getNome());
        perfilExistente.setDescricao(perfil.getDescricao());

        return perfilRepository.save(perfilExistente);
    }

    public void deletar(Long id) {
        perfilRepository.deleteById(id);
    }
}