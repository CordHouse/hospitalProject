package com.example.hospitalproject.Repository.Payment;

import com.example.hospitalproject.Entity.Payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
