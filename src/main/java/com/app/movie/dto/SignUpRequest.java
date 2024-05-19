package com.app.movie.dto;

import lombok.Builder;


@Builder
public record SignUpRequest(String username, String email, String password) {

}
