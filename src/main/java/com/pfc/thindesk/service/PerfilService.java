package com.pfc.thindesk.service;

import com.pfc.thindesk.PerfilMatchDTO;
import com.pfc.thindesk.entity.Perfil;
import com.pfc.thindesk.entity.Usuario;
import com.pfc.thindesk.repository.PerfilRepository;
import com.pfc.thindesk.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PerfilService {
    @Autowired
    public PerfilRepository perfilRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Recupera o usuário atualmente autenticado
    public String getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String email = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
            if (usuarioOpt.isPresent()) {
                return usuarioOpt.get().getId(); // retorna o ID Mongo do usuário
            }
        }
        return null;
    }


    // Cria um novo perfil
    public Perfil criarPerfil(Perfil perfil) {
        String usuarioId = getUsuarioAutenticado();
        if (usuarioId == null) {
            throw new IllegalStateException("Usuário não autenticado. Não é possível criar um perfil.");
        }

        // Seta o usuarioId no perfil antes de salvar
        perfil.setUsuarioId(usuarioId);
        return perfilRepository.save(perfil);
    }

    // Lista apenas os perfis do usuário autenticado
    public List<Perfil> listarPerfisDoUsuario() {
        String usuarioAutenticado = getUsuarioAutenticado();
        if (usuarioAutenticado == null) {
            return new ArrayList<>(); // Nenhum perfil para usuário não autenticado
        }

        return perfilRepository.findAll().stream()
                .filter(perfil -> usuarioAutenticado.equals(perfil.getUsuarioId())) // Verifica null aqui
                .toList();
    }

    // Busca um perfil específico do usuário autenticado
    public Optional<Perfil> buscarPerfilPorId(String id) {
        String usuarioAutenticado = getUsuarioAutenticado();
        if (usuarioAutenticado == null) {
            return Optional.empty(); // Retorna vazio se o usuário não está autenticado
        }

        // Busca o perfil e filtra pelo usuarioId
        return perfilRepository.findById(id)
                .filter(perfil -> usuarioAutenticado.equals(perfil.getUsuarioId())); // Verifica null aqui
    }

    // Atualiza apenas se o perfil pertencer ao usuário autenticado
    public Perfil atualizarPerfil(String id, Perfil perfilAtualizado) {
        String usuarioAutenticado = getUsuarioAutenticado();
        if (usuarioAutenticado == null) {
            throw new SecurityException("Usuário não autenticado. Operação não permitida.");
        }

        Perfil perfilExistente = perfilRepository.findById(id)
                .filter(perfil -> usuarioAutenticado.equals(perfil.getUsuarioId())) // Verifica null aqui
                .orElseThrow(() -> new SecurityException("Acesso negado ao perfil especificado!"));

        perfilAtualizado.setId(perfilExistente.getId()); // Garante que o ID original será mantido
        perfilAtualizado.setUsuarioId(perfilExistente.getUsuarioId()); // Mantém o usuário correto
        return perfilRepository.save(perfilAtualizado);
    }

    // Exclui apenas se o perfil pertencer ao usuário autenticado
    public void deletarPerfil(String id) {
        String usuarioAutenticado = getUsuarioAutenticado();
        if (usuarioAutenticado == null) {
            throw new SecurityException("Usuário não autenticado. Operação não permitida.");
        }

        Perfil perfilExistente = perfilRepository.findById(id)
                .filter(perfil -> usuarioAutenticado.equals(perfil.getUsuarioId())) // Verifica null aqui
                .orElseThrow(() -> new SecurityException("Acesso negado ao perfil especificado!"));

        perfilRepository.delete(perfilExistente);
    }

    // Algoritmo de compatibilidade
    public List<PerfilMatchDTO> buscarPerfisCompatíveis(Perfil perfilAtual) {
        // O mesmo de antes, sem mudanças
        List<Perfil> todosPerfis = perfilRepository.findAll();
        List<PerfilMatchDTO> resultados = new ArrayList<>();

        for (Perfil outroPerfil : todosPerfis) {
            if (!outroPerfil.getId().equals(perfilAtual.getId())) {
                int atributosEmComum = 0;
                int totalAtributos = 7;

                if (camposIguais(perfilAtual.getPlataforma(), outroPerfil.getPlataforma()))
                    atributosEmComum += 15;
                if (camposIguais(perfilAtual.getComunicacao(), outroPerfil.getComunicacao()))
                    atributosEmComum += 10;
                if (camposIguais(perfilAtual.getPeriodoOnline(), outroPerfil.getPeriodoOnline()))
                    atributosEmComum += 12;
                if (camposIguais(perfilAtual.getEstiloDeJogo(), outroPerfil.getEstiloDeJogo()))
                    atributosEmComum += 8;
                if (camposIguais(perfilAtual.getGeneroPreferidoPrincipal(), outroPerfil.getGeneroPreferidoPrincipal()))
                    atributosEmComum += 6;
                if (camposIguais(perfilAtual.getGeneroPreferidoSecundario(), outroPerfil.getGeneroPreferidoSecundario()))
                    atributosEmComum += 3;
                if (camposIguais(perfilAtual.getEstadoCivil(), outroPerfil.getEstadoCivil()))
                    atributosEmComum += 1;

                int porcentagem = (int) ((atributosEmComum / (double) totalAtributos) * 100);
                resultados.add(new PerfilMatchDTO(outroPerfil, porcentagem));
            }
        }

        return resultados.stream()
                .sorted(Comparator.comparingInt(PerfilMatchDTO::scorePercentual).reversed())
                .toList();
    }

    // Método auxiliar para comparação segura de strings
    private boolean camposIguais(String a, String b) {
        return (a == null && b == null) || (a != null && a.equalsIgnoreCase(b));
    }

    public Optional<Perfil> buscarPerfilDoUsuarioLogado(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return Optional.empty();
        }
        String usuarioId = usuarioOpt.get().getId();  // pega o ID do usuário
        return perfilRepository.findByUsuarioId(usuarioId);
    }

}