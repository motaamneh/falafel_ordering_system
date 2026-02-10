package com.motaamneh.falafel.exception;

public class RestaurantDisabledException extends RuntimeException {
    public RestaurantDisabledException(String message) {
        super(message);
    }
}
