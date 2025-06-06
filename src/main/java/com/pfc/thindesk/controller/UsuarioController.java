package com.pfc.thindesk.controller;

import com.pfc.thindesk.email.dtos.RecoverDto;
import com.pfc.thindesk.email.dtos.RecoverPasswordDto;
import com.pfc.thindesk.entity.Usuario;
import com.pfc.thindesk.repository.UsuarioRepository;
import com.pfc.thindesk.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Exibi o cadastro para registrar um usuario
    @GetMapping("/novo")
    public String novoCadastroUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "novoUsuario";
    }

    // Salva um novo usuario
    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario, Model model, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.criarUsuario(usuario);
            redirectAttributes.addFlashAttribute("msgSucesso", "Usuario cadastrado com sucesso!");
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("erro", e.getMessage());
            return "novoUsuario";
        }
    }
}
