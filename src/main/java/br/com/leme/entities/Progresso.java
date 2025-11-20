package br.com.leme.entities;

import java.time.LocalDate;
import java.util.UUID;

public class Progresso {
    private UUID id;
    private UUID idUsuario;
    private UUID idModulo;
    private LocalDate dataConclusao;

    public Progresso() {
    }

    public Progresso(UUID idUsuario, UUID idModulo, LocalDate dataConclusao) {
        this.id = UUID.randomUUID();
        this.idUsuario = idUsuario;
        this.idModulo = idModulo;
        this.dataConclusao = dataConclusao;
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

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
}

