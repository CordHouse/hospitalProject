package com.example.hospitalproject.Repository.Comment;

import com.example.hospitalproject.Entity.Comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
