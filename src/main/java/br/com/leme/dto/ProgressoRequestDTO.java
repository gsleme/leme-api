package br.com.leme.dto;

import java.time.LocalDateTime;

public record ProgressoRequestDTO(
    String idUsuario,
    String idModulo
) {}
