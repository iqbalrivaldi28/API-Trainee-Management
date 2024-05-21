package com.paradigm.tech.app.repository;

import com.paradigm.tech.app.model.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface BDClientRepository extends JpaRepository<BD_Client, String> {
    List<BD_Client> findAllByIsDeletedIsFalse();
    Optional<BD_Client> findByClientName(String clientName);
    List<BD_Client> findByBdDetail(BD_Detail bdDetail);
}
