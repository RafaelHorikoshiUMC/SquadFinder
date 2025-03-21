package com.example.crud.domain;

import jakarta.validation.constraints.NotBlank;

public record RequestPerfil(
        String id,

        @NotBlank
        String apelido,
        String nascimento,
        String generoPreferido,
        String comunicacao,
        String plataforma,
        String periodoOnline,
        String estiloDeJogo,
        String descricao,
        String estadoCivil
) {
}
