package com.paradigm.tech.app.controller;

import com.paradigm.tech.app.model.entityDTO.request.JobVacancy.RequestJobVacancyCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.JobVacancy.RequestJobVacancyUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.model.entityDTO.response.JobVacancy.ResponseJobVacancyDTO;
import com.paradigm.tech.app.model.entityDTO.response.JobVacancy.ResponseJobVacancyGetAllDTO;
import com.paradigm.tech.app.services.*;
import com.paradigm.tech.app.utlls.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION)
public class JobVacancyController {
    private final JobVacancyService jobVacancyService;

    @PostMapping(ApiPathConstant.JOB_VACANCY + "/create")
    public ResponseEntity<CustomResponseDTO<?>> createJobVacancy(@RequestBody RequestJobVacancyCreateDTO requestJobVacancyCreateDTO) {
        ResponseJobVacancyDTO result = jobVacancyService.createJobVacancy(requestJobVacancyCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponseDTO<>(true, 200, "Job vacancy successfully created", result));
    }

    @GetMapping(ApiPathConstant.JOB_VACANCY + "/open")
    public ResponseEntity<CustomResponseDTO<?>> getAllOpenJobVacancy() {
        List<ResponseJobVacancyGetAllDTO> jobVacancies = jobVacancyService.getAllOpenJobVacancy();
        System.out.println(jobVacancies);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "All open job vacancies successfully fetched", jobVacancies));
    }

    @GetMapping(ApiPathConstant.JOB_VACANCY + "/all")
    public ResponseEntity<CustomResponseDTO<?>> getAllJobVacancy() {
        List<ResponseJobVacancyGetAllDTO> jobVacancies = jobVacancyService.getAllJobVacancy();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "All job vacancies successfully fetched", jobVacancies));
    }

    @GetMapping(ApiPathConstant.JOB_VACANCY + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> getJobVacancyById(@PathVariable String id) {
        ResponseJobVacancyDTO responseJobVacancyDTO = jobVacancyService.getJobVacancyById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Job vacancy successfully fetched", responseJobVacancyDTO));
    }

    @PutMapping(ApiPathConstant.JOB_VACANCY + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> updateJobVacancy(@PathVariable String id, @RequestBody RequestJobVacancyUpdateDTO requestJobVacancyUpdateDTO) {
        ResponseJobVacancyDTO responseJobVacancyDTO = jobVacancyService.updateJobVacancy(id, requestJobVacancyUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Job vacancy successfully updated", responseJobVacancyDTO));
    }
}
