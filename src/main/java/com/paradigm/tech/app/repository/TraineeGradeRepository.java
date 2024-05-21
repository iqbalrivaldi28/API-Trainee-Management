package com.paradigm.tech.app.repository;

import com.paradigm.tech.app.model.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.List;

@Repository
public interface TraineeGradeRepository extends JpaRepository<Trainee_Grade, String> {
    List<Trainee_Grade> findByTraineeDetail(Trainee_Detail traineeDetail);

}
