package com.paradigm.tech.app.controller;

import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestTraineeEduCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestTraineeEduUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeEduCreateDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeEduGetByTraineeDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeEduGetDTO;
import com.paradigm.tech.app.services.TraineeEduService;
import com.paradigm.tech.app.services.impl.*;
import com.paradigm.tech.app.utlls.ApiPathConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION)
@RequiredArgsConstructor
public class TraineeEducationController {
    private final TraineeEduService traineeEducationService;

    @PostMapping(ApiPathConstant.TRAINEE_EDUCATION + "/create")
    public ResponseEntity<CustomResponseDTO<?>> saveTraineeExperience(
            @RequestBody RequestTraineeEduCreateDTO traineeEducation,
            Authentication authentication
    ) {
            ResponseTraineeEduCreateDTO savedTraineeEducation = traineeEducationService.saveEducation(traineeEducation, authentication);
            return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Trainee education saved successfully!", savedTraineeEducation));
    }

    @GetMapping(ApiPathConstant.TRAINEE_EDUCATION + "/all")
    public ResponseEntity<CustomResponseDTO<?>> getAllTraineeEducation() {
        List<ResponseTraineeEduGetDTO> responseTraineeEduGetDTOS = traineeEducationService.getAllEducation();
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Success get education!", responseTraineeEduGetDTOS));
    }

    @GetMapping(ApiPathConstant.TRAINEE_EDUCATION + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> getTraineeEducationById(@PathVariable String id) {
        ResponseTraineeEduGetDTO responseTraineeEduGetDTO = traineeEducationService.getEducationById(id);
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Success get education!", responseTraineeEduGetDTO));
    }

    @GetMapping(ApiPathConstant.TRAINEE_EDUCATION)
    public ResponseEntity<CustomResponseDTO<?>> getTraineeEducationByTraineeId(Authentication authentication) {
        List<ResponseTraineeEduGetByTraineeDTO> responseTraineeEduGetDTOS = traineeEducationService.getEducationByTraineeId(authentication);
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Success get education!", responseTraineeEduGetDTOS));
    }

    @PutMapping(ApiPathConstant.TRAINEE_EDUCATION + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> updateTraineeEducation(
            Authentication authentication,
            @PathVariable String id,
            @RequestBody RequestTraineeEduUpdateDTO requestTraineeEduUpdateDTO
    ) {
        try {
            ResponseTraineeEduGetDTO updatedTraineeEducation = traineeEducationService.updateEducation(authentication, requestTraineeEduUpdateDTO, id);
            CustomResponseDTO<?> response = CustomResponseDTO.<ResponseTraineeEduGetDTO>builder()
                    .status(true)
                    .code(HttpStatus.OK.value())
                    .message("Trainee Education updated successfully!")
                    .data(updatedTraineeEducation)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            CustomResponseDTO<?> response = CustomResponseDTO.<ResponseTraineeEduGetDTO>builder()
                    .status(false)
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to update trainee education: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping(ApiPathConstant.TRAINEE_EDUCATION + "/{id}")
    public ResponseEntity<CustomResponseDTO<Void>> deleteTraineeEducation(@PathVariable String id) {
        traineeEducationService.deleteEducation(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Success delete education!", null));
    }
}
