package com.paradigm.tech.app.controller;

import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestCreateTraineeSkillDTO;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestUpdateTraineeSkillDTO;
import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.*;
import com.paradigm.tech.app.services.*;
import com.paradigm.tech.app.utlls.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION)
@RequiredArgsConstructor
public class TraineeSkillController {

    private final TraineeSkillService traineeSkillService;

    @PostMapping(ApiPathConstant.TRAINEE_SKILL + "/create")
    public ResponseEntity<CustomResponseDTO<ResponseCreateTraineeSkillDTO>> saveTraineeSkill(
            @RequestBody RequestCreateTraineeSkillDTO requestCreateTraineeSkillDTO,
            Authentication authentication
    ){
        ResponseCreateTraineeSkillDTO savedTraineeSkill = traineeSkillService.saveTraineeSkill(requestCreateTraineeSkillDTO, authentication);
        return new ResponseEntity<>(new CustomResponseDTO<>(true, HttpStatus.CREATED.value(), "Skill created successfully!", savedTraineeSkill), HttpStatus.CREATED);
    }

    @GetMapping(ApiPathConstant.TRAINEE_SKILL + "/all")
    public ResponseEntity<CustomResponseDTO<List<ResponseGetTraineeSkillDTO>>> getAllTraineeSkills(Authentication authentication) {
        List<ResponseGetTraineeSkillDTO> traineeSkills = traineeSkillService.getAllTraineeSkill();
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Successfully retrieved all trainee skills!", traineeSkills));
    }

    @GetMapping(ApiPathConstant.TRAINEE_SKILL)
    public ResponseEntity<CustomResponseDTO<List<ResponseGetTraineeSkillByTraineeDTO>>> getSkillByTraineeId(Authentication authentication) {
        List<ResponseGetTraineeSkillByTraineeDTO> traineeSkills = traineeSkillService.getTraineeSkillByTraineeId(authentication);
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Successfully retrieved trainee skills by trainee!", traineeSkills));
    }

    @GetMapping(ApiPathConstant.TRAINEE_SKILL + "/{id}")
    public ResponseEntity<CustomResponseDTO<ResponseGetTraineeSkillDTO>> getTraineeSkillById(@PathVariable String id) {
        ResponseGetTraineeSkillDTO traineeSkills = traineeSkillService.getTraineeSkillById(id);
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Successfully retrieved trainee skill by ID!", traineeSkills));
    }

    @PutMapping(ApiPathConstant.TRAINEE_SKILL + "/{id}")
    public ResponseEntity<CustomResponseDTO<ResponseUpdateTraineeSkillDTO>> updateTraineeSkill(
            Authentication authentication,
            @RequestBody RequestUpdateTraineeSkillDTO requestUpdateTraineeSkillDTO,
            @PathVariable String id
    ) {
        ResponseUpdateTraineeSkillDTO updatedTraineeSkill = traineeSkillService.updateTraineeSkill(authentication, requestUpdateTraineeSkillDTO, id);
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Skill updated successfully!", updatedTraineeSkill));
    }

    @DeleteMapping(ApiPathConstant.TRAINEE_SKILL + "/{id}")
    public ResponseEntity<CustomResponseDTO<Void>> deleteTraineeSkill(Authentication authentication, @PathVariable String id) {
        traineeSkillService.deleteTraineeSkill(authentication, id);
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Successfully deleted skill!", null));
    }
}
