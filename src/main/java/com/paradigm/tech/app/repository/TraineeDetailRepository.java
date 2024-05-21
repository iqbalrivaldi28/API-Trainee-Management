package com.paradigm.tech.app.repository;

import com.paradigm.tech.app.model.entity.M_Batch;
import com.paradigm.tech.app.model.entity.Trainee_Detail;
import com.paradigm.tech.app.model.entity.Trainee_Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeDetailRepository extends JpaRepository<Trainee_Detail, String> {
    Trainee_Detail findByUserId(String id);
    List<Trainee_Detail> findAllByIsDeletedIsFalse();
    List<Trainee_Detail> findByBatch(M_Batch mBatch);
}
