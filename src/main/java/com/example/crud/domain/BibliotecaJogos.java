package com.example.crud.domain;

import jakarta.persistence.*;
import lombok.*;

    @Table(name="bibliotecaJogos")
    @Entity(name="bibliotecaJogos")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(of = "id")
    public class BibliotecaJogos {
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

        public BibliotecaJogos(RequestBibliotecaJogos requestBibliotecaJogos){
            this.nome = requestBibliotecaJogos.nome();
            this.categoria = requestBibliotecaJogos.categoria();
            this.plataforma = requestBibliotecaJogos.plataforma();
            this.active = true;
        }
    }
