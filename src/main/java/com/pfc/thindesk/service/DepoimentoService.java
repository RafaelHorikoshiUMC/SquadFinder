package com.pfc.thindesk.service;

import com.pfc.thindesk.entity.Depoimento;
import com.pfc.thindesk.repository.DepoimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DepoimentoService {

    @Autowired
    private DepoimentoRepository depoimentoRepository;

    // Cria um novo Depoimento
    public Depoimento criarDepoimento(Depoimento depoimento) {
        LocalDateTime agora = LocalDateTime.now();
        depoimento.setDataCriacao(agora);
        depoimento.setDataAtualizacao(agora);
        return depoimentoRepository.save(depoimento);
    }

    // Busca todos os Depoimentos
    public List<Depoimento> listarTodosDepoimentos() {
        return depoimentoRepository.findAll();
    }

    // Busca o Depoimento por ID
    public Optional<Depoimento> buscarDepoimentoPorId(String id) {
        return depoimentoRepository.findById(id);
    }

    // Atualizar um Depoimento
    public Depoimento atualizarDepoimento(String id, Depoimento depoimento) {
        Optional<Depoimento> existente = depoimentoRepository.findById(id);
        if (existente.isPresent()) {
            Depoimento original = existente.get();
            original.setTexto(depoimento.getTexto());
            original.setDataAtualizacao(LocalDateTime.now());
            return depoimentoRepository.save(original);
        }
        return null;
    }

    // Deleta um Depoimento
    public void deletarDepoimento(String id) {
        depoimentoRepository.deleteById(id);
    }
}