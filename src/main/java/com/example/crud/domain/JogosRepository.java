package com.example.crud.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JogosRepository extends JpaRepository<Jogos, String> {
    List<Jogos> findAllByActiveTrue();
}