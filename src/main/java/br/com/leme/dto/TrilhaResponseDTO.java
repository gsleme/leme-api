package br.com.leme.dto;

import java.time.LocalDate;

public record TrilhaResponseDTO (
    String id,
    String titulo,
    String descricao,
    String areaFoco,
    int xpTrilha,
    LocalDate dataCriacao
) {}
