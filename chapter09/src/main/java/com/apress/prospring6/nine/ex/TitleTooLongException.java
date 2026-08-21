package com.apress.prospring6.nine.ex;

public class TitleTooLongException extends Exception {

    public TitleTooLongException(String message) {
        super(message);
    }

    public TitleTooLongException(String message, Throwable cause) {
        super(message, cause);
    }
}
