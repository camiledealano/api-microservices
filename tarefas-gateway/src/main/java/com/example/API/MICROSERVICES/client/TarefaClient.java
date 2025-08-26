package com.example.API.MICROSERVICES.client;

import com.example.API.MICROSERVICES.dto.TarefaRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "tarefas-service", url = "http://localhost:8082/tarefas")
public interface TarefaClient {

    @GetMapping
    List<TarefaRequestDTO> listarTarefas();

    @GetMapping("/{id}")
    TarefaRequestDTO obterTarefa(@PathVariable("id") Long id);

    @PostMapping
    TarefaRequestDTO criarTarefa(@RequestBody TarefaRequestDTO tarefa);

    @PutMapping("/{id}")
    TarefaRequestDTO atualizarTarefa(@PathVariable("id") Long id, @RequestBody TarefaRequestDTO tarefa);

    @DeleteMapping("/{id}")
    void deletarTarefa(@PathVariable("id") Long id);
}
