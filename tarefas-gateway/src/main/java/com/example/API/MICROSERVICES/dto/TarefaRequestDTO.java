package com.example.API.MICROSERVICES.dto;

import com.example.API.MICROSERVICES.dto.enums.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TarefaRequestDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private Prioridade prioridade;
    private Status status;
    private LocalDate dataLimite;
}
