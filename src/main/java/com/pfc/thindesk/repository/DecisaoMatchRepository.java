package com.pfc.thindesk.repository;

import com.pfc.thindesk.entity.DecisaoMatch;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface DecisaoMatchRepository extends MongoRepository<DecisaoMatch, String> {

    // Busca todas as decisões de um perfil (perfilOrigemId) com o filtro sim ou não (deuMatch)
    List<DecisaoMatch> findByPerfilOrigemIdAndDeuMatch(String perfilOrigemId, boolean deuMatch);

}
