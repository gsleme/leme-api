package br.com.leme.dto;

public record SugestaoRequestDTO(
    String area,
    String acessibilidade,
    int modulos_concluidos,
    int tempo_plataforma_dias
) {}
