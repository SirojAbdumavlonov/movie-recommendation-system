package com.app.movie.dto;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record SignUpRequest(@NonNull String username, @NonNull String email, @NonNull String password) {

}
