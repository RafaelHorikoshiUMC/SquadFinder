package com.pfc.thindesk.repository;

import com.pfc.thindesk.entity.Depoimento;
import com.pfc.thindesk.entity.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepoimentoRepository extends MongoRepository<Depoimento, String> {
    @Query("{ '$or': [ " +
            "{ 'texto': { $regex: ?0, $options: 'i' } }, " +
            "{ 'criador': { $regex: ?0, $options: 'i' } } " +
            "] }")
    Page<Depoimento> buscarPorTermo(String termo, Pageable pageable);

    void deleteByPerfilCriador(Perfil perfil);

}
