package br.com.leme.entities;

import java.time.LocalDate;
import java.util.UUID;

public class Usuario {
    private UUID id;
    private String nome;
    private String username;
    private String email;
    private String senha;
    private String area;
    private String acessibilidade;
    private int modulosConcluidos;
    private int xpTotal;
    private LocalDate dataCadastro;

    public Usuario() {
    }

    public Usuario(String nome, String username, String email, String senha, String area, String acessibilidade, int modulosConcluidos, int xpTotal, LocalDate dataCadastro) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.area = area;
        this.acessibilidade = acessibilidade;
        this.modulosConcluidos = modulosConcluidos;
        this.xpTotal = xpTotal;
        this.dataCadastro = dataCadastro;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAcessibilidade() {
        return acessibilidade;
    }

    public void setAcessibilidade(String acessibilidade) {
        this.acessibilidade = acessibilidade;
    }

    public int getModulosConcluidos() {
        return modulosConcluidos;
    }

    public void setModulosConcluidos(int modulosConcluidos) {
        this.modulosConcluidos = modulosConcluidos;
    }

    public int getXpTotal() {
        return xpTotal;
    }

    public void setXpTotal(int xpTotal) {
        this.xpTotal = xpTotal;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
