package com.example.API.MICROSERVICES.controller;

import com.example.API.MICROSERVICES.client.TarefaClient;
import com.example.API.MICROSERVICES.dto.TarefaRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gateway/tarefas")
public class GatewayController {

    @Autowired
    private TarefaClient tarefaClient;

    @GetMapping
    public List<TarefaRequestDTO> listarTarefas() {
        return tarefaClient.listarTarefas();
    }

    @GetMapping("/{id}")
    public TarefaRequestDTO obterTarefa(@PathVariable Long id) {
        return tarefaClient.obterTarefa(id);
    }

    @PostMapping
    public TarefaRequestDTO criarTarefa(@RequestBody TarefaRequestDTO tarefa) {
        return tarefaClient.criarTarefa(tarefa);
    }

    @PutMapping("/{id}")
    public TarefaRequestDTO atualizarTarefa(@PathVariable Long id, @RequestBody TarefaRequestDTO tarefa) {
        return tarefaClient.atualizarTarefa(id, tarefa);
    }

    @DeleteMapping("/{id}")
    public void deletarTarefa(@PathVariable Long id) {
        tarefaClient.deletarTarefa(id);
    }
}
