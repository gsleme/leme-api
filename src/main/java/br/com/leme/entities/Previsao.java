package br.com.leme.entities;

import java.time.LocalDate;
import java.util.UUID;

public class Previsao {
    private UUID id;
    private UUID idUsuario;
    private double taxaSucesso;
    private String categoria;
    private LocalDate dataPrevisao;

    public Previsao() {
    }

    public Previsao(UUID idUsuario, double taxaSucesso, String categoria) {
        this.id = UUID.randomUUID();
        this.idUsuario = idUsuario;
        this.taxaSucesso = taxaSucesso;
        this.categoria = categoria;
        this.dataPrevisao = LocalDate.now();
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

    public double getTaxaSucesso() {
        return taxaSucesso;
    }

    public void setTaxaSucesso(double taxaSucesso) {
        this.taxaSucesso = taxaSucesso;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getDataPrevisao() {
        return dataPrevisao;
    }

    public void setDataPrevisao(LocalDate dataPrevisao) {
        this.dataPrevisao = dataPrevisao;
    }
}
