package org.example.http;

public class BadHttpVersionException extends Exception {
    public BadHttpVersionException(String message) {
        super(message);
    }
}
