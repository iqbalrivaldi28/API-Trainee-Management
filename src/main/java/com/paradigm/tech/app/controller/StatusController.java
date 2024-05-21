package com.paradigm.tech.app.controller;

import com.paradigm.tech.app.services.StatusService;
import com.paradigm.tech.app.utlls.ApiPathConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.model.entityDTO.response.Enums.ResponseStatusDTO;

import java.util.List;

@RestController
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION + ApiPathConstant.M_STATUS)
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;

    @GetMapping("/grade-status")
    public ResponseEntity<CustomResponseDTO<List<ResponseStatusDTO>>> getGradeStatus() {
        List<ResponseStatusDTO> savedStatusResponse = statusService.getGradeStatus();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Success get grade status!", savedStatusResponse));
    }

    @GetMapping("/application-status")
    public ResponseEntity<CustomResponseDTO<List<ResponseStatusDTO>>> getApplicationStatus() {
        List<ResponseStatusDTO> savedStatusResponse = statusService.getApplicationStatus();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Success get application status!", savedStatusResponse));
    }

    @GetMapping("/vacancy-status")
    public ResponseEntity<CustomResponseDTO<List<ResponseStatusDTO>>> getVacancyStatus() {
        List<ResponseStatusDTO> savedStatusResponse = statusService.getVacancyStatus();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Success get vacancy status!", savedStatusResponse));
    }

    @GetMapping("/bootcamp-status")
    public ResponseEntity<CustomResponseDTO<List<ResponseStatusDTO>>> getBootcampStatus() {
        List<ResponseStatusDTO> savedStatusResponse = statusService.getBootcampStatus();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Success get bootcamp status!", savedStatusResponse));
    }

    @GetMapping("/all-status")
    public ResponseEntity<CustomResponseDTO<List<ResponseStatusDTO>>> getAllStatus() {
        List<ResponseStatusDTO> savedStatusResponse = statusService.getAllStatus();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Success get all statuses!", savedStatusResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponseDTO<ResponseStatusDTO>> getStatusById(@PathVariable String id) {
        ResponseStatusDTO savedStatusResponse = statusService.getStatusById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Success get status by ID!", savedStatusResponse));
    }
}
