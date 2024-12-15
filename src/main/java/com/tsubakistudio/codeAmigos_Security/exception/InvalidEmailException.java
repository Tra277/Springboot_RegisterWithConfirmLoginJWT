package com.tsubakistudio.codeAmigos_Security.exception;

public class InvalidEmailException  extends RuntimeException{
    public InvalidEmailException(String msg){
        super(msg);
    }
}
