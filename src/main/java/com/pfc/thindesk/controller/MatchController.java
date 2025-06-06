package com.pfc.thindesk.controller;

import com.pfc.thindesk.PerfilMatchDTO;
import com.pfc.thindesk.entity.Perfil;
import com.pfc.thindesk.service.DecisaoMatchService;
import com.pfc.thindesk.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/perfis")
public class MatchController {

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private DecisaoMatchService decisaoMatchService;

    // Pagina com perfis compatíveis
    @GetMapping("/compativeis")
    public String mostrarPerfisCompativeis(Model model, RedirectAttributes redirectAttributes, Principal principal) {
        Optional<Perfil> meuPerfil = perfilService.buscarPerfilDoUsuarioLogado(principal.getName());

        if (meuPerfil.isEmpty()) {
            redirectAttributes.addFlashAttribute("msgErro", "Você precisa criar um perfil antes de visualizar ou interagir com o match.");
            return "redirect:/perfis";
        }

        List<Perfil> perfisNaoReagidos = perfilService.obterPerfisNaoReagidos(meuPerfil.get());
        List<PerfilMatchDTO> perfisCompativeis = perfilService.calcularCompatibilidade(meuPerfil.get(), perfisNaoReagidos);

        model.addAttribute("perfilAtual", meuPerfil.get());
        model.addAttribute("perfisCompativeis", perfisCompativeis);
        return "compativeis";
    }

    // Pagina com matches recíprocos
    @GetMapping("/matches-reciprocos")
    public String listarMatchesReciprocos(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Perfil> meuPerfil = perfilService.buscarPerfilDoUsuarioLogado(principal.getName());

        if (meuPerfil.isEmpty()) {
            redirectAttributes.addFlashAttribute("msgErro", "Você precisa criar um perfil antes de visualizar seus matches.");
            return "redirect:/perfis";
        }

        List<com.pfc.thindesk.entity.DecisaoMatch> matchesComigo = decisaoMatchService.listarMatchesComigo(meuPerfil.get().getId());

        Set<String> ids = matchesComigo.stream()
                .map(com.pfc.thindesk.entity.DecisaoMatch::getPerfilOrigemId)
                .collect(Collectors.toSet());

        List<Perfil> perfis = perfilService.buscarPerfisPorIds(ids);

        model.addAttribute("meuPerfil", meuPerfil.get());
        model.addAttribute("perfisQueDeramMatch", perfis);
        return "matches-reciprocos";
    }

    // Pagina com curtidas recebidas (sem resposta)
    @GetMapping("/curtidas-recebidas")
    public String verCurtidasRecebidas(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Perfil> meuPerfil = perfilService.buscarPerfilDoUsuarioLogado(principal.getName());

        if (meuPerfil.isEmpty()) {
            redirectAttributes.addFlashAttribute("msgErro", "Você precisa criar um perfil antes de visualizar as curtidas recebidas.");
            return "redirect:/perfis";
        }

        List<Perfil> perfisQueMeCurtiram = decisaoMatchService.listarQuemMeCurtiuSemResposta(meuPerfil.get().getId());

        model.addAttribute("perfis", perfisQueMeCurtiram);
        model.addAttribute("perfilAtual", meuPerfil.get());
        return "curtidas-recebidas";
    }

}
