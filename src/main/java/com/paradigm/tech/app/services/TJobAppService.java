package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entityDTO.request.JobApplication.RequestJobAppCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.JobApplication.RequestJobAppUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.JobApplication.*;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TJobAppService {
    ResponseJobAppCreateDTO createJobApp(Authentication authentication, RequestJobAppCreateDTO requestJobAppCreateDTO);
    List<ResponseJobAppGetDTO> getAllJobApp();
    List<ResponseJobAppGetByTraineeDTO> getJobAppByTraineeId(Authentication authentication);
    List<ResponseJobAppGetByJobVacancyDTO> getJobAppByJobVacancy(Authentication authentication);
    ResponseJobAppGetStatusDTO getAllJobAppByStatus(Authentication authentication);
    List<ResponseJobAppGetStatusAndBatchDTO> getJobAppByStatusAndBatch(Authentication authentication);
    ResponseJobAppGetDTO getJobAppById(String id);
    ResponseJobAppUpdateDTO updateJobApp(String id, RequestJobAppUpdateDTO requestJobAppUpdateDTO);
}
