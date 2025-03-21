package com.example.crud.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BibliotecaJogosRepository extends JpaRepository<BibliotecaJogos, String> {
    List<BibliotecaJogos> findAllByActiveTrue();
}