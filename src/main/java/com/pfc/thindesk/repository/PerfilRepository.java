package com.pfc.thindesk.repository;

import com.pfc.thindesk.entity.Perfil;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends MongoRepository<Perfil, String> {
}
