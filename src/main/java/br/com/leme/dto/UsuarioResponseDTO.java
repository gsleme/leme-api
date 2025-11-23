package br.com.leme.dto;

import java.time.LocalDateTime;

public record UsuarioResponseDTO (
    String id,
    String nome,
    String username,
    String email,
    String area,
    String acessibilidade,
    int modulosConcluidos,
    int xpTotal,
    LocalDateTime dataCadastro
) {}
