package com.example.hospitalproject.Entity.Payment.Credit;

import com.example.hospitalproject.Entity.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(nullable = false)
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

    public Card(User user, String bank, String cardNumber, String validYear, String validMonth, String password){
        this.user = user;
        this.bank = bank;
        this.cardNumber = cardNumber;
        this.validYear = validYear;
        this.validMonth = validMonth;
        this.password = password;
    }
}
