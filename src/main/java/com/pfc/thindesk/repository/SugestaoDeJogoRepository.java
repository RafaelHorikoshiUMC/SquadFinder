package com.pfc.thindesk.repository;

import com.pfc.thindesk.entity.Grupo;
import com.pfc.thindesk.entity.Perfil;
import com.pfc.thindesk.entity.SugestaoDeJogo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SugestaoDeJogoRepository extends MongoRepository<SugestaoDeJogo, String> {
    @Query("{ '$or': [ " +
            "{ 'nomeDoJogoSugerido': { $regex: ?0, $options: 'i' } }, " +
            "{ 'descricaoOpcional': { $regex: ?0, $options: 'i' } }, " +
            "{ 'criador': { $regex: ?0, $options: 'i' } } " +
            "] }")
    Page<SugestaoDeJogo> buscarPorTermo(String termo, Pageable pageable);

    void deleteByCriador(String criadorId);
    void deleteByPerfilCriador(Perfil perfil);


}
