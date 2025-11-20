package br.com.leme.dto;

import java.time.LocalDate;

public record ProgressoRequestDTO(
    String idUsuario,
    String idModulo,
    LocalDate dataConclusao
) {}
