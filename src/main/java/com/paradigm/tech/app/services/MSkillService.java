package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entityDTO.response.Enums.ResponseMSkillDTO;

import java.util.*;

public interface MSkillService {

//    MSkill saveMSkill(String skill);

    List<ResponseMSkillDTO> getAllMSkill();

    ResponseMSkillDTO getMSkillById(String id);

}
