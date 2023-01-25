package com.example.hospitalproject.Entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Comment(String comment, String writer){

        this.comment=comment;
        this.writer=writer;
    }

    //id(pk), content, writer, usergrade, like, delete

}
