package com.example.hospitalproject.Entity.Comment.CommentUserLike;

import com.example.hospitalproject.Entity.Comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentUserLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)  //다 대 일
    @OnDelete(action = OnDeleteAction.CASCADE)  //Cascade 연관되어있는 데이터 모두 삭제
    private Comment comment;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, name = "myLike")
    private int like;

    public CommentUserLike(Comment comment, String username, int like){
        this.comment = comment;
        this.username = username;
        this.like = like;
    }
}
