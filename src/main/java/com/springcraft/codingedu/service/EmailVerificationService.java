package com.springcraft.codingedu.service;

import com.resend.core.exception.ResendException;
import com.springcraft.codingedu.domain.EmailVerificationCode;
import com.springcraft.codingedu.domain.User;
import com.springcraft.codingedu.dto.EmailResendResponse;
import com.springcraft.codingedu.dto.EmailVerifyResponse;
import com.springcraft.codingedu.repository.EmailVerificationCodeRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailVerificationService {

    private final EmailService emailVerificationService;
    private final EmailVerificationCodeRepository emailVerificationCodeRepository;

    public EmailVerificationService(EmailService emailVerificationService,
                                    EmailVerificationCodeRepository emailVerificationCodeRepository) {
        this.emailVerificationService = emailVerificationService;
        this.emailVerificationCodeRepository = emailVerificationCodeRepository;
    }

    public void createAndSendCode(User user) throws ResendException {
        SecureRandom secureRandom = new SecureRandom();
        String code = String.valueOf(secureRandom.nextInt(900000) + 100000);
        emailVerificationService.sendVerificationEmail(user.getEmail(), code);
        EmailVerificationCode verificationCode = new EmailVerificationCode(code, user);
        emailVerificationCodeRepository.save(verificationCode);
    }

    public EmailVerifyResponse verify(Long userId, String code) {
        EmailVerificationCode verificationCode = emailVerificationCodeRepository.findByUser_Id(userId)
                .orElseThrow();
        if (LocalDateTime.now().isAfter(verificationCode.getExpiresAt())) {
            throw new IllegalArgumentException("인증 코드가 만료되었습니다.");
        }

        if (!verificationCode.getCode().equals(code)) {
            throw new IllegalArgumentException("잘못된 인증번호입니다.");
        }
        return new EmailVerifyResponse(
                userId,
                true
        );
    }

    public void resend(User user) throws ResendException {
        Optional<EmailVerificationCode> verificationCode = emailVerificationCodeRepository.findByUser_Id(user.getId());
        // 메서드 참조로 줄임 (삭제함 반환값 없음)
        verificationCode.ifPresent(emailVerificationCodeRepository::delete);
        createAndSendCode(user);
    }
}
