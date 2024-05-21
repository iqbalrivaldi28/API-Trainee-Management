package com.paradigm.tech.app.repository;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.utlls.ELanguage;
import com.paradigm.tech.app.utlls.ESkill;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.Optional;

@Repository
public interface MSkillRepository extends JpaRepository<M_Skill, String> {
    Optional<M_Skill> findBySkillName(ESkill skill);

}
