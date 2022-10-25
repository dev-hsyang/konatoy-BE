package com.konai.hsyang.konatoybe.exceptions;

public class NoUserFoundException extends RuntimeException{

    public NoUserFoundException(){

        super("사용자를 찾지 못하였습니다.");
    }

    public NoUserFoundException(String message){

        super(message);
    }
}
