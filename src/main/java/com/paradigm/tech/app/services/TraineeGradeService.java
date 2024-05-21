package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entityDTO.request.TraineeGrade.RequestTraineeGradeCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.TraineeGrade.RequestTraineeGradeUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeGrade.*;
import org.springframework.security.core.Authentication;

import java.util.*;

public interface TraineeGradeService {
    ResponseTraineeGradeCreateDTO createTraineeGrade(RequestTraineeGradeCreateDTO requestTraineeGradeCreateDTO);
    List<ResponseTraineeGradeGetDTO> getAllTraineeGrades();
    List<ResponseTraineeGradeBatchAndTraineeDTO> getTraineeGradesByBatchAndTrainee(Authentication authentication);
    ResponseTraineeGradeGetStatusDTO getTraineeGradesByStatus(Authentication authentication);
    List<ResponseTraineeGradeDTO> getTraineeGradeByTrainee(Authentication authentication);
    ResponseTraineeGradeGetDTO getTraineeGradeById(String id);
    ResponseTraineeGradeGetDTO updateTraineeGrade(String id, RequestTraineeGradeUpdateDTO requestTraineeGradeUpdateDTO);
}
