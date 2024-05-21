package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.model.entityDTO.request.TraineeGrade.RequestTraineeGradeCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.TraineeGrade.RequestTraineeGradeUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.ResponseShortTraineeDetailDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeGrade.*;
import com.paradigm.tech.app.repository.*;
import com.paradigm.tech.app.services.*;
import com.paradigm.tech.app.utlls.EStatus;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TraineeGradeServiceImpl implements TraineeGradeService {
    private final TraineeDetailRepository traineeDetailRepository;
    private final TraineeGradeRepository traineeGradeRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final MBatchRepository mBatchRepository;
    private final TrainerRepository trainerRepository;

    @Override
    public ResponseTraineeGradeCreateDTO createTraineeGrade(RequestTraineeGradeCreateDTO requestTraineeGradeCreateDTO) {
        Trainee_Detail traineeDetail = traineeDetailRepository.findById(requestTraineeGradeCreateDTO.getTraineeDetailId()).get();

        String normalizeStatus = requestTraineeGradeCreateDTO.getStatus().toUpperCase();
        EStatus eStatus = EStatus.valueOf(normalizeStatus);
        Status status = statusRepository.findByStatus(eStatus).get();

        Trainee_Grade traineeGrade = Trainee_Grade.builder()
                .traineeDetail(traineeDetail)
                .status(status)
                .grade(requestTraineeGradeCreateDTO.getGrade())
                .build();

        traineeGradeRepository.save(traineeGrade);

        ResponseShortTraineeDetailDTO traineeDetailData = ResponseShortTraineeDetailDTO.builder()
                .firstName(traineeGrade.getTraineeDetail().getFirstName())
                .lastName(traineeGrade.getTraineeDetail().getLastName())
                .batch(traineeGrade.getTraineeDetail().getBatch().getBatchName().getDescription())
                .build();

        return ResponseTraineeGradeCreateDTO.builder()
                .status(traineeGrade.getStatus().getStatus().getDescription())
                .grade(traineeGrade.getGrade())
                .traineeDetail(traineeDetailData)
                .build();
    }

    @Override
    public List<ResponseTraineeGradeGetDTO> getAllTraineeGrades() {
        List<Trainee_Grade> traineeGrades = traineeGradeRepository.findAll();
        List<ResponseTraineeGradeGetDTO> traineeGradeGetDTOList = new ArrayList<>();
        for (Trainee_Grade traineeGrade : traineeGrades) {
            ResponseShortTraineeDetailDTO traineeDetail = ResponseShortTraineeDetailDTO.builder()
                    .firstName(traineeGrade.getTraineeDetail().getFirstName())
                    .lastName(traineeGrade.getTraineeDetail().getLastName())
                    .batch(traineeGrade.getTraineeDetail().getBatch().getBatchName().getDescription())
                    .build();

            ResponseTraineeGradeGetDTO traineeGradeGetDTO = ResponseTraineeGradeGetDTO.builder()
                    .status(traineeGrade.getStatus().getStatus().getDescription())
                    .grade(traineeGrade.getGrade())
                    .traineeDetail(traineeDetail)
                    .build();

            traineeGradeGetDTOList.add(traineeGradeGetDTO);
        }
        return traineeGradeGetDTOList;
    }

    @Override
    public List<ResponseTraineeGradeBatchAndTraineeDTO> getTraineeGradesByBatchAndTrainee(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElse(null);
        if (user != null) {
            Trainer trainer = trainerRepository.findByUserId(user.getId()).get();
            M_Batch mBatch = trainer.getBatch();

            List<Trainee_Detail> traineeDetails = traineeDetailRepository.findByBatch(mBatch);
            List<ResponseTraineeGradeBatchAndTraineeDTO> traineeGradeGetAllList = new ArrayList<>();
            for (Trainee_Detail traineeDetail : traineeDetails) {
                List<Trainee_Grade> traineeGrades = traineeGradeRepository.findByTraineeDetail(traineeDetail);
                List<ResponseTraineeGradeDTO> traineeGradeDTOList = new ArrayList<>();
                for (Trainee_Grade traineeGrade : traineeGrades) {
                    ResponseTraineeGradeDTO traineeGradeDTO = ResponseTraineeGradeDTO.builder()
                            .id(traineeGrade.getId())
                            .status(traineeGrade.getStatus().getStatus().getDescription())
                            .grade(traineeGrade.getGrade())
                            .build();
                    traineeGradeDTOList.add(traineeGradeDTO);
                }
                ResponseTraineeGradeBatchAndTraineeDTO traineeGradeList = ResponseTraineeGradeBatchAndTraineeDTO.builder()
                        .id(traineeDetail.getId())
                        .firstName(traineeDetail.getFirstName())
                        .lastName(traineeDetail.getLastName())
                        .batch(traineeDetail.getBatch().getBatchName().getDescription())
                        .traineeGrade(traineeGradeDTOList)
                        .build();
                traineeGradeGetAllList.add(traineeGradeList);
            }
            return traineeGradeGetAllList;
        } else {
            throw new RuntimeException("User not found!");
        }
    }

    @Override
    public ResponseTraineeGradeGetStatusDTO getTraineeGradesByStatus(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElse(null);
        if (user != null) {
            Trainer trainer = trainerRepository.findByUserId(user.getId()).get();
            M_Batch mBatch = trainer.getBatch();

            List<Trainee_Detail> traineeDetails = traineeDetailRepository.findByBatch(mBatch);
            List<ResponseTraineeGradeDTO> backend = new ArrayList<>();
            List<ResponseTraineeGradeDTO> frontend = new ArrayList<>();
            List<ResponseTraineeGradeDTO> mobile = new ArrayList<>();
            for (Trainee_Detail traineeDetail : traineeDetails) {
                List<Trainee_Grade> traineeGrades = traineeGradeRepository.findByTraineeDetail(traineeDetail);
                for (Trainee_Grade traineeGrade : traineeGrades) {
                    if (traineeGrade.getStatus().getStatus().equals(EStatus.BACKEND)) {
                        ResponseTraineeGradeDTO backendGrade = ResponseTraineeGradeDTO.builder()
                                .id(traineeGrade.getId())
                                .status(traineeGrade.getStatus().getStatus().getDescription())
                                .grade(traineeGrade.getGrade())
                                .build();
                        backend.add(backendGrade);
                    } else if (traineeGrade.getStatus().getStatus().equals(EStatus.FRONTEND)) {
                        ResponseTraineeGradeDTO frontendGrade = ResponseTraineeGradeDTO.builder()
                                .id(traineeGrade.getId())
                                .status(traineeGrade.getStatus().getStatus().getDescription())
                                .grade(traineeGrade.getGrade())
                                .build();
                        frontend.add(frontendGrade);
                    } else if (traineeGrade.getStatus().getStatus().equals(EStatus.MOBILE)) {
                        ResponseTraineeGradeDTO mobileGrade = ResponseTraineeGradeDTO.builder()
                                .id(traineeGrade.getId())
                                .status(traineeGrade.getStatus().getStatus().getDescription())
                                .grade(traineeGrade.getGrade())
                                .build();
                        mobile.add(mobileGrade);
                    }
                }
            }
            return ResponseTraineeGradeGetStatusDTO.builder()
                    .backend(backend)
                    .frontend(frontend)
                    .mobile(mobile)
                    .build();
        } else {
            throw new RuntimeException("User not found!");
        }
    }

    @Override
    public List<ResponseTraineeGradeDTO> getTraineeGradeByTrainee(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElse(null);
        if (user != null) {
            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            List<Trainee_Grade> traineeGrades = traineeGradeRepository.findByTraineeDetail(traineeDetail);
            List<ResponseTraineeGradeDTO> traineeGradeDTOList = new ArrayList<>();
            for (Trainee_Grade traineeGrade : traineeGrades) {
                ResponseTraineeGradeDTO traineeGradeDTO = ResponseTraineeGradeDTO.builder()
                        .id(traineeGrade.getId())
                        .status(traineeGrade.getStatus().getStatus().getDescription())
                        .grade(traineeGrade.getGrade())
                        .build();
                traineeGradeDTOList.add(traineeGradeDTO);
            }
            return traineeGradeDTOList;
        } else {
            throw new RuntimeException("User not found!");
        }
    }

    @Override
    public ResponseTraineeGradeGetDTO getTraineeGradeById(String id) {
        Trainee_Grade traineeGrade = traineeGradeRepository.findById(id).get();
        ResponseShortTraineeDetailDTO traineeDetail = ResponseShortTraineeDetailDTO.builder()
                .firstName(traineeGrade.getTraineeDetail().getFirstName())
                .lastName(traineeGrade.getTraineeDetail().getLastName())
                .batch(traineeGrade.getTraineeDetail().getBatch().getBatchName().getDescription())
                .build();

        return ResponseTraineeGradeGetDTO.builder()
                .status(traineeGrade.getStatus().getStatus().getDescription())
                .grade(traineeGrade.getGrade())
                .traineeDetail(traineeDetail)
                .build();
    }

    @Override
    public ResponseTraineeGradeGetDTO updateTraineeGrade(String id, RequestTraineeGradeUpdateDTO requestTraineeGradeUpdateDTO) {
        Trainee_Grade traineeGrade = traineeGradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trainee grade ID"));

        traineeGrade.setGrade(requestTraineeGradeUpdateDTO.getGrade());
        traineeGradeRepository.save(traineeGrade);

        ResponseShortTraineeDetailDTO traineeDetail = ResponseShortTraineeDetailDTO.builder()
                .firstName(traineeGrade.getTraineeDetail().getFirstName())
                .lastName(traineeGrade.getTraineeDetail().getLastName())
                .batch(traineeGrade.getTraineeDetail().getBatch().getBatchName().getDescription())
                .build();

        return ResponseTraineeGradeGetDTO.builder()
                .status(traineeGrade.getStatus().getStatus().getDescription())
                .grade(traineeGrade.getGrade())
                .traineeDetail(traineeDetail)
                .build();
    }
}