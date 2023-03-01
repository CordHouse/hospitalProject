package com.example.hospitalproject.Entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 5)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String birthday;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private RoleUserGrade roleUserGrade;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate;

    @PrePersist // DB에 Insert 되기 직전에 실행된다.
    public void createDate(){
        this.createDate = LocalDate.now();
        this.passwordRecycle = LocalDate.now().plusMonths(3);
    }

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate passwordRecycle;

    public User(String name, String username, String password, String birthday, String phone, String email, String address, RoleUserGrade roleUserGrade){
        this.name = name;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.roleUserGrade = roleUserGrade;
    }

}
