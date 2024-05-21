package com.paradigm.tech.app.repository;

import com.paradigm.tech.app.model.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.List;

@Repository
public interface TraineeEducationRepo extends JpaRepository<Trainee_Education, String> {
    List<Trainee_Education> findByTraineeDetailId(String id);
}
