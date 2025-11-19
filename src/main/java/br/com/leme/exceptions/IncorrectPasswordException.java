package br.com.leme.exceptions;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
        super("|ERRO|: senha incorreta.");
    }
}
