package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestTraineeDetailCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestTraineeDetailUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeDetailCreateDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeDetailGetAllDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeDetailGetDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeDetailUpdateDTO;
import com.paradigm.tech.app.repository.*;
import com.paradigm.tech.app.services.TraineeDetailService;
import com.paradigm.tech.app.utlls.EBatch;
import com.paradigm.tech.app.utlls.ERole;
import com.paradigm.tech.app.utlls.EStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TraineeDetailServiceImpl implements TraineeDetailService {
    private final UserRepository userRepository;
    private final TraineeDetailRepository traineeDetailRepository;
    private final PasswordEncoder passwordEncoder;
    private  final StatusRepository statusRepository;
    private final MBatchRepository mBatchRepository;


    @Override
    public ResponseTraineeDetailCreateDTO saveTraineeDetail(RequestTraineeDetailCreateDTO requestTraineeDetailCreateDTO) {
        try {
            Instant timestamp = Instant.now();
            User user = User.builder()
                    .email(requestTraineeDetailCreateDTO.getEmail())
                    .password(passwordEncoder.encode(requestTraineeDetailCreateDTO.getPassword()))
                    .role(Role.builder()
                            .role(ERole.TRAINEE)
                            .build())
                    .createdAt(timestamp)
                    .build();

            userRepository.save(user);
            System.out.println(user);

            String normalizeBatch = requestTraineeDetailCreateDTO.getBatch().toUpperCase().replace(" ", "_");
            if (normalizeBatch.startsWith("OFFLINE")) {
                normalizeBatch = normalizeBatch.replace("OFFLINE", "OF");
            } else if (normalizeBatch.startsWith("ONLINE")) {
                normalizeBatch = normalizeBatch.replace("ONLINE", "ON");
            }
            EBatch eBatch = EBatch.valueOf(normalizeBatch);
            M_Batch mBatch = mBatchRepository.findByBatchName(eBatch).get();
            Status status = statusRepository.findByStatus(EStatus.BACKEND).get();

            Trainee_Detail traineeDetail = Trainee_Detail.builder()
                    .firstName(requestTraineeDetailCreateDTO.getFirstName())
                    .joinedAt(requestTraineeDetailCreateDTO.getJoinedAt())
                    .isDeleted(Boolean.FALSE)
                    .user(user)
                    .batch(mBatch)
                    .status(status)
                    .build();
            System.out.println(traineeDetail);

            traineeDetailRepository.save(traineeDetail);

            return ResponseTraineeDetailCreateDTO.builder()
                    .email(user.getEmail())
                    .firstName(traineeDetail.getFirstName())
                    .batch(traineeDetail.getBatch().getBatchName().getDescription())
                    .status(traineeDetail.getStatus().getStatus().getDescription())
                    .build();
        } catch (ConfigDataResourceNotFoundException e) {
            // Handle specific not found exceptions
            throw new RuntimeException("Resource not found: " + e.getMessage());
        } catch ( RuntimeException e) {
            // Handle other specific exceptions
            throw new RuntimeException("Error occurred while saving trainee detail: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public List<ResponseTraineeDetailGetAllDTO> getAllTraineeDetail() {
        try {
            List<Trainee_Detail> traineeDetails = traineeDetailRepository.findAll();
            if (traineeDetails != null) {
                List<ResponseTraineeDetailGetAllDTO> traineeDetailDTOList = new ArrayList<>();
                for (Trainee_Detail traineeDetail : traineeDetails) {
                    User user = userRepository.findById(traineeDetail.getUser().getId()).get();
                    ResponseTraineeDetailGetAllDTO traineeDetailDTO = ResponseTraineeDetailGetAllDTO.builder()
                            .id(traineeDetail.getId())
                            .email(user.getEmail())
                            .firstName(traineeDetail.getFirstName())
                            .lastName(traineeDetail.getLastName())
                            .dateOfBirth(traineeDetail.getDateOfBirth())
                            .phone(traineeDetail.getPhone())
                            .joinedAt(traineeDetail.getJoinedAt())
                            .batch(traineeDetail.getBatch().getBatchName().getDescription())
                            .status(traineeDetail.getStatus().getStatus().getDescription())
                            .build();

                    traineeDetailDTOList.add(traineeDetailDTO);
                }
                return traineeDetailDTOList;
            } else {
                throw new RuntimeException("Trainee Details not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public List<ResponseTraineeDetailGetDTO> getAllAvailTraineeDetail() {
        try {
            List<Trainee_Detail> traineeDetails = traineeDetailRepository.findAllByIsDeletedIsFalse();
            if (traineeDetails != null) {
                List<ResponseTraineeDetailGetDTO> traineeDetailDTOList = new ArrayList<>();
                for (Trainee_Detail traineeDetail : traineeDetails) {
                    User user = userRepository.findById(traineeDetail.getUser().getId()).get();
                    ResponseTraineeDetailGetDTO traineeDetailDTO = ResponseTraineeDetailGetDTO.builder()
                            .email(user.getEmail())
                            .firstName(traineeDetail.getFirstName())
                            .lastName(traineeDetail.getLastName())
                            .dateOfBirth(traineeDetail.getDateOfBirth())
                            .phone(traineeDetail.getPhone())
                            .joinedAt(traineeDetail.getJoinedAt())
                            .batch(traineeDetail.getBatch().getBatchName().getDescription())
                            .status(traineeDetail.getStatus().getStatus().getDescription())
                            .build();

                    traineeDetailDTOList.add(traineeDetailDTO);
                }
                return traineeDetailDTOList;
            } else {
                throw new RuntimeException("Trainee Details not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseTraineeDetailGetDTO getTraineeProfile (Authentication authentication) {
        try {
            User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found!"));
            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());

            if (traineeDetail != null) {
                return ResponseTraineeDetailGetDTO.builder()
                        .email(user.getEmail())
                        .firstName(traineeDetail.getFirstName())
                        .lastName(traineeDetail.getLastName())
                        .dateOfBirth(traineeDetail.getDateOfBirth())
                        .placeOfBirth(traineeDetail.getPlaceOfBirth())
                        .phone(traineeDetail.getPhone())
                        .joinedAt(traineeDetail.getJoinedAt())
                        .batch(traineeDetail.getBatch().getBatchName().getDescription())
                        .status(traineeDetail.getStatus().getStatus().getDescription())
                        .build();
            } else {
                throw new RuntimeException("Trainee Detail not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public void softDeleteTraineeDetail(String id) {
        try {
            List<Trainee_Detail> traineeDetails = traineeDetailRepository.findAllByIsDeletedIsFalse();
            for (Trainee_Detail traineeDetail : traineeDetails) {
                if (traineeDetail.getId().equals(id)) {
                    traineeDetailRepository.deleteById(id);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseTraineeDetailUpdateDTO updateTraineeDetail(RequestTraineeDetailUpdateDTO requestTraineeDetailUpdateDTO) {
        try {
            User user = userRepository.findByEmail(requestTraineeDetailUpdateDTO.getEmail()).orElse(null);
            if (user != null) {
                Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
                    if (requestTraineeDetailUpdateDTO.getFirstName() == null) {
                        traineeDetail.setFirstName(traineeDetail.getFirstName());
                    } else {
                        traineeDetail.setFirstName(requestTraineeDetailUpdateDTO.getFirstName());
                    }
                    if (requestTraineeDetailUpdateDTO.getLastName() == null) {
                        traineeDetail.setLastName(traineeDetail.getLastName());
                    } else {
                        traineeDetail.setLastName(requestTraineeDetailUpdateDTO.getLastName());
                    }
                    if (requestTraineeDetailUpdateDTO.getDateOfBirth() == null) {
                        traineeDetail.setDateOfBirth(traineeDetail.getDateOfBirth());
                    } else {
                        traineeDetail.setDateOfBirth(requestTraineeDetailUpdateDTO.getDateOfBirth());
                    }
                    if (requestTraineeDetailUpdateDTO.getPlaceOfBirth() == null) {
                        traineeDetail.setPlaceOfBirth(traineeDetail.getPlaceOfBirth());
                    } else {
                        traineeDetail.setPlaceOfBirth(requestTraineeDetailUpdateDTO.getPlaceOfBirth());
                    }
                    if (requestTraineeDetailUpdateDTO.getPhone() == null) {
                        traineeDetail.setPhone(traineeDetail.getPhone());
                    } else {
                        traineeDetail.setPhone(requestTraineeDetailUpdateDTO.getPhone());
                    }
                    traineeDetailRepository.save(traineeDetail);

                    return ResponseTraineeDetailUpdateDTO.builder()
                            .firstName(traineeDetail.getFirstName())
                            .lastName(traineeDetail.getLastName())
                            .dateOfBirth(traineeDetail.getDateOfBirth())
                            .placeOfBirth(traineeDetail.getPlaceOfBirth())
                            .phone(traineeDetail.getPhone())
                            .joinedAt(traineeDetail.getJoinedAt())
                            .batch(traineeDetail.getBatch().getBatchName().getDescription())
                            .status(traineeDetail.getStatus().getStatus().getDescription())
                            .build();
            }
            else {
                throw new RuntimeException("User not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }
}
