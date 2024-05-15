package com.app.movie.model;

import lombok.Builder;


@Builder
public record SignUpRequest(String username, String email, String password) {

}
