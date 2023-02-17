package com.example.hospitalproject.Repository.Email;

import com.example.hospitalproject.Entity.Email.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<EmailToken, Long> {
    Optional<EmailToken> findByIdAndExpirationDateAfterAndExpired(String emailTokenId, LocalDateTime now, String expired);

    Optional<EmailToken> findByUsername(String username);
}
