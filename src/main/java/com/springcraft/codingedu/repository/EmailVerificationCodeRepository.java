package com.springcraft.codingedu.repository;

import com.springcraft.codingedu.domain.EmailVerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationCodeRepository extends JpaRepository<EmailVerificationCode, Long> {

    Optional<EmailVerificationCode> findByUser_Id(Long userId);
}
