package br.com.leme.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("|ERRO|: email não cadastrado.");
    }

    public EntityNotFoundException(String entity) {
        super(String.format("|ERRO|: não foi possível encontrar %s com esse id.", entity));
    }
}