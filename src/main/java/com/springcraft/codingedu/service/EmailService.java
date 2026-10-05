package com.springcraft.codingedu.service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import com.springcraft.codingedu.config.ResendConfig;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final Resend resend;

    public EmailService(Resend resend) {
        this.resend = resend;
    }

    public void sendVerificationEmail(String email, String token) throws ResendException {
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("onboarding@resend.dev")
                .to("apppppp8229@gmail.com")
                .subject("Hello World")
                .html("<p>Congrats on sending your <strong>first email</strong>!</p>")
                .build();
        CreateEmailResponse data = resend.emails().send(params);
    }
}
