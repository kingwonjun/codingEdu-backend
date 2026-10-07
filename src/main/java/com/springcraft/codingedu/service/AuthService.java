package com.springcraft.codingedu.service;

import com.resend.core.exception.ResendException;
import com.springcraft.codingedu.domain.Role;
import com.springcraft.codingedu.domain.User;
import com.springcraft.codingedu.dto.SignupRequest;
import com.springcraft.codingedu.dto.SignupResponse;
import com.springcraft.codingedu.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            EmailVerificationService emailVerificationService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailVerificationService = emailVerificationService;
    }

    public SignupResponse signup(String email, String password, String nickname) throws ResendException {

        email = email.strip().toLowerCase();
        nickname = nickname.strip();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임 입니다.");
        }

        if (nickname.length() < 2) {
            throw new IllegalArgumentException("닉네임은 2자 이상이어야 합니다");
        }

        User user = new User(email, nickname);
        String encodePassword = passwordEncoder.encode(password);
        user.setPassword(encodePassword);
        user.setRole(Role.ROLE_USER);

        User savedUser = userRepository.save(user);
        emailVerificationService.createAndSendCode(user);

        return new SignupResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getNickname(),
                "PENDING_EMAIL"
        );
    }

    public void verifyEmail(Long userId) throws ResendException {
        User user = userRepository.findById(userId).orElseThrow();
        emailVerificationService.resend(user);
        user.setEmailVerified(true);
    }
}
