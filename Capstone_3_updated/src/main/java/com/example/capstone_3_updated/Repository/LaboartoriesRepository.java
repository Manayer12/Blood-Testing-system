package com.example.capstone_3_updated.Repository;

import com.example.capstone_3_updated.Model.Laboratories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboartoriesRepository extends JpaRepository<Laboratories , Integer> {

    Laboratories findLaboratoriesById(Integer id);
}
