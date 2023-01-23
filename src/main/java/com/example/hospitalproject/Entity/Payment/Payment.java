package com.example.hospitalproject.Entity.Payment;

import com.example.hospitalproject.Entity.Payment.Credit.Card;
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
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String pay;

    @Column(nullable = false)
    private String groupCode;

    @Column(nullable = false)
    private int code;

    @Column(nullable = false)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Card card;

    /**
     * 승인 여부
     */
    @Column(nullable = false)
    private String approval;

    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private LocalDateTime approvalDate;

    @PrePersist
    public void approvalDate(){
        this.approvalDate = LocalDateTime.now();
    }

    public Payment(String username, String pay, String groupCode, int code, String type, Card card, String approval){
        this.username = username;
        this.pay = pay;
        this.groupCode = groupCode;
        this.code = code;
        this.type = type;
        this.card = card;
        this.approval = approval;
    }
}
