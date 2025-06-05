package com.pfc.thindesk.repository;

import com.pfc.thindesk.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    // Busca pelo email
    Optional<Usuario> findByEmail(String email);

    // Busca pelo nome
    Optional<Usuario> findByNome(String nome);

    // Busca pelo nome, email ou apelido e retorna os resultados em uma página
    Page<Usuario> findByNomeContainingIgnoreCaseOrEmailContainingIgnoreCaseOrIdIn(String nome, String email, List<String> ids, Pageable pageable);

}


