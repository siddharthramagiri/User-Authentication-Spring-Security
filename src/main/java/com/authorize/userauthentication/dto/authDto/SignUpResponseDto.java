package com.authorize.userauthentication.dto.authDto;

public record SignUpResponseDto(
        Long user_id,
        String username,
        String message
) {
}
