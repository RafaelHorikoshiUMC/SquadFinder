package com.pfc.thindesk.repository;

import com.pfc.thindesk.entity.Mensagem;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MensagemRepository extends MongoRepository<Mensagem, String> {
    List<Mensagem> findByRemetenteIdAndDestinatarioIdOrRemetenteIdAndDestinatarioIdOrderByDataHora(
            String remetenteId1, String destinatarioId1, String remetenteId2, String destinatarioId2
    );
}

