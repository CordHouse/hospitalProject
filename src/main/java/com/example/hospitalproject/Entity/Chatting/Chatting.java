package com.example.hospitalproject.Entity.Chatting;

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
public class Chatting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "text")
    private String comment;

    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
    private String target;

    @Column(nullable = false)
    private String doDelete;

    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(name = "timeStamp")
    private LocalDateTime date;

    @PrePersist
    private void createDate(){
        date = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatBoardId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ChatBoard chatBoard;

    public Chatting(String comment, ChatBoard chatBoard, String host, String target){
        this.comment = comment;
        this.chatBoard = chatBoard;
        this.host = host;
        this.target = target;
        this.doDelete = "false";
    }
}
