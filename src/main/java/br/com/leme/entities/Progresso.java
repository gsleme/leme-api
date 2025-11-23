package br.com.leme.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Progresso {
    private UUID id;
    private UUID idUsuario;
    private UUID idModulo;
    private LocalDateTime dataConclusao;

    public Progresso() {
    }

    public Progresso(UUID idUsuario, UUID idModulo) {
        this.id = UUID.randomUUID();
        this.idUsuario = idUsuario;
        this.idModulo = idModulo;
        this.dataConclusao = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public UUID getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(UUID idModulo) {
        this.idModulo = idModulo;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
}

