package com.example.hospitalproject.Entity.Payment.Credit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "bank")
    private String bank;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String validYear;

    @Column(nullable = false)
    private String validMonth;

    @Column(nullable = false)
    private String password;
    @DateTimeFormat(pattern = "YYYY-mm-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    @PrePersist
    public void createTime(){
        this.localDateTime = LocalDateTime.now();
    }

    public Card(String bank, String cardNumber, String validYear, String validMonth, String password){
        this.bank = bank;
        this.cardNumber = cardNumber;
        this.validYear = validYear;
        this.validMonth = validMonth;
        this.password = password;
    }
}
