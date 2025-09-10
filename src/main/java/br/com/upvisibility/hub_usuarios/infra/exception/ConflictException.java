package br.com.upvisibility.hub_usuarios.infra.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
