package com.pfc.thindesk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "decisoes")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DecisaoMatch {

    @Id
    private String id; // se for MongoDB
    private String perfilOrigemId; // quem está decidindo
    private String perfilAlvoId;   // sobre quem foi a decisão
    private boolean deuMatch;
}
