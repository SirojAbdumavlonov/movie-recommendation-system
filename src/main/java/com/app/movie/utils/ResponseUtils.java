package com.app.movie.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class ResponseUtils {
    public static <T> ResponseEntity<T> wrapOrNotFound(Optional<T> optionalArgument) {
        return optionalArgument.map(t -> new ResponseEntity<>(t, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
