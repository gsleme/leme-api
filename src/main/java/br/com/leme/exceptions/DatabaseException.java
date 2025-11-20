package br.com.leme.exceptions;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String operation, Throwable cause) {
        super(String.format("Não foi possível %s, verificar SQL. Causa: ", operation) + cause.getCause().getMessage());
    }
}