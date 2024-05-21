package com.paradigm.tech.app.controller;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.model.entityDTO.request.TraineeGrade.RequestTraineeGradeCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.TraineeGrade.RequestTraineeGradeUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.model.entityDTO.response.TraineeGrade.*;
import com.paradigm.tech.app.services.*;
import com.paradigm.tech.app.utlls.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION)
@RequiredArgsConstructor
public class TraineeGradeController {
    private final TraineeGradeService traineeGradeService;

    @PostMapping(ApiPathConstant.TRAINEE_GRADE + "/create")
    public ResponseEntity<CustomResponseDTO<?>> createTraineeGrade(@RequestBody RequestTraineeGradeCreateDTO requestTraineeGradeCreateDTO) {
        ResponseTraineeGradeCreateDTO traineeGrade = traineeGradeService.createTraineeGrade(requestTraineeGradeCreateDTO);
        CustomResponseDTO<?> response = CustomResponseDTO.<ResponseTraineeGradeCreateDTO>builder()
                .status(true)
                .code(HttpStatus.CREATED.value())
                .message("Trainee grade created successfully!")
                .data(traineeGrade)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(ApiPathConstant.TRAINEE_GRADE + "/all")
    public ResponseEntity<CustomResponseDTO<?>> getAllTraineeGrades() {
        List<ResponseTraineeGradeGetDTO> traineeGrades = traineeGradeService.getAllTraineeGrades();
        CustomResponseDTO<?> response = CustomResponseDTO.<List<ResponseTraineeGradeGetDTO>>builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List of all trainee grades")
                .data(traineeGrades)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(ApiPathConstant.TRAINEE_GRADE)
    public ResponseEntity<CustomResponseDTO<?>> getTraineeGradesByBatchAndTrainee(Authentication authentication) {
        List<ResponseTraineeGradeBatchAndTraineeDTO> traineeGrades = traineeGradeService.getTraineeGradesByBatchAndTrainee(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Trainee grades fetched", traineeGrades));
    }

    @GetMapping(ApiPathConstant.TRAINEE_GRADE + "/status")
    public ResponseEntity<CustomResponseDTO<?>> getTraineeGradesByStatus(Authentication authentication) {
        ResponseTraineeGradeGetStatusDTO traineeGradeGetStatus = traineeGradeService.getTraineeGradesByStatus(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Trainee grades fetched", traineeGradeGetStatus));
    }

    @GetMapping(ApiPathConstant.TRAINEE_GRADE + "/trainee")
    public ResponseEntity<CustomResponseDTO<?>> getTraineeGradeByTrainee(Authentication authentication) {
        List<ResponseTraineeGradeDTO> traineeGrades = traineeGradeService.getTraineeGradeByTrainee(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Trainee grades fetched", traineeGrades));
    }

    @GetMapping(ApiPathConstant.TRAINEE_GRADE + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> getTraineeGradeById(@PathVariable String id) {
        ResponseTraineeGradeGetDTO traineeGrade = traineeGradeService.getTraineeGradeById(id);
        if (traineeGrade != null) {
            CustomResponseDTO<?> response = CustomResponseDTO.<ResponseTraineeGradeGetDTO>builder()
                    .status(true)
                    .code(HttpStatus.OK.value())
                    .message("Trainee grade found")
                    .data(traineeGrade)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            CustomResponseDTO<?> response = CustomResponseDTO.<ResponseTraineeGradeGetDTO>builder()
                    .status(false)
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("Trainee grade not found")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping(ApiPathConstant.TRAINEE_GRADE + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> updateTraineeGrade(@PathVariable String id, @RequestBody RequestTraineeGradeUpdateDTO requestTraineeGradeUpdateDTO) {
        ResponseTraineeGradeGetDTO updatedTraineeGrade = traineeGradeService.updateTraineeGrade(id, requestTraineeGradeUpdateDTO);
        CustomResponseDTO<?> response = CustomResponseDTO.<ResponseTraineeGradeGetDTO>builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Trainee grade updated successfully!")
                .data(updatedTraineeGrade)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}