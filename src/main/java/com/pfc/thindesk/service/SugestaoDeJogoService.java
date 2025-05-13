package com.pfc.thindesk.service;

import com.pfc.thindesk.entity.Depoimento;
import com.pfc.thindesk.entity.Jogo;
import com.pfc.thindesk.entity.SugestaoDeJogo;
import com.pfc.thindesk.repository.SugestaoDeJogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SugestaoDeJogoService {

    @Autowired
    private SugestaoDeJogoRepository SugestaoDeJogoRepository;
    @Autowired
    private SugestaoDeJogoRepository sugestaoDeJogoRepository;

    // Cria uma nova Sugestao De Jogo
    public SugestaoDeJogo criarSugestaoDeJogo(SugestaoDeJogo sugestaoDeJogo) {
        LocalDateTime agora = LocalDateTime.now();
        sugestaoDeJogo.setDataCriacao(agora);
        sugestaoDeJogo.setDataAtualizacao(agora);
        return SugestaoDeJogoRepository.save(sugestaoDeJogo);
    }

    // Busca todas as Sugestões De Jogos
    public List<SugestaoDeJogo> listarTodasSugestaoDeJogo() {return SugestaoDeJogoRepository.findAll();}

    // Busca a Sugestao De Jogo por ID
    public Optional<SugestaoDeJogo> buscarSugestaoDeJogoPorId(String id) {
        return SugestaoDeJogoRepository.findById(id);
    }

    // Atualizar uma Sugestao De Jogo
    public SugestaoDeJogo atualizarSugestaoDeJogo(String id, SugestaoDeJogo sugestaoDeJogo) {
        Optional<SugestaoDeJogo> existente = sugestaoDeJogoRepository.findById(id);
        if (existente.isPresent()) {
            SugestaoDeJogo original = existente.get();
            original.setNomeDoJogoSugerido(sugestaoDeJogo.getNomeDoJogoSugerido());
            original.setDataAtualizacao(LocalDateTime.now());
            return sugestaoDeJogoRepository.save(original);
        }
        return null;
    }

    // Deleta uma Sugestao De Jogo
    public void deletarSugestaoDeJogo(String id) {
        SugestaoDeJogoRepository.deleteById(id);
    }

}
