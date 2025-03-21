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
@RequestMapping("/biblioteca")
public class BibliotecaJogosController {
    @Autowired
    private BibliotecaJogosRepository repository;
    @GetMapping
    public ResponseEntity getAllUsers(){
        var allUsers = repository.findAllByActiveTrue();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping
    public ResponseEntity registerBibliotecaJogos(@RequestBody @Valid RequestBibliotecaJogos data){
        BibliotecaJogos newBibliotecaJogos = new BibliotecaJogos(data);
        repository.save(newBibliotecaJogos);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateBibliotecaJogos(@RequestBody @Valid RequestBibliotecaJogos data){
        Optional<BibliotecaJogos> optionaljogo = repository.findById(data.id());
        if (optionaljogo.isPresent()) {
            BibliotecaJogos bibliotecaJogos = optionaljogo.get();
            bibliotecaJogos.setNome(data.nome());
            bibliotecaJogos.setCategoria(data.categoria());
            bibliotecaJogos.setPlataforma(data.plataforma());
            return ResponseEntity.ok(bibliotecaJogos);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteBibliotecaJogos(@PathVariable String id){
        Optional<BibliotecaJogos> optionalJogo = repository.findById(id);
        if (optionalJogo.isPresent()) {
            BibliotecaJogos bibliotecaJogos = optionalJogo.get();
            bibliotecaJogos.setActive(false);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException();
        }
    }
}
