package com.springcraft.codingedu.service;

import com.springcraft.codingedu.domain.User;
import com.springcraft.codingedu.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(String email, String password, String nickname) {

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일 입니다.");
        }

        email = email.strip().toLowerCase();
        nickname = nickname.strip();
        if (nickname.length() == 1) {

        }
        User user = new User();

        userRepository.save(user);
    }
}
