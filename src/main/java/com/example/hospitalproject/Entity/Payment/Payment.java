package com.example.hospitalproject.Entity.Payment;

import com.example.hospitalproject.Entity.Payment.Credit.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    private GroupCode groupCode;
//
//    private Code code;
//
//    private Card card;
//
//    private String approval;
}
