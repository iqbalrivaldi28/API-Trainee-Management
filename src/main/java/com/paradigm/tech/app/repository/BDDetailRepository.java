package com.paradigm.tech.app.repository;

import com.paradigm.tech.app.model.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.List;

@Repository
public interface BDDetailRepository extends JpaRepository<BD_Detail, String> {
    BD_Detail findByUserId(String id);
    List<BD_Detail> findAllByIsDeletedIsFalse();
}
