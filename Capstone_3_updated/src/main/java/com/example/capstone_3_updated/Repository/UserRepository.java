package com.example.capstone_3_updated.Repository;

import com.example.capstone_3_updated.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User,Integer> {
User findUserById(Integer id);
}
