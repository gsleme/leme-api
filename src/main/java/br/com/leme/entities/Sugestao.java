package br.com.leme.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Sugestao {
    private UUID id;
    private UUID idUsuario;
    private UUID idTrilha;
    private LocalDateTime dataSugestao;

    public Sugestao() {
    }

    public Sugestao(UUID idUsuario, UUID idTrilha) {
        this.id = UUID.randomUUID();
        this.idUsuario = idUsuario;
        this.idTrilha = idTrilha;
        this.dataSugestao = LocalDateTime.now();
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

    public UUID getIdTrilha() {
        return idTrilha;
    }

    public void setIdTrilha(UUID idTrilha) {
        this.idTrilha = idTrilha;
    }

    public LocalDateTime getDataSugestao() {
        return dataSugestao;
    }

    public void setDataSugestao(LocalDateTime dataSugestao) {
        this.dataSugestao = dataSugestao;
    }
}
