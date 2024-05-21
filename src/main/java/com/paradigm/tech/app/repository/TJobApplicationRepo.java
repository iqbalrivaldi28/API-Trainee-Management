package com.paradigm.tech.app.repository;

import com.paradigm.tech.app.model.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface TJobApplicationRepo extends JpaRepository<T_Job_Application, String> {
    List<T_Job_Application> findByTraineeDetailId(String id);
    List<T_Job_Application> findByJobVacancy(Job_Vacancy jobVacancy);
}
