package br.com.leme.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("Email não cadastrado.");
    }

    public EntityNotFoundException(String entity) {
        super(String.format("Não foi possível encontrar %s com esse id.", entity));
    }
}