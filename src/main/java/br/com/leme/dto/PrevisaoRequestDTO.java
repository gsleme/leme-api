package br.com.leme.dto;

public record PrevisaoRequestDTO(
    String area,
    String acessibilidade,
    int modulos_concluidos,
    int tempo_plataforma_dias
) {}
