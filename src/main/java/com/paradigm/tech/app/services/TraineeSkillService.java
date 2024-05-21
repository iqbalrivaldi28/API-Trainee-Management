package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestCreateTraineeSkillDTO;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestUpdateTraineeSkillDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.*;
import org.springframework.security.core.*;

import java.util.*;

public interface TraineeSkillService {
    ResponseCreateTraineeSkillDTO saveTraineeSkill(RequestCreateTraineeSkillDTO requestCreateTraineeSkillDTO, Authentication authentication);

    List<ResponseGetTraineeSkillDTO> getAllTraineeSkill();

    List<ResponseGetTraineeSkillByTraineeDTO> getTraineeSkillByTraineeId(Authentication authentication);

    ResponseGetTraineeSkillDTO getTraineeSkillById(String id);

    ResponseUpdateTraineeSkillDTO updateTraineeSkill(Authentication authentication, RequestUpdateTraineeSkillDTO requestUpdateTraineeSkillDTO, String id);

    void deleteTraineeSkill(Authentication authentication, String id);
}
