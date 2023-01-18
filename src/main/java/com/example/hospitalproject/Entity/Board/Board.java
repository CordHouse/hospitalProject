package com.example.hospitalproject.Entity.Board;

import com.example.hospitalproject.Entity.User.RoleUserGrade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Data // getter, setter
@AllArgsConstructor // 매개변수가 다 들어가는 생성자
@NoArgsConstructor // 매개변수가 없는 생성자
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

    @ColumnDefault("0")
    private Long viewCount;

    @Enumerated(EnumType.STRING)
    private RoleUserGrade roleUserGrade;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean delete;

}
