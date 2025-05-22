package com.pfc.thindesk.service;

import com.pfc.thindesk.entity.DecisaoMatch;
import com.pfc.thindesk.repository.DecisaoMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DecisaoMatchService {

    @Autowired
    private DecisaoMatchRepository decisaoMatchRepository; // repositório Mongo ou JPA

    public void salvarDecisao(String perfilOrigemId, String perfilAlvoId, boolean deuMatch) {
        DecisaoMatch decisao = new DecisaoMatch();
        decisao.setPerfilOrigemId(perfilOrigemId);
        decisao.setPerfilAlvoId(perfilAlvoId);
        decisao.setDeuMatch(deuMatch);
        decisaoMatchRepository.save(decisao);
    }

    public List<DecisaoMatch> listarDecisoesPorPerfilEStatus(String perfilOrigemId, boolean deuMatch) {
        return decisaoMatchRepository.findByPerfilOrigemIdAndDeuMatch(perfilOrigemId, deuMatch);
    }


}
