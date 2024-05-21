package com.paradigm.tech.app.repository;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.utlls.ELanguage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface MLanguageRepository extends JpaRepository<M_Language, String> {
    Optional<M_Language> findByLanguageName(ELanguage language);
}
