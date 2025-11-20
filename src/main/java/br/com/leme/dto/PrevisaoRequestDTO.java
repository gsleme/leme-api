package br.com.leme.dto;

public record PrevisaoRequestDTO(
    String area,
    String acessibilidade,
    String modulos_concluidos,
    String tempo_plataforma_dias
) {}
