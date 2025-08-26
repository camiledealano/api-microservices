package com.example.service;

import com.example.model.Tarefa;
import com.example.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    public Tarefa obterTarefa(Long id) {
        return tarefaRepository.findById(id).orElse(null);
    }

    public Tarefa criarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public Tarefa atualizarTarefa(Long id, Tarefa tarefa) {
        if (tarefaRepository.existsById(id)) {
            tarefa.setId(id);
            return tarefaRepository.save(tarefa);
        }
        return null;
    }

    public void deletarTarefa(Long id) {
        tarefaRepository.deleteById(id);
    }
}
