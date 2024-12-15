package com.tsubakistudio.codeAmigos_Security.exception;

public class EntityNotEnabledException extends RuntimeException{
    public EntityNotEnabledException(String message){
        super(message);
    }
}
