package com.exchange.rate;

public class ConversionExeption extends RuntimeException {
    private String message;

    public ConversionExeption(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
