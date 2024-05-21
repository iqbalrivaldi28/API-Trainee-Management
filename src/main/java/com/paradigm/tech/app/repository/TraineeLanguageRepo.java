package com.paradigm.tech.app.repository;

import com.paradigm.tech.app.model.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.List;

@Repository
public interface TraineeLanguageRepo extends JpaRepository<Trainee_Language, String> {
    List<Trainee_Language> findByTraineeDetail(Trainee_Detail traineeDetail);

}
