package com.paradigm.tech.app.controller;

import com.paradigm.tech.app.model.entityDTO.request.RequestTrainerCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.RequestTrainerUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.model.entityDTO.response.ResponseTrainerCreateDTO;
import com.paradigm.tech.app.model.entityDTO.response.ResponseTrainerGetDTO;
import com.paradigm.tech.app.model.entityDTO.response.ResponseTrainerUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.Trainer.ResponseTrainerGetAllDTO;
import com.paradigm.tech.app.repository.*;
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
public class TrainerController {

    private final TrainerService trainerService;
    private final TrainerRepository trainerRepository;

    @PostMapping(ApiPathConstant.TRAINER + "/create")
    public ResponseEntity<CustomResponseDTO<?>> createTrainer(@RequestBody RequestTrainerCreateDTO requestTrainerCreateDTO) {
        ResponseTrainerCreateDTO responseCreateTrainerDTO = trainerService.createTrainer(requestTrainerCreateDTO);
        CustomResponseDTO<?> response = CustomResponseDTO.<ResponseTrainerCreateDTO>builder()
                .status(true)
                .code(HttpStatus.CREATED.value())
                .message("Trainer created successfully!")
                .data(responseCreateTrainerDTO)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(ApiPathConstant.TRAINER + "/all")
    public ResponseEntity<CustomResponseDTO<?>> getAllTrainers() {
        List<ResponseTrainerGetAllDTO> trainers = trainerService.getAllTrainers();
        CustomResponseDTO<?> response = CustomResponseDTO.<List<ResponseTrainerGetAllDTO>>builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List of all trainers")
                .data(trainers)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(ApiPathConstant.TRAINER + "/active")
    public ResponseEntity<CustomResponseDTO<?>> getActiveTrainers() {
        List<ResponseTrainerGetDTO> trainers = trainerService.getActiveTrainers();
        CustomResponseDTO<?> response = CustomResponseDTO.<List<ResponseTrainerGetDTO>>builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List of active trainers")
                .data(trainers)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(ApiPathConstant.TRAINER + "/profile")
    public ResponseEntity<CustomResponseDTO<?>> getTrainerById(Authentication authentication) {
        ResponseTrainerGetDTO trainerDTO = trainerService.getTrainerProfile(authentication);
        CustomResponseDTO<?> response = CustomResponseDTO.<ResponseTrainerGetDTO>builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Trainer details")
                .data(trainerDTO)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(ApiPathConstant.TRAINER + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> updateTrainer(Authentication authentication, @RequestBody RequestTrainerUpdateDTO requestUpdateTrainerDTO) {
        try {
            ResponseTrainerUpdateDTO updatedTrainer = trainerService.updateTrainer(requestUpdateTrainerDTO);
            CustomResponseDTO<?> response = CustomResponseDTO.<ResponseTrainerUpdateDTO>builder()
                    .status(true)
                    .code(HttpStatus.OK.value())
                    .message("Trainer updated successfully!")
                    .data(updatedTrainer)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            CustomResponseDTO<?> response = CustomResponseDTO.<ResponseTrainerUpdateDTO>builder()
                    .status(false)
                    .code(HttpStatus.NOT_FOUND.value())
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping(ApiPathConstant.TRAINER + "/{id}")
    public ResponseEntity<CustomResponseDTO<String>> softDeleteTrainer(@PathVariable String id) {
        try {
            trainerService.softDeleteTrainer(id);
            CustomResponseDTO<String> response = CustomResponseDTO.<String>builder()
                    .status(true)
                    .code(HttpStatus.OK.value())
                    .message("Trainer deleted successfully!")
                    .data(null )
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            CustomResponseDTO<String> response = CustomResponseDTO.<String>builder()
                    .status(false)
                    .code(HttpStatus.NOT_FOUND.value())
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
