package com.app.movie.service.exceptions;

import lombok.NonNull;

import java.io.Serial;

public class EmailAlreadyUsedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException(@NonNull String email) {
        super(String.format("Email already used: %s", email));
    }
}
