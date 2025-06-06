package com.pfc.thindesk.repository;

import com.pfc.thindesk.entity.Jogo;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface JogoRepository extends MongoRepository<Jogo, String> {
    //Procura Todos
    List<Jogo> findAll();

    // Busca e retorna os resultados em uma página
    @Query("{ '$or': [ " +
            "{ 'nome': { $regex: ?0, $options: 'i' } }, " +
            "{ 'categoria': { $regex: ?0, $options: 'i' } }, " +
            "{ 'plataforma': { $regex: ?0, $options: 'i' } }, " +
            "{ 'desenvolvedora': { $regex: ?0, $options: 'i' } }, " +
            "{ 'anoDeLancamento': { $regex: ?0, $options: 'i' } } " +
            "] }")
    Page<Jogo> buscarPorTodosCampos(String termo, Pageable pageable);

}
