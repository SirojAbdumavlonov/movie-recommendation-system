package com.app.movie.service.exceptions;

import lombok.NonNull;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(@NonNull String email) {
        super(String.format("Email not used: %s", email));
    }
}
