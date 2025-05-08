package com.pfc.thindesk.service;

import com.pfc.thindesk.entity.Grupo;
import com.pfc.thindesk.entity.Jogo;
import com.pfc.thindesk.repository.GrupoRepository;
import com.pfc.thindesk.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    // Cria um novo grupo
    public Grupo criarGrupo(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    // Busca todos os grupos
    public List<Grupo> listarTodosGrupos() {
        return grupoRepository.findAll();
    }

    // Busca o grupo por ID
    public Optional<Grupo> buscarGrupoPorId(String id) {
        return grupoRepository.findById(id);
    }

    // Atualizar um grupo
    public Grupo atualizarGrupo(String id, Grupo grupo) {
        if (grupoRepository.existsById(id)) {
            grupo.setId(id);
            return grupoRepository.save(grupo);
        } else {
            return null;
        }
    }

    // Deleta um grupo
    public void deletarGrupo(String id) {
        grupoRepository.deleteById(id);
    }
}
