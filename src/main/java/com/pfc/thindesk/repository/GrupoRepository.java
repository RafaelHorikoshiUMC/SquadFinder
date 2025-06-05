package com.pfc.thindesk.repository;

import com.pfc.thindesk.entity.Grupo;
import com.pfc.thindesk.entity.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

@Repository
public interface GrupoRepository extends MongoRepository<Grupo, String> {
    @Query("{ '$or': [ " +
            "{ 'criador': { $regex: ?0, $options: 'i' } }, " +
            "{ 'objetivo': { $regex: ?0, $options: 'i' } }, " +
            "{ 'horario': { $regex: ?0, $options: 'i' } }, " +
            "{ 'nomeJogo': { $regex: ?0, $options: 'i' } } " +
            "] }")
    Page<Grupo> buscarPorTermo(String termo, Pageable pageable);


    List<Grupo> findByPerfilCriador(Perfil perfil);
}
