package com.tsubakistudio.codeAmigos_Security.exception;

public class EntityAlreadyExistException extends RuntimeException{
    public EntityAlreadyExistException(String message){
        super(message);
    }
}
