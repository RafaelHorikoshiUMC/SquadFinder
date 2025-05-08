package com.pfc.thindesk.controller;

import com.pfc.thindesk.entity.*;
import com.pfc.thindesk.repository.UsuarioRepository;
import com.pfc.thindesk.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private JogoService jogoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PerfilService perfilService;
    @Autowired
    private GrupoService grupoService;
    @Autowired
    private SugestaoDeJogoService sugestaoDeJogoService;
    @Autowired
    private DepoimentoService depoimentoService;

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

    // Exibi o cadastro para registrar um usuario
    @GetMapping("/usuarios/novo")
    public String novoCadastroUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "novoUsuario";
    }

    // Salva um novo usuario
    @PostMapping("/usuarios/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario) {
        System.out.println("Salvando usuário: " + usuario); // Log simples
        usuarioService.criarUsuario(usuario);
        return "redirect:/login";
    }

    // Lista todos os grupos
    @GetMapping("/grupos")
    public String listarGrupos(Model model) {
        List<Grupo> grupos = grupoService.listarTodosGrupos();
        model.addAttribute("grupos", grupos);
        String fragment = "grupos :: content";
        log.info("Carregando fragmento: {}", fragment); // Log para depuração
        model.addAttribute("content", fragment);
        return "grupos";
    }

    // Exibi o cadastro para registrar um grupo
    @GetMapping("/grupos/novo")
    public String novoCadastroGrupo(Model model) {
        model.addAttribute("grupo", new Grupo());
        return "novoGrupo";
    }

    // Exibi o cadastro para editar um grupo existente
    @GetMapping("/grupos/editar/{id}")
    public String editarCadastroGrupo(@PathVariable("id") String id, Model model) {
        Grupo grupo = grupoService.buscarGrupoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado: " + id));
        model.addAttribute("grupo", grupo);
        return "editarGrupo";
    }

    // Salva um novo grupo
    @PostMapping("/grupos/salvar")
    public String salvarGrupo(@ModelAttribute Grupo grupo) {
        grupoService.criarGrupo(grupo);
        return "redirect:/grupos";
    }

    // Deleta um grupo
    @PostMapping("/grupos/deletar")
    public String deletarGrupo(@RequestParam("id") String id) {
        grupoService.deletarGrupo(id);
        return "redirect:/grupos";
    }

    // Atualiza um grupo
    @PostMapping("/grupos/atualizar/{id}")
    public String atualizarGrupo(@PathVariable("id") String id, @ModelAttribute("grupo") Grupo grupo) {
        grupo.setId(id);
        grupoService.atualizarGrupo(id, grupo);
        return "redirect:/grupos";
    }

    // Lista todos os perfis
    @GetMapping("/perfis")
    public String listarPerfis(Model model) {
        List<Perfil> perfis = perfilService.listarTodosPerfis();
        model.addAttribute("perfis", perfis);
        String fragment = "perfis :: content";
        log.info("Carregando fragmento: {}", fragment); // Log para depuração
        model.addAttribute("content", fragment);
        return "perfis";
    }

    // Exibi o cadastro para registrar um perfil
    @GetMapping("/perfis/novo")
    public String novoCadastroPerfil(Model model) {
        model.addAttribute("perfil", new Perfil());
        return "novoPerfil";
    }

    // Exibi o cadastro para editar um perfil existente
    @GetMapping("/perfis/editar/{id}")
    public String editarCadastroPerfil(@PathVariable("id") String id, Model model) {
        Perfil perfil = perfilService.buscarPerfilPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado: " + id));
        model.addAttribute("perfil", perfil);
        return "editarPerfil";
    }

    // Salva um novo perfil
    @PostMapping("/perfis/salvar")
    public String salvarPerfil(@ModelAttribute Perfil perfil) {
        perfilService.criarPerfil(perfil);
        return "redirect:/perfis";
    }

    // Deleta um perfil
    @PostMapping("/perfis/deletar")
    public String deletarPerfil(@RequestParam("id") String id) {
        perfilService.deletarPerfil(id);
        return "redirect:/perfis";
    }

    // Atualiza um perfil
    @PostMapping("/perfis/atualizar/{id}")
    public String atualizarPerfil(@PathVariable("id") String id, @ModelAttribute("perfil") Perfil perfil) {
        perfil.setId(id);
        perfilService.atualizarPerfil(id, perfil);
        return "redirect:/perfis";
    }

    // Lista todas as Sugestões De Jogos
    @GetMapping("/sugestoesDeJogos")
    public String listarSugestoesDeJogos(Model model) {
        List<SugestaoDeJogo> sugestoesDeJogos = sugestaoDeJogoService.listarTodasSugestaoDeJogo();
        model.addAttribute("sugestoesDeJogos", sugestoesDeJogos);
        String fragment = "sugestoesDeJogos :: content";
        log.info("Carregando fragmento: {}", fragment); // Log para depuração
        model.addAttribute("content", fragment);
        return "sugestoesDeJogos";
    }

    // Exibi o cadastro para registrar uma Sugestão De Jogo
    @GetMapping("/sugestoesDeJogos/novo")
    public String novaSugestaoDeJogo(Model model) {
        model.addAttribute("sugestaoDeJogo", new SugestaoDeJogo());
        return "novaSugestaoDeJogo";
    }

    // Exibi o cadastro para editar uma Sugestão De Jogo
    @GetMapping("/sugestoesDeJogos/editar/{id}")
    public String editarSugestaoDeJogo(@PathVariable("id") String id, Model model) {
        SugestaoDeJogo sugestaoDeJogo = sugestaoDeJogoService.buscarSugestaoDeJogoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Sugestao De Jogo não encontrado: " + id));
        model.addAttribute("sugestaoDeJogo", sugestaoDeJogo);
        return "editarSugestaoDeJogo";
    }

    // Salva uma nova Sugestão De Jogo
    @PostMapping("/sugestoesDeJogos/salvar")
    public String salvarSugestaoDeJogo(@ModelAttribute SugestaoDeJogo sugestaoDeJogo) {
        sugestaoDeJogoService.criarSugestaoDeJogo(sugestaoDeJogo);
        return "redirect:/sugestoesDeJogos";
    }

    // Deleta uma Sugestão De Jogo
    @PostMapping("/sugestoesDeJogos/deletar")
    public String deletarSugestaoDeJogo(@RequestParam("id") String id) {
        sugestaoDeJogoService.deletarSugestaoDeJogo(id);
        return "redirect:/sugestoesDeJogos";
    }

    // Atualiza uma Sugestão De Jogo
    @PostMapping("/sugestoesDeJogos/atualizar/{id}")
    public String atualizarSugestaoDeJogo(@PathVariable("id") String id, @ModelAttribute("sugestoes") SugestaoDeJogo sugestaoDeJogo) {
        sugestaoDeJogo.setId(id);
        sugestaoDeJogoService.atualizarSugestaoDeJogo(id, sugestaoDeJogo);
        return "redirect:/sugestoesDeJogos";
    }

    // Lista todos os Depoimentos
    @GetMapping("/depoimentos")
    public String listarDepoimentos(Model model) {
        List<Depoimento> depoimentos = depoimentoService.listarTodosDepoimentos();
        model.addAttribute("depoimentos", depoimentos);
        String fragment = "depoimentos :: content";
        log.info("Carregando fragmento: {}", fragment); // Log para depuração
        model.addAttribute("content", fragment);
        return "depoimentos";
    }

    // Exibi o cadastro para registrar um Depoimento
    @GetMapping("/depoimentos/novo")
    public String novoDepoimento(Model model) {
        model.addAttribute("depoimento", new Depoimento());
        return "novoDepoimento";
    }

    // Exibi o cadastro para editar um  Depoimento
    @GetMapping("/depoimentos/editar/{id}")
    public String editarDepoimento(@PathVariable("id") String id, Model model) {
        Depoimento depoimento = depoimentoService.buscarDepoimentoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Depoimento não encontrado: " + id));
        model.addAttribute("depoimento", depoimento);
        return "editarDepoimento";
    }

    // Salva um novo Depoimento
    @PostMapping("/depoimentos/salvar")
    public String salvarDepoimento(@ModelAttribute Depoimento Depoimento) {
        depoimentoService.criarDepoimento(Depoimento);
        return "redirect:/depoimentos";
    }

    // Deleta um Depoimento
    @PostMapping("/depoimentos/deletar")
    public String deletarDepoimento(@RequestParam("id") String id) {
        depoimentoService.deletarDepoimento(id);
        return "redirect:/depoimentos";
    }

    // Atualiza um Depoimento
    @PostMapping("/depoimentos/atualizar/{id}")
    public String atualizarDepoimento(@PathVariable("id") String id, @ModelAttribute("depoimentos") Depoimento depoimento) {
        depoimento.setId(id);
        depoimentoService.atualizarDepoimento(id, depoimento);
        return "redirect:/depoimentos";
    }
}