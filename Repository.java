package com.memory.h2;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface Repository extends JpaRepository<User, Long> {
    List<User> findByUsernameContaining(String username);
    //List<User> findById(int id);
}