package com.paradigm.tech.app.repository;

import com.paradigm.tech.app.model.entity.M_Batch;
import com.paradigm.tech.app.utlls.EBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

import java.util.Optional;

@Repository
public interface MBatchRepository extends JpaRepository <M_Batch, String> {
    Optional<M_Batch> findByBatchName(EBatch eBatch);
}
