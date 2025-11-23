package br.com.leme.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Previsao {
    private UUID id;
    private UUID idUsuario;
    private double taxaSucesso;
    private String categoria;
    private LocalDateTime dataPrevisao;

    public Previsao() {
    }

    public Previsao(UUID idUsuario, double taxaSucesso, String categoria) {
        this.id = UUID.randomUUID();
        this.idUsuario = idUsuario;
        this.taxaSucesso = taxaSucesso;
        this.categoria = categoria;
        this.dataPrevisao = LocalDateTime.now();
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

    public LocalDateTime getDataPrevisao() {
        return dataPrevisao;
    }

    public void setDataPrevisao(LocalDateTime dataPrevisao) {
        this.dataPrevisao = dataPrevisao;
    }
}
