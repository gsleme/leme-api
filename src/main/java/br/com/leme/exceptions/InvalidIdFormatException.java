package br.com.leme.exceptions;

public class InvalidIdFormatException extends RuntimeException {
    public InvalidIdFormatException () {
        super("|ERRO|: id não esta dentro dos padrões de UUID.");
    }
}
