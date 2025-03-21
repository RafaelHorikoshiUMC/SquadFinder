package com.example.crud.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name="perfils")
@Entity(name="perfils")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Perfil {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "apelido")
    private String apelido;

    @Column(name = "nascimento")
    private String nascimento;

    @Column(name = "genero_preferido")
    private String generoPreferido;

    @Column(name = "comunicacao")
    private String comunicacao;

    @Column(name = "plataforma")
    private String plataforma;

    @Column(name = "periodo_online")
    private String periodoOnline;

    private Boolean active;

    @Column(name = "estilo_de_jogo")
    private String estiloDeJogo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "estado_civil")
    private String estadoCivil;

    public Perfil(RequestPerfil requestPerfil){
        this.apelido = requestPerfil.apelido();
        this.nascimento = requestPerfil.nascimento();
        this.generoPreferido = requestPerfil.generoPreferido();
        this.comunicacao = requestPerfil.comunicacao();
        this.plataforma = requestPerfil.plataforma();
        this.periodoOnline = requestPerfil.periodoOnline();
        this.active = true;
        this.estiloDeJogo = requestPerfil.estiloDeJogo();
        this.descricao = requestPerfil.descricao();
        this.estadoCivil = requestPerfil.estadoCivil();
    }
}
