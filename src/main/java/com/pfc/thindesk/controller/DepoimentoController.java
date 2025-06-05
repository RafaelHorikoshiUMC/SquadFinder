package com.pfc.thindesk.controller;

import com.pfc.thindesk.entity.Depoimento;
import com.pfc.thindesk.entity.Perfil;
import com.pfc.thindesk.service.DepoimentoService;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/depoimentos")
public class DepoimentoController {
    @Autowired
    private DepoimentoService depoimentoService;
    @Autowired
    private PerfilService perfilService;

    // Lista todos os Depoimentos
    @GetMapping
    public String listarDepoimentos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(required = false) String termo, Model model, RedirectAttributes redirectAttributes) {

        Optional<Perfil> meuPerfil = perfilService.listarPerfisDoUsuario().stream().findFirst();

        if (meuPerfil.isEmpty()) {
            redirectAttributes.addFlashAttribute("msgErro", "Você precisa criar um perfil antes de visualizar ou interagir com os depoimentos.");
            return "redirect:/perfis";
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Depoimento> depoimentosPage = depoimentoService.buscarDepoimentosPaginados(termo, pageable);

        String emailUsuarioLogado = SecurityContextHolder.getContext().getAuthentication().getName();
        Perfil perfilLogado = perfilService.buscarPerfilDoUsuarioLogado(emailUsuarioLogado).orElse(null);

        model.addAttribute("depoimentos", depoimentosPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", depoimentosPage.getTotalPages());
        model.addAttribute("perfilLogado", perfilLogado);
        model.addAttribute("termo", termo);

        return "depoimentos";
    }

    // Exibi o cadastro para registrar um Depoimento
    @GetMapping("/novo")
    public String novoDepoimento(Model model) {
        model.addAttribute("depoimento", new Depoimento());
        return "novoDepoimento";
    }

    // Exibi o cadastro para editar um  Depoimento
    @GetMapping("/editar/{id}")
    public String editarDepoimento(@PathVariable("id") String id, Model model) {
        Depoimento depoimento = depoimentoService.buscarDepoimentoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Depoimento não encontrado: " + id));
        model.addAttribute("depoimento", depoimento);
        return "editarDepoimento";
    }

    // Salva um novo Depoimento
    @PostMapping("/salvar")
    public String salvarDepoimento(@ModelAttribute Depoimento Depoimento, RedirectAttributes redirectAttributes) {
        depoimentoService.criarDepoimento(Depoimento);
        redirectAttributes.addFlashAttribute("msgSucesso", "Depoimento criado com sucesso!");
        return "redirect:/depoimentos";
    }

    // Deleta um Depoimento
    @PostMapping("/deletar")
    public String deletarDepoimento(@RequestParam("id") String id, RedirectAttributes redirectAttributes) {
        depoimentoService.deletarDepoimento(id);
        redirectAttributes.addFlashAttribute("msgSucesso", "Depoimento deletado com sucesso!");
        return "redirect:/depoimentos";
    }

    // Atualiza um Depoimento
    @PostMapping("/atualizar/{id}")
    public String atualizarDepoimento(@PathVariable("id") String id, @ModelAttribute("depoimentos") Depoimento depoimento, RedirectAttributes redirectAttributes) {
        depoimento.setId(id);
        depoimentoService.atualizarDepoimento(id, depoimento);
        redirectAttributes.addFlashAttribute("msgSucesso", "Depoimento atualizado com sucesso!");
        return "redirect:/depoimentos";
    }
}
