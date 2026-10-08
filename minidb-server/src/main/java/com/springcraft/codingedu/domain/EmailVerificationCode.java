package com.springcraft.codingedu.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class EmailVerificationCode {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, unique = true)
     String code;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    User user;

    @Column(nullable = false)
    LocalDateTime expiresAt;

    @Column(nullable = false)
    LocalDateTime createdAt;

    protected EmailVerificationCode(){}

    public EmailVerificationCode(
            String code,
            User user
    ) {
        this.code = code;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = createdAt.plusMinutes(1);
    }

    public String getCode() {
        return code;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
