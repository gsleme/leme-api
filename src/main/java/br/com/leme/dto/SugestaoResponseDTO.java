package br.com.leme.dto;

import java.time.LocalDateTime;

public record SugestaoResponseDTO(
    String id,
    String idUsuario,
    String idTrilha,
    LocalDateTime dataSugestao
) {}
