package com.example.crud.controllers;

import com.example.crud.domain.Perfil;
import com.example.crud.domain.PerfilRepository;
import com.example.crud.domain.RequestPerfil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/perfil")
public class PerfilController {
    @Autowired
    private PerfilRepository repository;
    @GetMapping
    public ResponseEntity getAllUsers(){
        var allUsers = repository.findAllByActiveTrue();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping
    public ResponseEntity registerProduct(@RequestBody @Valid RequestPerfil data){
        Perfil newPerfil = new Perfil(data);
        repository.save(newPerfil);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateUser(@RequestBody @Valid RequestPerfil data){
        Optional<Perfil> optionalUser = repository.findById(data.id());
        if (optionalUser.isPresent()) {
            Perfil perfil = optionalUser.get();
            perfil.setApelido(data.apelido());
            perfil.setNascimento(data.nascimento());
            perfil.setGeneroPreferido(data.generoPreferido());
            perfil.setComunicacao(data.comunicacao());
            perfil.setPlataforma(data.plataforma());
            perfil.setPeriodoOnline(data.periodoOnline());
            perfil.setEstiloDeJogo(data.estiloDeJogo());
            perfil.setDescricao(data.descricao());
            perfil.setEstadoCivil(data.estadoCivil());
            return ResponseEntity.ok(perfil);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable String id){
        Optional<Perfil> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            Perfil perfil = optionalUser.get();
            perfil.setActive(false);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException();
        }
    }



}
