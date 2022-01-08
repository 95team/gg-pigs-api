package com.gg_pigs.app.verificationMail.repository;

import com.gg_pigs.app.verificationMail.entity.VerificationMail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface VerificationMailRepository extends JpaRepository<VerificationMail, Long> {

    Long countByToEmailAndSentDate(String toEmail, LocalDate sentDate);
}
