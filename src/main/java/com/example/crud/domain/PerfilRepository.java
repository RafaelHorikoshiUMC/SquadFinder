package com.example.crud.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerfilRepository extends JpaRepository<Perfil, String> {
    List<Perfil> findAllByActiveTrue();
}