package br.com.leme.dto;

import java.time.LocalDateTime;

public record ProgressoResponseDTO(
    String id,
    String idUsuario,
    String idModulo,
    LocalDateTime dataConclusao
) {}
