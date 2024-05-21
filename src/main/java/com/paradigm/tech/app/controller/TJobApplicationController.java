package com.paradigm.tech.app.controller;

import com.paradigm.tech.app.model.entityDTO.request.JobApplication.RequestJobAppCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.JobApplication.RequestJobAppUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.CustomResponseDTO;
import com.paradigm.tech.app.model.entityDTO.response.JobApplication.*;
import com.paradigm.tech.app.services.impl.*;
import com.paradigm.tech.app.utlls.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION)
@RequiredArgsConstructor
public class TJobApplicationController {
    private final TJobAppServiceImpl jobApplicationService;

    @PostMapping(ApiPathConstant.T_JOB_APP + "/create")
    public ResponseEntity<CustomResponseDTO<?>> createJobApp(Authentication authentication, @RequestBody RequestJobAppCreateDTO requestJobAppCreateDTO) {
        ResponseJobAppCreateDTO responseJobAppCreateDTO = jobApplicationService.createJobApp(authentication, requestJobAppCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponseDTO<>(true, 200, "Job Application created", responseJobAppCreateDTO));
    }

    @GetMapping(ApiPathConstant.T_JOB_APP + "/all")
    public ResponseEntity<CustomResponseDTO<?>> getAllJobApp() {
        List<ResponseJobAppGetDTO> responseJobAppGetDTOS = jobApplicationService.getAllJobApp();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "All Job Applications fetched", responseJobAppGetDTOS));
    }

    @GetMapping(ApiPathConstant.T_JOB_APP)
    public ResponseEntity<CustomResponseDTO<?>> getJobAppByTrainee(Authentication authentication) {
        List<ResponseJobAppGetByTraineeDTO> responseJobAppGetDTOS = jobApplicationService.getJobAppByTraineeId(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Job Applications fetched", responseJobAppGetDTOS));
    }

    @GetMapping(ApiPathConstant.T_JOB_APP + "/job-vacancy")
    public ResponseEntity<CustomResponseDTO<?>> getJobAppByJobVacancy(Authentication authentication) {
        List<ResponseJobAppGetByJobVacancyDTO> responseJobAppGet = jobApplicationService.getJobAppByJobVacancy(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Job Applications fetched", responseJobAppGet));
    }

    @GetMapping(ApiPathConstant.T_JOB_APP + "/status")
    public ResponseEntity<CustomResponseDTO<?>> getAllJobAppByStatus(Authentication authentication) {
        ResponseJobAppGetStatusDTO jobAppGetStatus = jobApplicationService.getAllJobAppByStatus(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Job Applications fetched", jobAppGetStatus));
    }

    @GetMapping(ApiPathConstant.T_JOB_APP + "/batch-status")
    public ResponseEntity<CustomResponseDTO<?>> getJobAppByStatusAndBatch(Authentication authentication) {
        List<ResponseJobAppGetStatusAndBatchDTO> jobAppGetStatusByBatch = jobApplicationService.getJobAppByStatusAndBatch(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Job Applications fetched", jobAppGetStatusByBatch));
    }

    @GetMapping(ApiPathConstant.T_JOB_APP + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> getJobAppById(@PathVariable String id) {
        ResponseJobAppGetDTO responseJobAppGetDTO = jobApplicationService.getJobAppById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Job Application fetched", responseJobAppGetDTO));
    }

    @PutMapping(ApiPathConstant.T_JOB_APP + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> updateJobApp(@PathVariable String id, @RequestBody RequestJobAppUpdateDTO requestJobAppUpdateDTO) {
        ResponseJobAppUpdateDTO responseJobAppUpdateDTO = jobApplicationService.updateJobApp(id, requestJobAppUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Job Application updated", responseJobAppUpdateDTO));
    }
}
