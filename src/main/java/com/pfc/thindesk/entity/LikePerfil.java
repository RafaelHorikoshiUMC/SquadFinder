package com.pfc.thindesk.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "likes")
public class LikePerfil {

    @Id
    private String id;

    private String perfilQueCurtiuId;  // Quem deu like
    private String perfilCurtiDoId;    // Quem recebeu o like

    public LikePerfil() {}

    public LikePerfil(String perfilQueCurtiuId, String perfilCurtiDoId) {
        this.perfilQueCurtiuId = perfilQueCurtiuId;
        this.perfilCurtiDoId = perfilCurtiDoId;
    }

    // Getters e setters

    public String getId() {
        return id;
    }

    public String getPerfilQueCurtiuId() {
        return perfilQueCurtiuId;
    }

    public void setPerfilQueCurtiuId(String perfilQueCurtiuId) {
        this.perfilQueCurtiuId = perfilQueCurtiuId;
    }

    public String getPerfilCurtiDoId() {
        return perfilCurtiDoId;
    }

    public void setPerfilCurtiDoId(String perfilCurtiDoId) {
        this.perfilCurtiDoId = perfilCurtiDoId;
    }
}
