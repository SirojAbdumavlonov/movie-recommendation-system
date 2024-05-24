package com.app.movie.service.exceptions;

import java.io.Serial;

public class UsernameAlreadyUsedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameAlreadyUsedException(String username) {
        super(String.format("Login name already used!: %s", username));
    }
}
