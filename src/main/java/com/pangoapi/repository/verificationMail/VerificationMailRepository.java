package com.pangoapi.repository.verificationMail;

import com.pangoapi.domain.entity.verificationMail.VerificationMail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface VerificationMailRepository extends JpaRepository<VerificationMail, Long> {

    Long countByToEmailAndSentDate(String toEmail, LocalDate sentDate);
}
