package com.example.capstone_3_updated.Repository;

import com.example.capstone_3_updated.Model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Integer> {
    Report findReportById(Integer id);
}
