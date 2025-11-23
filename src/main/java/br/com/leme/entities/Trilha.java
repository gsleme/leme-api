package br.com.leme.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Trilha {
    private UUID id;
    private String titulo;
    private String descricao;
    private String areaFoco;
    private int xpTrilha;
    private LocalDateTime dataCriacao;

    public Trilha() {
    }

    public Trilha(String titulo, String descricao, String areaFoco, int xpTrilha) {
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.descricao = descricao;
        this.areaFoco = areaFoco;
        this.xpTrilha = xpTrilha;
        this.dataCriacao = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAreaFoco() {
        return areaFoco;
    }

    public void setAreaFoco(String areaFoco) {
        this.areaFoco = areaFoco;
    }

    public int getXpTrilha() {
        return xpTrilha;
    }

    public void setXpTrilha(int xpTrilha) {
        this.xpTrilha = xpTrilha;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
