package com.pfc.thindesk.repository;

import com.pfc.thindesk.entity.LikePerfil;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface LikePerfilRepository extends MongoRepository<LikePerfil, String> {

    // Retorna todos os likes dados por um perfil
    List<LikePerfil> findByPerfilQueCurtiuId(String perfilQueCurtiuId);

    // Retorna todos os likes recebidos por um perfil
    List<LikePerfil> findByPerfilCurtiDoId(String perfilCurtiDoId);

    // Busca um like que indica que o outro perfil curtiu este
    Optional<LikePerfil> findByPerfilQueCurtiuIdAndPerfilCurtiDoId(String perfilCurtiDoId, String perfilQueCurtiuId);
}
