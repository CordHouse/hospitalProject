package com.example.hospitalproject.Repository.Board;

import com.example.hospitalproject.Entity.Board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
