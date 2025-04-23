package com.pfc.thindesk.repository;

import com.pfc.thindesk.entity.Jogo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogoRepository extends MongoRepository<Jogo, String> {
}
