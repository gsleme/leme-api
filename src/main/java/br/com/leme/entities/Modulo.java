package br.com.leme.entities;

import java.util.UUID;

public class Modulo {
    private UUID id;
    private UUID idTrilha;
    private String titulo;
    private String descricao;
    private String tipo;
    private String conteudo;
    private int xpRecompensa;
    private String adaptacaoNecessaria;

    public Modulo() {
    }

    public Modulo(UUID idTrilha, String titulo, String descricao, String tipo, String conteudo, int xpRecompensa, String adaptacaoNecessaria) {
        this.id = UUID.randomUUID();
        this.idTrilha = idTrilha;
        this.titulo = titulo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.conteudo = conteudo;
        this.xpRecompensa = xpRecompensa;
        this.adaptacaoNecessaria = adaptacaoNecessaria;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdTrilha() {
        return idTrilha;
    }

    public void setIdTrilha(UUID idTrilha) {
        this.idTrilha = idTrilha;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public int getXpRecompensa() {
        return xpRecompensa;
    }

    public void setXpRecompensa(int xpRecompensa) {
        this.xpRecompensa = xpRecompensa;
    }

    public String getAdaptacaoNecessaria() {
        return adaptacaoNecessaria;
    }

    public void setAdaptacaoNecessaria(String adaptacaoNecessaria) {
        this.adaptacaoNecessaria = adaptacaoNecessaria;
    }
}
