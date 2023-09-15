package com.example.capstone_3_updated.Repository;

import com.example.capstone_3_updated.Model.TestType;
import com.example.capstone_3_updated.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TestTypeRepository extends JpaRepository<TestType , Integer> {

    TestType findTestTypeById(Integer id);


}
