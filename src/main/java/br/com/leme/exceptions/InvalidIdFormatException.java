package br.com.leme.exceptions;

public class InvalidIdFormatException extends RuntimeException {
    public InvalidIdFormatException () {
        super("Id não esta dentro dos padrões de UUID.");
    }
}
