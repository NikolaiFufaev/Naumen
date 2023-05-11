package ru.fufaev.naumen;

public class ErrorResponse {
    String message;

    public ErrorResponse(String message) {
        this.message = message;

    }

    public String getError() {
        return message;
    }

}