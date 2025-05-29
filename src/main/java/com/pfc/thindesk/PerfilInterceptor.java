package com.pfc.thindesk;

import com.pfc.thindesk.entity.Usuario;
import com.pfc.thindesk.service.PerfilService;
import com.pfc.thindesk.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.Principal;

@Component
public class PerfilInterceptor implements HandlerInterceptor {

    private final UsuarioService usuarioService;
    private final PerfilService perfilService;

    public PerfilInterceptor(UsuarioService usuarioService, PerfilService perfilService) {
        this.usuarioService = usuarioService;
        this.perfilService = perfilService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            Usuario usuario = usuarioService.buscarPorEmail(principal.getName());

            boolean temPerfil = perfilService.buscarPorUsuario(usuario).isPresent();

            // Evita redirecionamento em páginas relacionadas à criação de perfil
            String uri = request.getRequestURI();
            if (!temPerfil && !uri.startsWith("/perfis/novo") && !uri.startsWith("/logout")) {
                response.sendRedirect("/perfis/novo");
                return false;
            }
        }

        return true;
    }
}
