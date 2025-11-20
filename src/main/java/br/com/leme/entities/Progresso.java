package br.com.leme.entities;

import java.time.LocalDate;
import java.util.UUID;

public class Progresso {
    private UUID id;
    private String idUsuario;
    private String idModulo;
    private LocalDate dataConclusao;

    public Progresso() {
    }

    public Progresso(String idUsuario, String idModulo, LocalDate dataConclusao) {
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

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(String idModulo) {
        this.idModulo = idModulo;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
}

