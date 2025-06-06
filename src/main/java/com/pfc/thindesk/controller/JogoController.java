package com.pfc.thindesk.controller;

import com.pfc.thindesk.entity.Jogo;
import com.pfc.thindesk.service.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/jogos")
public class JogoController {

    @Autowired
    private JogoService jogoService;

    // Lista todos os jogos
    @GetMapping
    public String listarJogos(@RequestParam(defaultValue = "0") int page, @RequestParam(required = false) String termo, Model model) {
        Pageable pageable = PageRequest.of(page, 25);
        Page<Jogo> jogosPage = jogoService.buscarJogosPaginados(termo, pageable);

        model.addAttribute("jogos", jogosPage.getContent());
        model.addAttribute("paginaAtual", page);
        model.addAttribute("totalPaginas", jogosPage.getTotalPages());
        model.addAttribute("termo", termo);

        return "jogos";
    }

    // Exibi o cadastro para registrar um jogo
    @GetMapping("/novo")
    public String novoCadastroJogo(Model model) {

        model.addAttribute("jogo", new Jogo());
        return "novoJogo";
    }

    // Exibi o cadastro para editar um jogo existente
    @GetMapping("/editar/{id}")
    public String editarCadastroJogo(@PathVariable("id") String id, Model model) {
        Jogo jogo = jogoService.buscarJogoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado: " + id));
        model.addAttribute("jogo", jogo);
        return "editarJogo";
    }

    // Salva um novo jogo
    @PostMapping("/salvar")
    public String salvarJogo(@ModelAttribute Jogo jogo, RedirectAttributes redirectAttributes) {
        jogoService.criarJogo(jogo);
        redirectAttributes.addFlashAttribute("msgSucesso", "Jogo cadastrado com sucesso!");
        return "redirect:/jogos";
    }

    // Deleta um jogo
    @PostMapping("/deletar")
    public String deletarJogo(@RequestParam("id") String id, RedirectAttributes redirectAttributes) {
        boolean deletado = jogoService.deletarJogo(id);
        if (deletado) {
            redirectAttributes.addFlashAttribute("msgSucesso", "Jogo deletado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("msgErro", "Jogo não encontrado.");
        }
        return "redirect:/jogos";
    }

    // Atualiza um jogo
    @PostMapping("/atualizar/{id}")
    public String atualizarJogo(@PathVariable("id") String id, @ModelAttribute("jogo") Jogo jogo, RedirectAttributes redirectAttributes) {
        jogo.setId(id);
        jogoService.atualizarJogo(id, jogo);
        redirectAttributes.addFlashAttribute("msgSucesso", "Jogo atualizado com sucesso!");
        return "redirect:/jogos";
    }

    // Busca os jogos e retorna paginados
    @GetMapping("/buscar")
    @ResponseBody
    public ResponseEntity<List<Jogo>> buscarJogos(@RequestParam String termo) {
        List<Jogo> jogos = jogoService.buscarJogosPorTermo(termo);
        return ResponseEntity.ok(jogos);
    }

}
