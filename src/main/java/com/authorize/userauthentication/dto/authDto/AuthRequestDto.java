package com.authorize.userauthentication.dto.authDto;

public record AuthRequestDto(
        String email,
        String password
) {
}
