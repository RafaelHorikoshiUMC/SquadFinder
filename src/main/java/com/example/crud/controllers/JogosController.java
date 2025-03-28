package com.example.crud.controllers;

import com.example.crud.domain.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/jogos")
public class JogosController {
    @Autowired
    private JogosRepository repository;
    @GetMapping
    public ResponseEntity getAllUsers(){
        var allUsers = repository.findAllByActiveTrue();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping
    public ResponseEntity registerJogos(@RequestBody @Valid RequestJogos data){
        Jogos newJogos = new Jogos(data);
        repository.save(newJogos);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateJogos(@RequestBody @Valid RequestJogos data){
        Optional<Jogos> optionaljogo = repository.findById(data.id());
        if (optionaljogo.isPresent()) {
            Jogos jogos = optionaljogo.get();
            jogos.setNome(data.nome());
            jogos.setCategoria(data.categoria());
            jogos.setPlataforma(data.plataforma());
            return ResponseEntity.ok(jogos);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteJogos(@PathVariable String id){
        Optional<Jogos> optionalJogo = repository.findById(id);
        if (optionalJogo.isPresent()) {
            Jogos jogos = optionalJogo.get();
            jogos.setActive(false);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException();
        }
    }
}
