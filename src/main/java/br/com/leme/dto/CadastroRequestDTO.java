package br.com.leme.dto;

import java.time.LocalDate;

public record CadastroRequestDTO (
    String nome,
    String username,
    String email,
    String senha,
    String area,
    String acessibilidade,
    int modulosConcluidos,
    int xpTotal,
    LocalDate dataCadastro
) {}
