package com.springcraft.codingedu.config;

import com.springcraft.codingedu.domain.Role;
import com.springcraft.codingedu.domain.User;
import com.springcraft.codingedu.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestDataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public TestDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (userRepository.existsByEmail("test@test.com")) {
            return;
        }
        User user = new User("test@test.com", "test1");
        user.setPassword(passwordEncoder.encode("123412341234"));
        user.setEmailVerified(true);
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
    }
}
