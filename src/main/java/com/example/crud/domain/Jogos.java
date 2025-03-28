package com.example.crud.domain;

import jakarta.persistence.*;
import lombok.*;

    @Table(name="jogos")
    @Entity(name="jogos")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(of = "id")
    public class Jogos {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;

        @Column(name = "nome")
        private String nome;

        @Column(name = "categoria")
        private String categoria;

        @Column(name = "plataforma")
        private String plataforma;

        private Boolean active;

        public Jogos(RequestJogos requestJogos){
            this.nome = requestJogos.nome();
            this.categoria = requestJogos.categoria();
            this.plataforma = requestJogos.plataforma();
            this.active = true;
        }
    }
