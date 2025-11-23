package br.com.leme.dto;

import java.time.LocalDateTime;

public record TrilhaResponseDTO (
    String id,
    String titulo,
    String descricao,
    String areaFoco,
    int xpTrilha,
    LocalDateTime dataCriacao
) {}
