package com.paradigm.tech.app.repository;

import com.paradigm.tech.app.model.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.List;

@Repository
public interface SocialMediaRepository extends JpaRepository<Social_Media,String> {
    List<Social_Media> findByTraineeDetail(Trainee_Detail traineeDetail);
}
