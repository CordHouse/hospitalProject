package com.example.hospitalproject.Repository.Payment.Card;

import com.example.hospitalproject.Entity.Payment.Credit.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<List<Card>> findByCardNumber(String cardNumber);
}