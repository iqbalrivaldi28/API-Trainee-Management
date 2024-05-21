package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entity.Trainee_Experience;
import com.paradigm.tech.app.model.entityDTO.request.RequestTraineeExpCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.RequestUpdateExpDTO;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestCreateTraineeExpDTO;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestUpdateTraineeExpDTO;
import com.paradigm.tech.app.model.entityDTO.response.ResponseTraineeExpCreateDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseCreateTraineeExpDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseGetTraineeExpByTraineeDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseGetTraineeExpDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeEduGetByTraineeDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TraineeExpService {
    ResponseCreateTraineeExpDTO saveExperience(RequestCreateTraineeExpDTO requestCreateTraineeExpDTO, Authentication authentication);

    List<ResponseGetTraineeExpDTO> getAllExperience();

    List<ResponseGetTraineeExpByTraineeDTO> getExperienceByTraineeId(Authentication authentication);

    ResponseGetTraineeExpDTO getExperienceById(String id);

    ResponseGetTraineeExpDTO updateExperience(Authentication authentication, RequestUpdateTraineeExpDTO requestUpdateTraineeExpDTO, String id);

    void deleteExperience(Authentication authentication, String id);
}
