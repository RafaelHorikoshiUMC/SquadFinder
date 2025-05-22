package com.pfc.thindesk.service;

import com.pfc.thindesk.entity.Usuario;
import com.pfc.thindesk.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Cria um novo Usuario
    public Usuario criarUsuario(Usuario usuario) {
        if (usuarioRepository.findByNome(usuario.getNome()).isPresent()) {
            throw new RuntimeException("Nome de usuário já está em uso.");
        }

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já está em uso.");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    // Busca todos os Usuarios
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Busca o Usuario por ID
    public Optional<Usuario> buscarUsuarioPorId(String id) {
        return usuarioRepository.findById(id);
    }

    // Busca o Usuario por Email
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o e-mail: " + email));
    }

    // Atualizar um Usuario
    public Usuario atualizarUsuario(String id, Usuario usuario) {
        Optional<Usuario> existente = usuarioRepository.findById(id);
        if (existente.isPresent()) {
            Usuario atual = existente.get();

            // Evitar mudança para email/nome já existentes em outros usuários
            if (!atual.getEmail().equals(usuario.getEmail()) &&
                    usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
                throw new RuntimeException("E-mail já está em uso.");
            }

            if (!atual.getNome().equals(usuario.getNome()) &&
                    usuarioRepository.findByNome(usuario.getNome()).isPresent()) {
                throw new RuntimeException("Nome de usuário já está em uso.");
            }

            usuario.setId(id);

            // Se for atualizar senha, encode novamente
            if (!usuario.getSenha().equals(atual.getSenha())) {
                usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            }

            return usuarioRepository.save(usuario);
        } else {
            return null;
        }
    }

    // Deleta um Usuario
    public void deletarUsuario(String id) {
        usuarioRepository.deleteById(id);
    }
}
