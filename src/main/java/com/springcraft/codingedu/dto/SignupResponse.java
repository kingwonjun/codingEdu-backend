package com.springcraft.codingedu.dto;

public record SignupResponse(
        Long id,
        String email,
        String nickname,
        String status
) {
}
