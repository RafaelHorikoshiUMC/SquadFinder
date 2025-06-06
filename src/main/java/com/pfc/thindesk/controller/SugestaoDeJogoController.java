package com.pfc.thindesk.controller;

import com.pfc.thindesk.entity.Perfil;
import com.pfc.thindesk.entity.SugestaoDeJogo;
import com.pfc.thindesk.repository.SugestaoDeJogoRepository;
import com.pfc.thindesk.service.PerfilService;
import com.pfc.thindesk.service.SugestaoDeJogoService;
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
@RequestMapping("/sugestoesDeJogos")
public class SugestaoDeJogoController {

    @Autowired
    private SugestaoDeJogoService sugestaoDeJogoService;
    @Autowired
    private SugestaoDeJogoRepository sugestaoDeJogoRepository;
    @Autowired
    private PerfilService perfilService;

    // Lista todas as Sugestões De Jogos
    @GetMapping
    public String listarSugestoesDeJogos(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(required = false) String termo, RedirectAttributes redirectAttributes) {

        Optional<Perfil> meuPerfil = perfilService.listarPerfisDoUsuario().stream().findFirst();

        if (meuPerfil.isEmpty()) {
            redirectAttributes.addFlashAttribute("msgErro", "Você precisa criar um perfil antes de visualizar ou interagir com as sugestões de jogos.");
            return "redirect:/perfis";
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<SugestaoDeJogo> sugestoesPage = sugestaoDeJogoService.buscarSugestoesPaginadas(termo, pageable);

        String emailUsuarioLogado = SecurityContextHolder.getContext().getAuthentication().getName();
        Perfil perfilLogado = perfilService.buscarPerfilDoUsuarioLogado(emailUsuarioLogado).orElse(null);

        model.addAttribute("sugestoesDeJogos", sugestoesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", sugestoesPage.getTotalPages());
        model.addAttribute("perfilLogado", perfilLogado);
        model.addAttribute("termo", termo);

        return "sugestoesDeJogos";
    }

    // Exibi o cadastro para registrar uma Sugestão De Jogo
    @GetMapping("/novo")
    public String novaSugestaoDeJogo(Model model) {
        model.addAttribute("sugestaoDeJogo", new SugestaoDeJogo());
        return "novaSugestaoDeJogo";
    }

    // Exibi o cadastro para editar uma Sugestão De Jogo
    @GetMapping("/editar/{id}")
    public String editarSugestaoDeJogo(@PathVariable("id") String id, Model model) {
        SugestaoDeJogo sugestaoDeJogo = sugestaoDeJogoService.buscarSugestaoDeJogoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Sugestao De Jogo não encontrado: " + id));
        model.addAttribute("sugestaoDeJogo", sugestaoDeJogo);
        return "editarSugestaoDeJogo";
    }

    // Salva uma nova Sugestão De Jogo
    @PostMapping("/salvar")
    public String salvarSugestaoDeJogo(@ModelAttribute SugestaoDeJogo sugestaoDeJogo, RedirectAttributes redirectAttributes) {
        sugestaoDeJogoService.criarSugestaoDeJogo(sugestaoDeJogo);
        redirectAttributes.addFlashAttribute("msgSucesso", "Sugestão De Jogo criada com sucesso!");
        return "redirect:/sugestoesDeJogos";
    }

    // Deleta uma Sugestão De Jogo
    @PostMapping("/deletar")
    public String deletarSugestaoDeJogo(@RequestParam("id") String id, RedirectAttributes redirectAttributes) {
        sugestaoDeJogoService.deletarSugestaoDeJogo(id);
        redirectAttributes.addFlashAttribute("msgSucesso", "Sugestão De Jogo deletada com sucesso!");
        return "redirect:/sugestoesDeJogos";
    }

    // Atualiza uma Sugestão De Jogo
    @PostMapping("/atualizar/{id}")
    public String atualizarSugestaoDeJogo(@PathVariable String id, @ModelAttribute SugestaoDeJogo sugestaoAtualizada) {
        SugestaoDeJogo sugestaoExistente = sugestaoDeJogoRepository.findById(id).orElseThrow();

        sugestaoExistente.setNomeDoJogoSugerido(sugestaoAtualizada.getNomeDoJogoSugerido());
        sugestaoExistente.setDescricaoOpcional(sugestaoAtualizada.getDescricaoOpcional());

        sugestaoDeJogoRepository.save(sugestaoExistente);
        return "redirect:/sugestoesDeJogos";
    }

    //O Admin marca como feito
    @PostMapping("/feito/{id}")
    public String marcarComoFeito(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            sugestaoDeJogoService.marcarComoFeito(id);
            redirectAttributes.addFlashAttribute("msgSucesso", "Sugestão marcada como feita!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msgErro", e.getMessage());
        }
        return "redirect:/sugestoesDeJogos";
    }


}
