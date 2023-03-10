package com.example.hospitalproject.Repository.ChatBoard;

import com.example.hospitalproject.Entity.Chatting.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ChattingRepository extends JpaRepository<Chatting, Long> {
    Optional<List<Chatting>> findAllByChatBoard_IdAndHostOrTarget(Long id, String host, String target);

    Optional<Chatting> findByIdAndHost(Long id, String host);
}
