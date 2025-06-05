package com.pfc.thindesk.controller;

import com.pfc.thindesk.email.dtos.RecoverDto;
import com.pfc.thindesk.entity.*;
import com.pfc.thindesk.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private JogoService jogoService;
    @Autowired
    private PerfilService perfilService;
    @Autowired
    private GrupoService grupoService;
    @Autowired
    private SugestaoDeJogoService sugestaoDeJogoService;
    @Autowired
    private DepoimentoService depoimentoService;
    @Autowired
    private DecisaoMatchService decisaoMatchService;

    // Página da dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<DecisaoMatch> todasDecisoes = decisaoMatchService.listarTodasDecisaoMatch();
        model.addAttribute("QuantidadeDecisoes", todasDecisoes.size());
        List<Depoimento> todosDepoimentos = depoimentoService.listarTodosDepoimentos();
        model.addAttribute("QuantidadeDepoimentos", todosDepoimentos.size());
        List<Grupo> todosGrupos = grupoService.listarTodosGrupos();
        model.addAttribute("QuantidadeGrupos", todosGrupos.size());
        List<Jogo> todosJogos = jogoService.listarTodosJogos();
        model.addAttribute("QuantidadeJogos", todosJogos.size());

        List<Perfil> todosPerfis = perfilService.listarTodosPerfis();
        model.addAttribute("QuantidadePerfis", todosPerfis.size());

        List<SugestaoDeJogo> todasSugestoes = sugestaoDeJogoService.listarTodasSugestaoDeJogo();
        model.addAttribute("QuantidadeSugestoes", todasSugestoes.size());
        return "dashboard";
    }

    // Página inicial
    @GetMapping("/inicial")
    public String inicial() {
        return "inicial";
    }

    // Página das politicas de Privacidade
    @GetMapping("/politicasPrivacidade")
    public String politicasPrivacidade() {
        return "politicasPrivacidade";
    }

    // Página de login (já configurada pelo Spring Security)
    @GetMapping("/login")
    public String login() {
        return "login"; // Página de login
    }

    // Redireciona para a página inicial
    @GetMapping("/")
    public String home() {
        return "inicial";
    }

    // Página de erro
    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "acesso-negado";
    }

}