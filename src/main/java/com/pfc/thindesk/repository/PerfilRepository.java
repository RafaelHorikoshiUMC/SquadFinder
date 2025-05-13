package com.pfc.thindesk.repository;

import com.pfc.thindesk.entity.Perfil;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilRepository extends MongoRepository<Perfil, String> {
    List<Perfil> findAll();
}
