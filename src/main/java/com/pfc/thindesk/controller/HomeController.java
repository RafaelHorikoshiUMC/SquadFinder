package com.pfc.thindesk.controller;

import com.pfc.thindesk.entity.Jogo;
import com.pfc.thindesk.entity.Usuario;
import com.pfc.thindesk.service.JogoService;
import com.pfc.thindesk.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private JogoService jogoService;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/inicial")
    public String inicial() {
        return "inicial";
    }

    @GetMapping("/recuperar")
    public String recuperar() {
        return "recuperar";
    }

    // Página de login (já configurada pelo Spring Security)
    @GetMapping("/login")
    public String login() {
        return "login"; // Página de login
    }

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("layout");
        String fragment = "home :: content";
        log.info("Carregando fragmento: {}", fragment); // Log para depuração
        modelAndView.addObject("content", fragment);
        return modelAndView;
    }

    // Lista todos os jogos
    @GetMapping("/jogos")
    public String listarJogos(Model model) {
        List<Jogo> jogos = jogoService.listarTodosJogos();
        model.addAttribute("jogos", jogos);
        String fragment = "jogos :: content";
        log.info("Carregando fragmento: {}", fragment); // Log para depuração
        model.addAttribute("content", fragment);
        return "jogos";
    }

    // Exibi o cadastro para registrar um jogo
    @GetMapping("/jogos/novo")
    public String novoCadastroJogo(Model model) {
        model.addAttribute("jogo", new Jogo());
        return "novoJogo";
    }

    // Exibi o cadastro para editar um jogo existente
    @GetMapping("/jogos/editar/{id}")
    public String editarCadastroJogo(@PathVariable("id") String id, Model model) {
        Jogo jogo = jogoService.buscarJogoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado: " + id));
        model.addAttribute("jogo", jogo);
        return "editarJogo";
    }

    // Salva um novo jogo
    @PostMapping("/jogos/salvar")
    public String salvarjogo(@ModelAttribute Jogo jogo) {
        jogoService.criarJogo(jogo);
        return "redirect:/jogos";
    }

    // Deleta um jogo
    @PostMapping("/jogos/deletar")
    public String deletarJogo(@RequestParam("id") String id) {
        jogoService.deletarJogo(id);
        return "redirect:/jogos";
    }

    // Atualiza um jogo
    @PostMapping("/jogos/atualizar/{id}")
    public String atualizarJogo(@PathVariable("id") String id, @ModelAttribute("jogo") Jogo jogo) {
        jogo.setId(id);
        jogoService.atualizarJogo(id, jogo);
        return "redirect:/jogos";
    }

    // Exibi o cadastro para registrar um jogo
    @GetMapping("/usuarios/novo")
    public String novoCadastroUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "novoUsuario";
    }

    // Salva um novo jogo
    @PostMapping("/usuarios/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario) {
        System.out.println("Salvando usuário: " + usuario); // Log simples
        usuarioService.criarUsuario(usuario);
        return "redirect:/login";
    }


}
