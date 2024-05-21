package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestTraineeEduCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestTraineeEduUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeEduCreateDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeEduGetByTraineeDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeEduGetDTO;
import org.springframework.security.core.*;

import java.util.List;

public interface TraineeEduService {
    ResponseTraineeEduCreateDTO saveEducation(RequestTraineeEduCreateDTO education, Authentication authentication);

    List<ResponseTraineeEduGetDTO> getAllEducation();

    List<ResponseTraineeEduGetByTraineeDTO> getEducationByTraineeId(Authentication authentication);

    ResponseTraineeEduGetDTO getEducationById(String id);

    ResponseTraineeEduGetDTO updateEducation(Authentication authentication, RequestTraineeEduUpdateDTO requestTraineeEduUpdateDTO, String id);

    void deleteEducation(String id);

}
