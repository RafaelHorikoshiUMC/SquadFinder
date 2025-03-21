package com.example.crud.domain;

import jakarta.validation.constraints.NotBlank;

public record RequestBibliotecaJogos(
        String id,

        @NotBlank
        String nome,
        String categoria,
        String plataforma
) {
}
