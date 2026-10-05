package com.springcraft.codingedu.controller;

import com.springcraft.codingedu.dto.SignupRequest;
import com.springcraft.codingedu.dto.SignupResponse;
import com.springcraft.codingedu.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/signup")
    public SignupResponse signup(@Valid @RequestBody SignupRequest request) {
        return AuthService.signup(SignupResponse response);
    }

    @PostMapping("/email-verification/confirm")
    public

    @PostMapping("/auth/email-verification/resend")
    public


}
