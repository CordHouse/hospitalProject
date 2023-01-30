package com.example.hospitalproject.Repository.User;

import com.example.hospitalproject.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findUserByEmailAndPhone(String email, String phone);

    Optional<User> findUserByEmailAndPhoneAndUsername(String email, String phone, String username);

    Optional<User> findByPassword(String password);

    boolean existsUserByUsernameOrEmailOrPhone(String username, String email, String phone);
}
