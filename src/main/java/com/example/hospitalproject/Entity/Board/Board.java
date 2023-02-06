package com.example.hospitalproject.Entity.Board;

import com.example.hospitalproject.Entity.User.RoleUserGrade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data // getter, setter
@AllArgsConstructor // 매개변수가 다 들어가는 생성자
@NoArgsConstructor // 매개변수가 없는 생성자
@DynamicInsert
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "content")
    private String content;

    @Column(nullable = false, name = "writer")
    private String writer;

    @Column(name = "viewCount", columnDefinition = "integer default 0")
    private Integer viewCount;

    @Column(nullable = false, name = "starPoint")
    private String starPoint;

    @Enumerated(EnumType.STRING)
    private RoleUserGrade roleUserGrade;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean deleted;
}