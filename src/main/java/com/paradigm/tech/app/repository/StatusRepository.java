package com.paradigm.tech.app.repository;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.utlls.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface StatusRepository extends JpaRepository<Status, String> {
    Optional<Status> findByStatus(EStatus status);

    @Query("SELECT s FROM Status s WHERE s.id IN ('550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440002')")
    List<Status> findGradeStatus();

    @Query("SELECT s FROM Status s WHERE s.id IN ('550e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440005', '550e8400-e29b-41d4-a716-446655440006', '550e8400-e29b-41d4-a716-446655440007', '550e8400-e29b-41d4-a716-446655440008', '550e8400-e29b-41d4-a716-446655440009', '550e8400-e29b-41d4-a716-446655440010', '550e8400-e29b-41d4-a716-446655440011')")
    List<Status> findApplicationStatus();

    @Query("SELECT s FROM Status s WHERE s.id IN ('550e8400-e29b-41d4-a716-446655440012', '550e8400-e29b-41d4-a716-446655440013')")
    List<Status> findVacancyStatus();

    @Query("SELECT s FROM Status s WHERE s.id IN ('550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440003')")
    List<Status> findBootcampStatus();
}
