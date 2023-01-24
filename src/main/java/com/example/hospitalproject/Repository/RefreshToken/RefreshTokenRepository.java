package com.example.hospitalproject.Repository.RefreshToken;

import com.example.hospitalproject.Entity.User.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    boolean existsRefreshTokenById(String id);
}
