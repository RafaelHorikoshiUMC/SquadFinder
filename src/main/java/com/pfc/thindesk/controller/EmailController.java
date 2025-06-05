package com.pfc.thindesk.controller;

import com.pfc.thindesk.email.dtos.RecoverDto;
import com.pfc.thindesk.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {

    @Autowired
    private UsuarioService usuarioService;

    // Exibe o formulário para digitar o e-mail de recuperação
    @GetMapping("/recuperar")
    public String mostrarFormularioRecuperarSenha() {
        return "recuperar"; // página para digitar o email
    }

    // Processa o envio do e-mail de recuperação de senha
    @PostMapping("/recuperar")
    public String processarPedidoRecuperarSenha(@RequestParam("email") String email, Model model) {
        try {
            usuarioService.sendEmailRecoverPassword(new RecoverDto(email)); // envia o e-mail com link
            return "redirect:/recuperar-enviado"; // redireciona para página de confirmação
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage()); // exibe erro na mesma página
            return "recuperar";
        }
    }

    // Exibe a confirmação de que o e-mail foi enviado
    @GetMapping("/recuperar-enviado")
    public String paginaEmailEnviado() {
        return "recuperar-enviado";
    }

    // Exibe o formulário para o usuário atualizar a senha usando o token
    @GetMapping("/atualizar-senha/{token}")
    public String mostrarFormularioAtualizarSenha(@PathVariable String token, Model model) {
        model.addAttribute("token", token); // passa o token para o formulário
        return "atualizar-senha";
    }

    // Processa a atualização da senha usando o token
    @PostMapping("/atualizar-senha/{token}")
    public String atualizarSenha(
            @PathVariable String token,
            @RequestParam("senha") String senha,
            Model model
    ) {
        try {
            usuarioService.updatePassword(token, senha); // atualiza a senha
            return "redirect:/senha-atualizada"; // redireciona para confirmação
        } catch (Exception e) {
            model.addAttribute("mensagemErro", e.getMessage()); // mostra erro, permanece na tela
            model.addAttribute("token", token);
            return "recuperar-senha";
        }
    }

    // Exibe confirmação de que a senha foi atualizada com sucesso
    @GetMapping("/senha-atualizada")
    public String paginaSenhaAtualizada() {
        return "senha-atualizada";
    }
}
