package com.paradigm.tech.app.controller;

import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestTraineeDetailCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestTraineeDetailUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeDetailCreateDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeDetailGetAllDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeDetailGetDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeDetailUpdateDTO;
import com.paradigm.tech.app.services.TraineeDetailService;
import com.paradigm.tech.app.utlls.ApiPathConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION)
public class TraineeDetailController {
    private final TraineeDetailService traineeDetailService;

    @PostMapping(ApiPathConstant.TRAINEE_DETAIL + "/create")
    public ResponseEntity<CustomResponseDTO<?>> saveTraineeDetail(@RequestBody RequestTraineeDetailCreateDTO requestTraineeDetailCreateDTO) {
        ResponseTraineeDetailCreateDTO result = traineeDetailService.saveTraineeDetail(requestTraineeDetailCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponseDTO<>(true, 200, "success create trainee!", result));
    }

    @GetMapping(ApiPathConstant.TRAINEE_DETAIL + "/all")
    public ResponseEntity<CustomResponseDTO<?>> getAllTraineeDetail() {
        List<ResponseTraineeDetailGetAllDTO> traineeDetails = traineeDetailService.getAllTraineeDetail();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "All trainee details fetched!", traineeDetails));
    }

    @GetMapping(ApiPathConstant.TRAINEE_DETAIL)
    public ResponseEntity<CustomResponseDTO<?>> getAllAvailTraineeDetail() {
        List<ResponseTraineeDetailGetDTO> traineeDetails = traineeDetailService.getAllAvailTraineeDetail();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "All trainee details fetched!", traineeDetails));
    }

    @GetMapping(ApiPathConstant.TRAINEE_DETAIL + "/profile")
    public ResponseEntity<CustomResponseDTO<?>> getTraineeProfile(Authentication authentication) {
        ResponseTraineeDetailGetDTO traineeDetail = traineeDetailService.getTraineeProfile(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Trainee detail fetched", traineeDetail));
    }

    @PutMapping(ApiPathConstant.TRAINEE_DETAIL)
    public ResponseEntity<CustomResponseDTO<?>> updateDetailTrainee(@RequestBody RequestTraineeDetailUpdateDTO requestTraineeDetailUpdateDTO) {
        ResponseTraineeDetailUpdateDTO response = traineeDetailService.updateTraineeDetail(requestTraineeDetailUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Trainee detail updated successfully", response));
    }

    @DeleteMapping(ApiPathConstant.TRAINEE_DETAIL + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> deleteTraineeDetail(@PathVariable String id) {
        traineeDetailService.softDeleteTraineeDetail(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Trainee detail deleted", null));
    }

    // GET BY ID USE AUTHENTICATION
}
