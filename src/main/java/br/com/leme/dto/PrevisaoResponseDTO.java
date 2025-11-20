package br.com.leme.dto;

import java.time.LocalDate;

public record PrevisaoResponseDTO(
    String id,
    String idUsuario,
    double taxaSucesso,
    String categoria,
    LocalDate dataPrevisao
) {}
