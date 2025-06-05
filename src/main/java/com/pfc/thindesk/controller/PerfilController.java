package com.pfc.thindesk.controller;

import com.pfc.thindesk.entity.Jogo;
import com.pfc.thindesk.entity.Perfil;
import com.pfc.thindesk.service.DecisaoMatchService;
import com.pfc.thindesk.service.JogoService;
import com.pfc.thindesk.service.PerfilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/perfis")
public class PerfilController {

    private static final Logger log = LoggerFactory.getLogger(PerfilController.class);

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private JogoService jogoService;

    @Autowired
    private DecisaoMatchService decisaoMatchService;

    // Lista todos os perfis
    @GetMapping
    public String listarPerfis(Model model) {
        // Obtém apenas os perfis do usuário autenticado
        List<Perfil> perfis = perfilService.listarPerfisDoUsuario();
        model.addAttribute("perfis", perfis);

        String fragment = "perfis :: content";
        log.info("Carregando fragmento: {}", fragment); // Log para depuração
        model.addAttribute("content", fragment);

        return "perfis";
    }

    // Exibi o cadastro para registrar um perfil
    @GetMapping("/novo")
    public String novoCadastroPerfil(Model model) {
        LocalDate hoje = LocalDate.now();
        String dataMinNascimento = hoje.minusYears(100).toString();
        String dataMaxNascimento = hoje.minusYears(14).toString();

        model.addAttribute("dataMinNascimento", dataMinNascimento);
        model.addAttribute("dataMaxNascimento", dataMaxNascimento);
        model.addAttribute("perfil", new Perfil());
        List<Jogo> jogos = jogoService.listarTodosJogos(); // Sua forma de buscar todos os jogos
        model.addAttribute("jogos", jogos);
        // Também passe as seeds dos avatares se for o caso
        model.addAttribute("seeds", List.of("1", "2", "3", "4", "5", "6", "7", "9", "10", "11", "12"));
        return "novoPerfil";
    }

    // Exibi o cadastro para editar um perfil existente
    @GetMapping("/editar/{id}")
    public String editarCadastroPerfil(@PathVariable("id") String id, Model model) {
        LocalDate hoje = LocalDate.now();
        String dataMinNascimento = hoje.minusYears(100).toString();
        String dataMaxNascimento = hoje.minusYears(14).toString();
        Perfil perfil = perfilService.buscarPerfilPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado: " + id));
        model.addAttribute("dataMinNascimento", dataMinNascimento);
        model.addAttribute("dataMaxNascimento", dataMaxNascimento);
        model.addAttribute("perfil", perfil);
        List<Jogo> jogos = jogoService.listarTodosJogos(); // Sua forma de buscar todos os jogos
        model.addAttribute("jogos", jogos);
        model.addAttribute("seeds", List.of("1", "2", "3", "4", "5", "6", "7", "9", "10", "11", "12"));
        return "editarPerfil";
    }

    // Salva um novo perfil
    @PostMapping("/salvar")
    public String salvarPerfil(@ModelAttribute Perfil perfil, RedirectAttributes redirectAttributes) {
        perfilService.criarPerfil(perfil);
        redirectAttributes.addFlashAttribute("msgSucesso", "Perfil criado com sucesso!");
        return "redirect:/perfis";
    }

    // Deleta um perfil
    @PostMapping("/deletar")
    public String deletarPerfil(@RequestParam("id") String id) {
        perfilService.deletarPerfil(id);
        return "redirect:/perfis";
    }

    // Atualiza um perfil
    @PostMapping("/atualizar/{id}")
    public String atualizarPerfil(@PathVariable("id") String id, @ModelAttribute("perfil") Perfil perfil, RedirectAttributes redirectAttributes) {
        perfil.setId(id);
        perfilService.atualizarPerfil(id, perfil);
        redirectAttributes.addFlashAttribute("msgSucesso", "Perfil atualizado com sucesso!");
        return "redirect:/perfis";
    }

    //O usuario Da Match Sim
    @PostMapping("/{perfilOrigemId}/match/{perfilAlvoId}/sim")
    public ResponseEntity<Void> darMatchSim(
            @PathVariable("perfilOrigemId") String perfilOrigemId,
            @PathVariable("perfilAlvoId") String perfilAlvoId) {
        decisaoMatchService.salvarDecisao(perfilOrigemId, perfilAlvoId, true);
        return ResponseEntity.ok().build();
    }

    //O usuario Da Match Sim
    @PostMapping("/{perfilOrigemId}/match/{perfilAlvoId}/nao")
    public ResponseEntity<Void> darMatchNao(
            @PathVariable("perfilOrigemId") String perfilOrigemId,
            @PathVariable("perfilAlvoId") String perfilAlvoId) {
        decisaoMatchService.salvarDecisao(perfilOrigemId, perfilAlvoId, false);
        return ResponseEntity.ok().build();
    }
}
