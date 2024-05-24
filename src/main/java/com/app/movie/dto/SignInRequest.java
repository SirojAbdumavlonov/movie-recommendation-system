package com.app.movie.dto;

import lombok.NonNull;

public record SignInRequest(@NonNull String email, @NonNull String username, @NonNull String password) {
}
