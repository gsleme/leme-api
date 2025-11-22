package br.com.leme.exceptions;

public class ClientAPIException extends RuntimeException {
    public ClientAPIException(String message) {
        super(message);
    }

    public ClientAPIException(Throwable cause) {
        super("Erro envolvendo chamada da API externa, consulte servidor. Causa: " + cause);
    }
}
