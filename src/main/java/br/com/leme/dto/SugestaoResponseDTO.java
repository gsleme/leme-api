package br.com.leme.dto;

import java.time.LocalDate;

public record SugestaoResponseDTO(
    String id,
    String idUsuario,
    String idTrilha,
    LocalDate dataSugestao
) {}
