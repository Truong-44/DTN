package com.noithat.backend.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BadRequestException extends RuntimeException {
    private String path;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, String path) {
        super(message);
        this.path = path;
    }
}
