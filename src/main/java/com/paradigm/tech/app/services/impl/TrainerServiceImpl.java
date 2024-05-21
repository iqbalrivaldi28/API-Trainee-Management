package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.model.entityDTO.request.RequestTrainerCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.RequestTrainerUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.ResponseTrainerCreateDTO;
import com.paradigm.tech.app.model.entityDTO.response.ResponseTrainerGetDTO;
import com.paradigm.tech.app.model.entityDTO.response.ResponseTrainerUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.Trainer.ResponseTrainerGetAllDTO;
import com.paradigm.tech.app.repository.*;
import com.paradigm.tech.app.services.*;
import com.paradigm.tech.app.utlls.*;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MBatchRepository mBatchRepository;

    @Override
    public ResponseTrainerCreateDTO createTrainer(RequestTrainerCreateDTO requestTrainerCreateDTO) {
        try {
            String normalizedBatch = requestTrainerCreateDTO.getBatch().toUpperCase().replace(" ", "_");
            if (normalizedBatch.startsWith("OFFLINE")) {
                normalizedBatch = normalizedBatch.replace("OFFLINE", "OF");
            } else if (normalizedBatch.startsWith("ONLINE")) {
                normalizedBatch = normalizedBatch.replace("ONLINE", "ON");
            }
            EBatch eBatch = EBatch.valueOf(normalizedBatch);

            String encodedPassword = passwordEncoder.encode(requestTrainerCreateDTO.getPassword());

            Instant timestamp = Instant.now();
            User user = User.builder()
                    .email(requestTrainerCreateDTO.getEmail())
                    .password(encodedPassword)
                    .role(Role.builder()
                            .role(ERole.TRAINER)
                            .build())
                    .createdAt(timestamp)
                    .build();
            userRepository.save(user);

            M_Batch mBatch = mBatchRepository.findByBatchName(eBatch)
                    .orElseThrow(() -> new RuntimeException("Batch not found!"));

            Trainer trainer = Trainer.builder()
                    .user(user)
                    .firstName(requestTrainerCreateDTO.getFirstName())
                    .batch(mBatch)
                    .isDeleted(Boolean.FALSE)
                    .build();
            trainer = trainerRepository.save(trainer);

            return ResponseTrainerCreateDTO.builder()
                    .email(trainer.getUser().getEmail())
                    .firstName(trainer.getFirstName())
                    .role(ERole.TRAINER)
                    .batch(mBatch.getBatchName().toString())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public List<ResponseTrainerGetAllDTO> getAllTrainers() {
        List<Trainer> trainers = trainerRepository.findAll();
        List<ResponseTrainerGetAllDTO> trainerGetAllDTOList = new ArrayList<>();
        for (Trainer trainer : trainers) {
            ResponseTrainerGetAllDTO trainerGetAllDTO = ResponseTrainerGetAllDTO.builder()
                    .id(trainer.getId())
                    .email(trainer.getUser().getEmail())
                    .firstName(trainer.getFirstName())
                    .lastName(trainer.getLastName())
                    .role(trainer.getUser().getRole().getRole())
                    .batch(trainer.getBatch().getBatchName().getDescription())
                    .build();
            trainerGetAllDTOList.add(trainerGetAllDTO);
        }
        return trainerGetAllDTOList;
    }

    @Override
    public List<ResponseTrainerGetDTO> getActiveTrainers() {
        List<Trainer> trainers = trainerRepository.findByIsDeletedFalse();
        return trainers.stream().map(this::convertToGetTrainerDTO).collect(Collectors.toList());
    }

    @Override
    public ResponseTrainerGetDTO getTrainerProfile(Authentication authentication) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found!"));
            Optional<Trainer> trainer = trainerRepository.findByUserId(user.getId());

            if (trainer.isPresent()) {
                return ResponseTrainerGetDTO.builder()
                        .email(trainer.get().getUser().getEmail())
                        .firstName(trainer.get().getFirstName())
                        .lastName(trainer.get().getLastName())
                        .role(trainer.get().getUser().getRole().getRole())
                        .batch(trainer.get().getBatch().getBatchName().getDescription())
                        .build();
            } else {
                throw new RuntimeException("Trainer Detail not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseTrainerUpdateDTO updateTrainer(RequestTrainerUpdateDTO requestTrainerUpdateDTO) {
        try {
            User user = userRepository.findByEmail(requestTrainerUpdateDTO.getEmail()).orElse(null);
            if (user != null) {
                Optional<Trainer> trainer = trainerRepository.findByUserId(user.getId());
                if (trainer.isPresent()) {
                    if (requestTrainerUpdateDTO.getFirstName() == null) {
                        trainer.get().setFirstName(trainer.get().getFirstName());
                    } else {
                        trainer.get().setFirstName(requestTrainerUpdateDTO.getFirstName());
                    }
                    if (requestTrainerUpdateDTO.getLastName() == null) {
                        trainer.get().setLastName(trainer.get().getLastName());
                    } else {
                        trainer.get().setLastName(requestTrainerUpdateDTO.getLastName());
                    }
                    if (requestTrainerUpdateDTO.getBatch() == null) {
                        trainer.get().setBatch(trainer.get().getBatch());
                    } else {
                        String normalizedBatch = requestTrainerUpdateDTO.getBatch().toUpperCase().replace(" ", "_");
                        if (normalizedBatch.startsWith("OFFLINE")) {
                            normalizedBatch = normalizedBatch.replace("OFFLINE", "OF");
                        } else if (normalizedBatch.startsWith("ONLINE")) {
                            normalizedBatch = normalizedBatch.replace("ONLINE", "ON");
                        }
                        EBatch eBatch = EBatch.valueOf(normalizedBatch);
                        M_Batch mBatch = mBatchRepository.findByBatchName(eBatch)
                                .orElseThrow(() -> new RuntimeException("Batch not found!"));
                        trainer.get().setBatch(mBatch);
                    }

                    trainerRepository.save(trainer.get());

                    return ResponseTrainerUpdateDTO.builder()
                            .firstName(trainer.get().getFirstName())
                            .lastName(trainer.get().getLastName())
                            .batch(trainer.get().getBatch().getBatchName().toString())
                            .build();
                } else {
                    throw new RuntimeException("Trainer not found!");
                }
            } else {
                throw new RuntimeException("User not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating trainer: " + e.getMessage());
        }
    }

    @Override
    public void softDeleteTrainer(String id) {
        try {
            Optional<Trainer> optionalTrainer = trainerRepository.findById(id);
            optionalTrainer.ifPresent(trainer -> {
                if (!trainer.getIsDeleted()) {
                    trainer.setIsDeleted(true);
                    trainerRepository.save(trainer);
                } else {
                    System.out.println("Trainer with ID " + id + " has already been deleted.");
                }
            });
        } catch (Exception e) {
            System.err.println("Error while deleting trainer: " + e.getMessage());
        }
    }

    private ResponseTrainerGetDTO convertToGetTrainerDTO(Trainer trainer) {
        return ResponseTrainerGetDTO.builder()
                .email(trainer.getUser().getEmail())
                .firstName(trainer.getFirstName())
                .lastName(trainer.getLastName())
                .role(trainer.getUser().getRole().getRole())
                .batch(trainer.getBatch().getBatchName().toString())
                .build();
    }
}