package com.projetozero.usuario.infrastructure.exception;

public class ConflictException extends  RuntimeException {

    public ConflictException(String msg) {
        super(msg);
    }

    public ConflictException(String msg, Throwable throwable) {
        super(msg);
    }
}
