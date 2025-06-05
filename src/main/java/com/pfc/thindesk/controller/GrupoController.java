package com.pfc.thindesk.controller;

import com.pfc.thindesk.entity.Grupo;
import com.pfc.thindesk.entity.Jogo;
import com.pfc.thindesk.entity.Perfil;
import com.pfc.thindesk.service.GrupoService;
import com.pfc.thindesk.service.JogoService;
import com.pfc.thindesk.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;
    @Autowired
    private PerfilService perfilService;
    @Autowired
    private JogoService jogoService;

    @GetMapping
    public String listarGrupos(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(required = false) String termo, RedirectAttributes redirectAttributes) {

        Optional<Perfil> meuPerfil = perfilService.listarPerfisDoUsuario().stream().findFirst();

        if (meuPerfil.isEmpty()) {
            redirectAttributes.addFlashAttribute("msgErro", "Você precisa criar um perfil antes de visualizar ou interagir com os grupos.");
            return "redirect:/perfis";
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Grupo> gruposPage = grupoService.buscarGruposPaginados(termo, pageable);

        String emailUsuarioLogado = SecurityContextHolder.getContext().getAuthentication().getName();
        Perfil perfilLogado = perfilService.buscarPerfilDoUsuarioLogado(emailUsuarioLogado).orElse(null);

        model.addAttribute("grupos", gruposPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", gruposPage.getTotalPages());
        model.addAttribute("perfilLogado", perfilLogado);
        model.addAttribute("termo", termo);

        return "grupos";
    }

    @GetMapping("/novo")
    public String novoCadastroGrupo(Model model) {
        model.addAttribute("jogos", jogoService.listarTodosJogos());
        model.addAttribute("grupo", new Grupo());
        return "novoGrupo";
    }

    @GetMapping("/editar/{id}")
    public String editarCadastroGrupo(@PathVariable("id") String id, Model model) {
        Grupo grupo = grupoService.buscarGrupoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Grupo não encontrado: " + id));
        model.addAttribute("jogos", jogoService.listarTodosJogos());
        model.addAttribute("grupo", grupo);
        return "editarGrupo";
    }

    @PostMapping("/salvar")
    public String salvarGrupo(@ModelAttribute Grupo grupo, RedirectAttributes ra) {
        grupoService.criarGrupo(grupo);
        ra.addFlashAttribute("msgSucesso", "Grupo criado com sucesso!");
        return "redirect:/grupos";
    }

    @PostMapping("/deletar")
    public String deletarGrupo(@RequestParam("id") String id, RedirectAttributes ra) {
        grupoService.deletarGrupo(id);
        ra.addFlashAttribute("msgSucesso", "Grupo deletado com sucesso!");
        return "redirect:/grupos";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarGrupo(@PathVariable("id") String id, @ModelAttribute("grupo") Grupo grupo, RedirectAttributes ra) {
        grupo.setId(id);
        grupoService.atualizarGrupo(id, grupo);
        ra.addFlashAttribute("msgSucesso", "Grupo atualizado com sucesso!");
        return "redirect:/grupos";
    }

    @PostMapping("/{id}/entrar")
    public String entrarNoGrupo(@PathVariable String id, RedirectAttributes ra) {
        try {
            grupoService.entrarNoGrupo(id);
            ra.addFlashAttribute("msgSucesso", "Você entrou no grupo com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("msgErro", e.getMessage());
        }
        return "redirect:/grupos";
    }

    @PostMapping("/{id}/sair")
    public String sairDoGrupo(@PathVariable String id, RedirectAttributes ra) {
        try {
            grupoService.sairDoGrupo(id);
            ra.addFlashAttribute("msgSucesso", "Você saiu do grupo com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("msgErro", e.getMessage());
        }
        return "redirect:/grupos";
    }
}

