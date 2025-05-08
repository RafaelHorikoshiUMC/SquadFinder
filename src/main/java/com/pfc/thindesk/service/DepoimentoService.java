package com.pfc.thindesk.service;

import com.pfc.thindesk.entity.Depoimento;
import com.pfc.thindesk.repository.DepoimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepoimentoService {

    @Autowired
    private DepoimentoRepository depoimentoRepository;

    // Cria um novo Depoimento
    public Depoimento criarDepoimento(Depoimento depoimento) {
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
        if (depoimentoRepository.existsById(id)) {
            depoimento.setId(id);
            return depoimentoRepository.save(depoimento);
        } else {
            return null;
        }
    }

    // Deleta um Depoimento
    public void deletarDepoimento(String id) {
        depoimentoRepository.deleteById(id);
    }
}