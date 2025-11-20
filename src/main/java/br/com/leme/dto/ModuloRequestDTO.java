package br.com.leme.dto;

public record ModuloRequestDTO(
    String idTrilha,
    String titulo,
    String descricao,
    String tipo,
    String conteudo,
    int xpRecompensa,
    String adaptacaoNecessaria
) {}
