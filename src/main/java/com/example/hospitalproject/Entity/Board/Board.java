package com.example.hospitalproject.Entity.Board;

import com.example.hospitalproject.Entity.Image.Image;
import com.example.hospitalproject.Entity.User.RoleUserGrade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data // getter, setter
@AllArgsConstructor // 매개변수가 다 들어가는 생성자
@NoArgsConstructor // 매개변수가 없는 생성자
@DynamicInsert
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "content")
    @Lob
    private String content;

    @Column(nullable = false, name = "writer")
    private String writer;

    @Column(name = "viewCount", columnDefinition = "integer default 0")
    private Integer viewCount;

    @Column(name = "starPoint", columnDefinition = "Double default 0")
    private Double starPoint;

    @Enumerated(EnumType.STRING)
    private RoleUserGrade roleUserGrade;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean deleted;

    @OneToMany(mappedBy = "board", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Image> imageList = new ArrayList<>();
}