package com.app.movie.service.exceptions;

public class EmailNotUsedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmailNotUsedException() {
        super("Email not used");
    }
}