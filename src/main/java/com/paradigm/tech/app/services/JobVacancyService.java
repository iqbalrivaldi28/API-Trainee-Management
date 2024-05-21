package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entityDTO.request.JobVacancy.RequestJobVacancyCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.JobVacancy.RequestJobVacancyUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.JobVacancy.ResponseJobVacancyDTO;
import com.paradigm.tech.app.model.entityDTO.response.JobVacancy.ResponseJobVacancyGetAllDTO;

import java.util.List;

public interface JobVacancyService {
    ResponseJobVacancyDTO createJobVacancy(RequestJobVacancyCreateDTO requestJobVacancyCreateDTO);
    ResponseJobVacancyDTO getJobVacancyById(String id);
    List<ResponseJobVacancyGetAllDTO> getAllOpenJobVacancy();
    List<ResponseJobVacancyGetAllDTO> getAllJobVacancy();
    ResponseJobVacancyDTO updateJobVacancy(String id, RequestJobVacancyUpdateDTO requestJobVacancyUpdateDTO);
//    JobVacancyDTO getJobVacancyApplication(String id);
//    List<JobVacancyDTO> getAllJobVacancyApplication();
}
