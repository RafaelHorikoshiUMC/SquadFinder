package com.pfc.thindesk.service;

import com.pfc.thindesk.entity.Perfil;
import com.pfc.thindesk.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfilService {
    @Autowired
    public PerfilRepository perfilRepository;

    // Cria uma novo perfil
    public Perfil criarPerfil(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    // Busca todos os perfis
    public List<Perfil> listarTodosPerfis() {
        return perfilRepository.findAll();
    }

    // Busca o perfil por ID
    public Optional<Perfil> buscarPerfilPorId(String id) {
        return perfilRepository.findById(id);
    }

    // Atualizar um perfil
    public Perfil atualizarPerfil(String id, Perfil perfil) {
        if (perfilRepository.existsById(id)) {
            perfil.setId(id);
            return perfilRepository.save(perfil);
        } else {
            return null;
        }
    }

    // Deleta um perfil
    public void deletarPerfil(String id) {
        perfilRepository.deleteById(id);
    }
}
