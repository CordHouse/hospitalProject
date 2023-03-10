package com.example.hospitalproject.Repository.Payment.Card;

import com.example.hospitalproject.Entity.Payment.Credit.Card;
import com.example.hospitalproject.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByCardNumber(String cardNumber);
    Card findByUser(User user);
    List<Card> findAllByUser(User user);

    Optional<Card> findByIdAndUser_Username(long id, String username);
}
