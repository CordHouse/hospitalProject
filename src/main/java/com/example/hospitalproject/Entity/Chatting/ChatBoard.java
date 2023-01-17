package com.example.hospitalproject.Entity.Chatting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "chatTitle")
    private String title;

    @Column(nullable = false, name = "doHostDelete")
    private String hostDelete;

    @Column(nullable = false, name = "doTargetDelete")
    private String targetDelete;

    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
    private String target;

    @Enumerated(EnumType.STRING)
    @Column(name = "chatType")
    private ChatTitleType chatTitleType;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate;

    @PrePersist
    private void createDate(){
        createDate = LocalDate.now();
    }

    public ChatBoard(String title, ChatTitleType chatTitleType, String host, String target){
        this.title = title;
        this.chatTitleType = chatTitleType;
        this.host = host;
        this.target = target;
        this.hostDelete = "false";
        this.targetDelete = "false";
    }
}
