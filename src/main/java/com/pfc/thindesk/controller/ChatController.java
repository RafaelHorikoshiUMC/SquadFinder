package com.pfc.thindesk.controller;

import com.pfc.thindesk.entity.Mensagem;
import com.pfc.thindesk.entity.Perfil;
import com.pfc.thindesk.entity.Usuario;
import com.pfc.thindesk.repository.MensagemRepository;
import com.pfc.thindesk.repository.PerfilRepository;
import com.pfc.thindesk.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @GetMapping("/{destinatarioId}")
    public String abrirChat(@PathVariable String destinatarioId, Model model, Principal principal) {
        String email = principal.getName();
        Usuario usuarioLogado = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Perfil perfilLogado = perfilRepository.findByUsuarioId(usuarioLogado.getId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));



        List<Mensagem> mensagens = mensagemRepository
                .findByRemetenteIdAndDestinatarioIdOrRemetenteIdAndDestinatarioIdOrderByDataHora(
                        perfilLogado.getId(), destinatarioId,
                        destinatarioId, perfilLogado.getId()
                );

        Perfil destinatario = perfilRepository.findById(destinatarioId).orElse(null);

        model.addAttribute("mensagens", mensagens);
        model.addAttribute("perfilLogado", perfilLogado);
        model.addAttribute("destinatario", destinatario);
        model.addAttribute("novaMensagem", new Mensagem());

        return "chat";
    }

    @PostMapping("/enviar")
    public String enviarMensagem(@ModelAttribute Mensagem novaMensagem, Principal principal) {
        String email = principal.getName();
        Usuario usuarioLogado = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Perfil perfilLogado = perfilRepository.findByUsuarioId(usuarioLogado.getId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        novaMensagem.setRemetenteId(perfilLogado.getId());
        novaMensagem.setDataHora(LocalDateTime.now());

        mensagemRepository.save(novaMensagem);

        return "redirect:/chat/" + novaMensagem.getDestinatarioId();
    }
}

