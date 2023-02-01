package com.example.hospitalproject.Repository.Comment;

import com.example.hospitalproject.Entity.Comment.Comment;
import com.example.hospitalproject.Entity.Comment.CommentUserLike.CommentUserLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentUserLikeRepository extends JpaRepository<CommentUserLike, Long> {
    CommentUserLike findByUsernameAndComment(String username, Comment comment);
}
