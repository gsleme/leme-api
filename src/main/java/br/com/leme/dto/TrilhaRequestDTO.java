package br.com.leme.dto;

public record TrilhaRequestDTO(
    String titulo,
    String descricao,
    String areaFoco,
    int xpTrilha
) {}
