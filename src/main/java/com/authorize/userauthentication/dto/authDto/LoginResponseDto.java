package com.authorize.userauthentication.dto.authDto;

import java.util.Set;

public record LoginResponseDto(
        String token,
        Long user_id,
        Set<String> role
) {
}
