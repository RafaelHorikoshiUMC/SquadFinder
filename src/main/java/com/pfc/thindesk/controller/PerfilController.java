package com.pfc.thindesk.controller;

import com.pfc.thindesk.entity.Perfil;
import com.pfc.thindesk.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfil")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    // Cria um novo perfil
    @PostMapping
    public Perfil criarPerfil(@RequestBody Perfil perfil) {
        return perfilService.criarPerfil(perfil);
    }

    // Lista todos os perfis
    @GetMapping
    public List<Perfil> listarPerfis() {
        return perfilService.listarTodosPerfis();
    }

    // Busca um perfil por ID
    @GetMapping("/{id}")
    public ResponseEntity<Perfil> buscarPerfilPorId(@PathVariable String id) {
        return perfilService.buscarPerfilPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualiza um perfil
    @PutMapping("/{id}")
    public ResponseEntity<Perfil> atualizarPerfil(@PathVariable String id, @RequestBody Perfil perfil) {
        Perfil perfilAtualizado = perfilService.atualizarPerfil(id, perfil);
        if (perfilAtualizado != null) {
            return ResponseEntity.ok(perfilAtualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // Deleta um perfil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPerfil(@PathVariable String id) {
        perfilService.deletarPerfil(id);
        return ResponseEntity.noContent().build();
    }

}
