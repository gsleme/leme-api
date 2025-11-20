package br.com.leme.dto;

public record ModuloResponseDTO(
    String id,
    String idTrilha,
    String titulo,
    String descricao,
    String tipo,
    String conteudo,
    int xpRecompensa,
    String adaptacaoNecessaria
) {}
