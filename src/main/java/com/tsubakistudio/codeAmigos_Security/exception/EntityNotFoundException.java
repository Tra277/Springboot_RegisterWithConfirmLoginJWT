package com.tsubakistudio.codeAmigos_Security.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String msg){
        super(msg);
    }
}
