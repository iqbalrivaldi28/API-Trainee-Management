package com.paradigm.tech.app.repository;

import com.paradigm.tech.app.model.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.List;

@Repository
public interface JobVacancyRepository extends JpaRepository<Job_Vacancy, String> {
    List<Job_Vacancy> findByBdClient(BD_Client bdClient);
}
