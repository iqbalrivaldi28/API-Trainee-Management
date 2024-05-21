package com.paradigm.tech.app.repository;

import com.paradigm.tech.app.model.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.List;

@Repository
public interface TraineeSkillRepo extends JpaRepository<Trainee_Skill, String> {

    List<Trainee_Skill> findByTraineeDetail(Trainee_Detail traineeDetail);
}
