package com.pfc.thindesk.service;

import com.pfc.thindesk.entity.LikePerfil;
import com.pfc.thindesk.entity.Perfil;
import com.pfc.thindesk.repository.LikePerfilRepository;
import com.pfc.thindesk.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikePerfilRepository likePerfilRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    // Curte um perfil e verifica se deu match (retorna true se sim)
    public boolean curtirPerfil(String meuPerfilId, String perfilParaCurtirId) {
        if (meuPerfilId.equals(perfilParaCurtirId)) {
            throw new IllegalArgumentException("Não pode curtir o próprio perfil.");
        }

        // Verifica se já curtiu
        Optional<LikePerfil> jaCurtiu = likePerfilRepository.findByPerfilQueCurtiuIdAndPerfilCurtiDoId(meuPerfilId, perfilParaCurtirId);
        if (jaCurtiu.isPresent()) {
            return false; // Já curtiu, não faz nada
        }

        // Salva o like
        LikePerfil novoLike = new LikePerfil(meuPerfilId, perfilParaCurtirId);
        likePerfilRepository.save(novoLike);

        // Verifica se o outro perfil já curtiu este
        Optional<LikePerfil> likeReciproco = likePerfilRepository.findByPerfilQueCurtiuIdAndPerfilCurtiDoId(perfilParaCurtirId, meuPerfilId);
        return likeReciproco.isPresent();
    }

    // Retorna lista dos perfis que deram match com o perfil informado
    public List<Perfil> obterMatches(String meuPerfilId) {
        List<Perfil> matches = new ArrayList<>();

        // Todos os likes dados por mim
        List<LikePerfil> meusLikes = likePerfilRepository.findByPerfilQueCurtiuId(meuPerfilId);

        for (LikePerfil like : meusLikes) {
            String outroPerfilId = like.getPerfilCurtiDoId();

            // Verifica se o outro perfil também curtiu o meu
            Optional<LikePerfil> likeReciproco = likePerfilRepository.findByPerfilQueCurtiuIdAndPerfilCurtiDoId(outroPerfilId, meuPerfilId);
            if (likeReciproco.isPresent()) {
                // Adiciona o perfil do outro lado do match
                perfilRepository.findById(outroPerfilId).ifPresent(matches::add);
            }
        }

        return matches;
    }
}
