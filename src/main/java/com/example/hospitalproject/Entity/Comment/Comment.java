package com.example.hospitalproject.Entity.Comment;

import com.example.hospitalproject.Entity.Board.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //@데이터베이스가 늘어날때마다 숫자가 늘어남
    private long id;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    private String writer;

    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private LocalDateTime createDate;

    @PrePersist
    public void createLocalDateTime(){
        this.createDate=LocalDateTime.now();
    }

    public Comment(String comment, String writer, Board board){

        this.comment=comment;
        this.writer=writer;
        this.board=board;
    }

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)  //다 대 일
    @OnDelete(action = OnDeleteAction.CASCADE)  //Cascade 연관되어있는 데이터 모두 삭제
    public Board board;


    //id(pk), content, writer, usergrade, like, delete

}
