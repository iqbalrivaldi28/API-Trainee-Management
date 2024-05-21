package com.paradigm.tech.app.controller;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.*;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.*;
import com.paradigm.tech.app.services.TraineeExpService;
import com.paradigm.tech.app.services.impl.*;
import com.paradigm.tech.app.utlls.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import com.paradigm.tech.app.model.entityDTO.request.*;
import com.paradigm.tech.app.model.entityDTO.response.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION)
@RequiredArgsConstructor
public class TraineeExperienceController {
    private final TraineeExpService traineeExperienceService;

    @PostMapping(ApiPathConstant.TRAINEE_EXPERIENCE + "/create")
    public ResponseEntity<CustomResponseDTO<?>> saveTraineeExperience(
            @RequestBody RequestCreateTraineeExpDTO traineeExperience,
            Authentication authentication
    ) {
        ResponseCreateTraineeExpDTO savedTraineeExperience = traineeExperienceService.saveExperience(traineeExperience, authentication);
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Another chapter added to your journey! Trainee experience successfully recorded.", savedTraineeExperience));
    }

    @GetMapping(ApiPathConstant.TRAINEE_EXPERIENCE + "/all")
    public ResponseEntity<CustomResponseDTO<?>> getAllTraineeExperience() {
        List<ResponseGetTraineeExpDTO> getTraineeExperienceDTOS = traineeExperienceService.getAllExperience();
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "All trainee experiences retrieved successfully!", getTraineeExperienceDTOS));
    }

    @GetMapping(ApiPathConstant.TRAINEE_EXPERIENCE)
    public ResponseEntity<CustomResponseDTO<?>> getTraineeExperienceByTraineeId(Authentication authentication) {
        List<ResponseGetTraineeExpByTraineeDTO> responseTraineeExpGetByTraineeDTOS = traineeExperienceService.getExperienceByTraineeId(authentication);
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Your journey, your experiences! Trainee experiences fetched successfully!", responseTraineeExpGetByTraineeDTOS));
    }

    @GetMapping(ApiPathConstant.TRAINEE_EXPERIENCE + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> getTraineeExperienceById(@PathVariable String id) {
        ResponseGetTraineeExpDTO responseTraineeExpGetDTO = traineeExperienceService.getExperienceById(id);
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Trainee experience details retrieved successfully!", responseTraineeExpGetDTO));
    }

    @PutMapping(ApiPathConstant.TRAINEE_EXPERIENCE + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> updateTraineeExperience(
            Authentication authentication,
            @PathVariable String id,
            @RequestBody RequestUpdateTraineeExpDTO requestUpdateTraineeExpDTO
    ) {
        ResponseGetTraineeExpDTO updatedTraineeExperience = traineeExperienceService.updateExperience(authentication, requestUpdateTraineeExpDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Crafting your story! Trainee experience updated successfully!", updatedTraineeExperience));
    }

    @DeleteMapping(ApiPathConstant.TRAINEE_EXPERIENCE + "/{id}")
    public ResponseEntity<CustomResponseDTO<Void>> deleteTraineeExperience(@PathVariable String id, Authentication authentication) {
        traineeExperienceService.deleteExperience(authentication, id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Trainee experience deleted successfully!", null));
    }
}
