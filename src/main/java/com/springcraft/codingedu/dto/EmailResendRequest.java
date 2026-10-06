package com.springcraft.codingedu.dto;

public record EmailResendRequest(
        String email,
        String code
) {
}
