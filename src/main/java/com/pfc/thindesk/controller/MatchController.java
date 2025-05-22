package com.pfc.thindesk.controller;

import com.pfc.thindesk.entity.Perfil;
import com.pfc.thindesk.service.LikeService;
import com.pfc.thindesk.service.PerfilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/match")
public class MatchController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private PerfilService perfilService;

    @PostMapping("/curtir/{id}")
    public String curtirPerfil(@PathVariable String id) {
        String meuPerfilId = perfilService.listarPerfisDoUsuario().get(0).getId(); // Simplesmente pega o primeiro perfil do usuário logado
        boolean deuMatch = likeService.curtirPerfil(meuPerfilId, id);
        if (deuMatch) {
            // redirecionar para tela de match?
            return "redirect:/match/novo";
        }
        return "redirect:/perfis"; // ou outro lugar
    }

    @GetMapping("/matches")
    public String verMatches(Model model) {
        String meuPerfilId = perfilService.listarPerfisDoUsuario().get(0).getId();
        List<Perfil> matches = likeService.obterMatches(meuPerfilId);
        model.addAttribute("matches", matches);
        return "match/lista";
    }
}
