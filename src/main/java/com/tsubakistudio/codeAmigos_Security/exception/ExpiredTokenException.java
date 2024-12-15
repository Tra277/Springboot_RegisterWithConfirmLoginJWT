package com.tsubakistudio.codeAmigos_Security.exception;

public class ExpiredTokenException extends RuntimeException{
    public ExpiredTokenException(String message){
        super(message);
    }
}
