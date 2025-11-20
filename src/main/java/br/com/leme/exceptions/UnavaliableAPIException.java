package br.com.leme.exceptions;

public class UnavaliableAPIException extends RuntimeException {
    public UnavaliableAPIException() {
        super("API externa indisponível ou endereço inválido. Verificar disponibilidade do servidor.");
    }
}
