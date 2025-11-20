package br.com.leme.dto;

import java.time.LocalDate;

public record ProgressoResponseDTO(
    String id,
    String idUsuario,
    String idModulo,
    LocalDate dataConclusao
) {}
