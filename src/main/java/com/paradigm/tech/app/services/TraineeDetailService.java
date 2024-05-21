package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestTraineeDetailCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestTraineeDetailUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeDetailCreateDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeDetailGetAllDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeDetailGetDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeDetailUpdateDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TraineeDetailService {
    ResponseTraineeDetailCreateDTO saveTraineeDetail (RequestTraineeDetailCreateDTO requestTraineeDetailCreateDTO);
    List<ResponseTraineeDetailGetAllDTO> getAllTraineeDetail();
    List<ResponseTraineeDetailGetDTO> getAllAvailTraineeDetail();
    ResponseTraineeDetailGetDTO getTraineeProfile (Authentication authentication);
    void softDeleteTraineeDetail(String id);
    ResponseTraineeDetailUpdateDTO updateTraineeDetail(RequestTraineeDetailUpdateDTO requestTraineeDetailUpdateDTO);
}
