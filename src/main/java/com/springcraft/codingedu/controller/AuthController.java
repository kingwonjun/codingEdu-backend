package com.springcraft.codingedu.controller;

import com.resend.core.exception.ResendException;
import com.springcraft.codingedu.dto.*;
import com.springcraft.codingedu.service.AuthService;
import com.springcraft.codingedu.service.EmailVerificationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final EmailVerificationService emailVerificationService;

    public AuthController(AuthService authService, EmailVerificationService emailVerificationService) {
        this.authService = authService;
        this.emailVerificationService = emailVerificationService;
    }

    @PostMapping("/signup")
    public SignupResponse signup(@Valid @RequestBody SignupRequest request) throws ResendException {
        return authService.signup(
                request.email(),
                request.password(),
                request.nickname()
        );
    }

    // 회원가입 즉시 이메일 보내기 ->
    @PostMapping("/email/verify")
    public EmailVerifyResponse emailVerify(@RequestBody EmailVerifyRequest request) {
        emailVerificationService.verify(request.id(), request.code());

        return
    }


}
