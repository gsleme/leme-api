package br.com.leme.dto;

public record SugestaoRequestDTO(
    String area,
    String acessibilidade,
    String modulos_concluidos,
    String tempo_plataforma_dias
) {}
