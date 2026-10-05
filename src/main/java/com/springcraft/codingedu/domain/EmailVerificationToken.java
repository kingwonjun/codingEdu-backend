package com.springcraft.codingedu.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class EmailVerificationToken {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, unique = true)
     String token;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    User user;

    @Column(nullable = false)
    LocalDateTime expiresAt;

    LocalDateTime usedAt;

    @Column(nullable = false)
    LocalDateTime createdAt;

    protected EmailVerificationToken(){}

    public EmailVerificationToken(
            String token,
            User user,
            LocalDateTime createdAt,
            LocalDateTime expiresAt
    ) {
        this.token = token;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = expiresAt;
    }

    public void markAsUsed() {
        this.usedAt = LocalDateTime.now();
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
