package com.projeto.sistema_eventos.service;

import com.projeto.sistema_eventos.entity.Perfil;
import com.projeto.sistema_eventos.entity.Usuario;
import com.projeto.sistema_eventos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilService perfilService;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"));
    }

    private Perfil buscarPerfil(Usuario usuario) {
        if (usuario.getPerfil() == null
                || usuario.getPerfil().getId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe o ID do perfil");
        }

        return perfilService.buscarPorId(
                usuario.getPerfil().getId());
    }

    public Usuario salvar(Usuario usuario) {
        Perfil perfil = buscarPerfil(usuario);

        usuario.setId(null);
        usuario.setPerfil(perfil);

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        Usuario usuarioExistente = buscarPorId(id);
        Perfil perfil = buscarPerfil(usuario);

        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setSenha(usuario.getSenha());
        usuarioExistente.setPerfil(perfil);

        return usuarioRepository.save(usuarioExistente);
    }

    public void deletar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }
}