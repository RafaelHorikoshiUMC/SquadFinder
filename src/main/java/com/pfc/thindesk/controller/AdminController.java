package com.pfc.thindesk.controller;

import com.pfc.thindesk.entity.Perfil;
import com.pfc.thindesk.entity.Usuario;
import com.pfc.thindesk.repository.UsuarioRepository;
import com.pfc.thindesk.service.PerfilService;
import com.pfc.thindesk.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    PerfilService perfilService;

    // Página do administrador
    @GetMapping
    public String listarUsuarios(@RequestParam(required = false) String termo,
                                 @RequestParam(defaultValue = "0") int page,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, 25);

        Page<Usuario> usuarios = usuarioService.buscarUsuarios(termo, pageable);
        Page<Perfil> perfisPage = perfilService.buscarTodos(pageable);
        List<Perfil> perfis = perfisPage.getContent();

        // Mapeia cada ID de usuário para o apelido correspondente
        Map<String, String> apelidosPorUsuario = perfis.stream()
                .collect(Collectors.toMap(Perfil::getUsuarioId, Perfil::getApelido, (a1, a2) -> a1));

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("apelidosPorUsuario", apelidosPorUsuario);
        model.addAttribute("termo", termo);

        return "/usuarios";
    }





    // Deleta usuarios se for administrador
    @PostMapping("/deletar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deletarUsuario(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        usuarioService.deletarUsuario(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Usuário deletado com sucesso!");
        return "redirect:/admin";
    }

}
