package com.pfc.thindesk.repository;

import com.pfc.thindesk.entity.DecisaoMatch;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface DecisaoMatchRepository extends MongoRepository<DecisaoMatch, String> {


    boolean existsByPerfilOrigemIdAndPerfilAlvoIdAndDeuMatch(String perfilOrigemId, String perfilAlvoId, boolean deuMatch);

    List<DecisaoMatch> findByPerfilAlvoIdAndDeuMatch(String perfilAlvoId, boolean deuMatch);

    boolean existsByPerfilOrigemIdAndPerfilAlvoId(String perfilOrigemId, String perfilAlvoId);

    List<DecisaoMatch> findByPerfilOrigemId(String perfilOrigemId);

}

