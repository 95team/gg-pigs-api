package com.pangoapi.verificationMail.repository;

import com.pangoapi.verificationMail.entity.VerificationMail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface VerificationMailRepository extends JpaRepository<VerificationMail, Long> {

    Long countByToEmailAndSentDate(String toEmail, LocalDate sentDate);
}
