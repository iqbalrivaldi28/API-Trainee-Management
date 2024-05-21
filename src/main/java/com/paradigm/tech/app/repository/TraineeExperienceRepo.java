package com.paradigm.tech.app.repository;

import com.paradigm.tech.app.model.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.List;

@Repository
public interface TraineeExperienceRepo extends JpaRepository<Trainee_Experience, String> {
    List<Trainee_Experience> findByTraineeDetail(Trainee_Detail traineeDetail);
}
