package com.example.capstone_3_updated.Repository;


import com.example.capstone_3_updated.Model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails,Integer> {
    UserDetails findUserDetailsById(Integer id);
}