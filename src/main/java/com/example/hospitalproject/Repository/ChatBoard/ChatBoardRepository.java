package com.example.hospitalproject.Repository.ChatBoard;

import com.example.hospitalproject.Entity.Chatting.ChatBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatBoardRepository extends JpaRepository<ChatBoard, Long> {
    Optional<List<ChatBoard>> findAllBySenderOrReceiver(String sender, String receiver);
}
