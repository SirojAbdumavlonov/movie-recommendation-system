package com.app.movie.service;

public class EmailNotUsedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmailNotUsedException() {
        super("Email not used");
    }
}
