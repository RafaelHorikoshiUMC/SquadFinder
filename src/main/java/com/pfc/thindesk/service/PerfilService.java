package com.pfc.thindesk.service;

import com.pfc.thindesk.PerfilMatchDTO;
import com.pfc.thindesk.entity.Perfil;
import com.pfc.thindesk.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    public List<PerfilMatchDTO> buscarPerfisCompatíveis(Perfil perfilAtual) {
        List<Perfil> todosPerfis = perfilRepository.findAll(); // Busca todos os perfis do sistema
        List<PerfilMatchDTO> resultados = new ArrayList<>();    // Lista de resultados com score

        for (Perfil outroPerfil : todosPerfis) {
            if (!outroPerfil.getId().equals(perfilAtual.getId())) { // Evita comparar com ele mesmo
                int atributosEmComum = 0;
                int totalAtributos = 7;

                // Compara os atributos principais (ignorando maiúsculas/minúsculas)
                if (camposIguais(perfilAtual.getGeneroPreferidoPrincipal(), outroPerfil.getGeneroPreferidoPrincipal()))
                    atributosEmComum++;
                if (camposIguais(perfilAtual.getGeneroPreferidoSecundario(), outroPerfil.getGeneroPreferidoSecundario()))
                    atributosEmComum++;
                if (camposIguais(perfilAtual.getPlataforma(), outroPerfil.getPlataforma()))
                    atributosEmComum++;
                if (camposIguais(perfilAtual.getComunicacao(), outroPerfil.getComunicacao()))
                    atributosEmComum++;
                if (camposIguais(perfilAtual.getPeriodoOnline(), outroPerfil.getPeriodoOnline()))
                    atributosEmComum++;
                if (camposIguais(perfilAtual.getEstiloDeJogo(), outroPerfil.getEstiloDeJogo()))
                    atributosEmComum++;
                if (camposIguais(perfilAtual.getEstadoCivil(), outroPerfil.getEstadoCivil()))
                    atributosEmComum++;

                // Calcula a porcentagem de compatibilidade
                int porcentagem = (int) ((atributosEmComum / (double) totalAtributos) * 100);

                // Adiciona o perfil com seu score na lista de resultados
                resultados.add(new PerfilMatchDTO(outroPerfil, porcentagem));
            }
        }

        // Retorna os perfis ordenados por maior porcentagem de compatibilidade
        return resultados.stream()
                .sorted(Comparator.comparingInt(PerfilMatchDTO::scorePercentual).reversed())
                .toList();
    }

    // Método auxiliar para comparação segura entre strings, ignorando maiúsculas e nulos
    private boolean camposIguais(String a, String b) {
        return a != null && b != null && a.equalsIgnoreCase(b);
    }

}
