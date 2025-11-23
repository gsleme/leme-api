package br.com.leme.dto;

import java.time.LocalDateTime;

public record PrevisaoResponseDTO(
    String id,
    String idUsuario,
    double taxaSucesso,
    String categoria,
    LocalDateTime dataPrevisao
) {}
