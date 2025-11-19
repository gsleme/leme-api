package br.com.leme.dto;

import java.time.LocalDate;

public record UsuarioResponseDTO (
    String id,
    String nome,
    String username,
    String email,
    String area,
    String acessibilidade,
    int modulosConcluidos,
    int xpTotal,
    LocalDate dataCadastro
) {}
