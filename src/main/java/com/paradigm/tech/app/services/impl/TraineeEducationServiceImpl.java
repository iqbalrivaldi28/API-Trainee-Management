package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestTraineeEduCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.RequestTraineeEduUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeEduCreateDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeEduGetByTraineeDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.ResponseTraineeEduGetDTO;
import com.paradigm.tech.app.repository.*;
import com.paradigm.tech.app.services.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TraineeEducationServiceImpl implements TraineeEduService {
    private final TraineeEducationRepo traineeEducationRepo;
    private final UserRepository userRepository;
    private final TraineeDetailRepository traineeDetailRepository;

    @Override
    public ResponseTraineeEduCreateDTO saveEducation(RequestTraineeEduCreateDTO education, Authentication authentication) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found for email: " + authentication.getName()));
            System.out.println(user);
            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            System.out.println(traineeDetail);
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found for user ID: " + user.getId());
            }

            Year graduationYear = Year.parse(education.getGraduationYear());
            System.out.println("Graduation Year: " + graduationYear);

            String cgpaStr = education.getGpaScore();
            System.out.println("CGPA: " + cgpaStr);
            if (cgpaStr == null || cgpaStr.trim().isEmpty()) {
                throw new RuntimeException("CGPA cannot be null or empty");
            }
            Float cGPA = Float.parseFloat(cgpaStr);

            Trainee_Education traineeEducation = Trainee_Education.builder()
                    .traineeDetail(traineeDetail)
                    .institutionName(education.getInstitutionName())
                    .fieldOfStudy(education.getFieldOfStudy())
                    .graduationYear(graduationYear)
                    .cGPA(cGPA)
                    .build();
            traineeEducationRepo.save(traineeEducation);

            ResponseShortTraineeDetailDTO traineeDetailData = ResponseShortTraineeDetailDTO.builder()
                    .firstName(traineeDetail.getFirstName())
                    .lastName(traineeDetail.getLastName())
                    .batch(traineeDetail.getBatch().getBatchName().getDescription())
                    .build();

            return ResponseTraineeEduCreateDTO.builder()
                    .institutionName(traineeEducation.getInstitutionName())
                    .fieldOfStudy(traineeEducation.getFieldOfStudy())
                    .graduationYear(traineeEducation.getGraduationYear().toString())
                    .cGPA(traineeEducation.getCGPA().toString())
                    .traineeDetail(traineeDetailData)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to save trainee education: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ResponseTraineeEduGetDTO> getAllEducation() {
        try {
            List<Trainee_Education> traineeEducations = traineeEducationRepo.findAll();
            List<ResponseTraineeEduGetDTO> traineeEduGetAllDTOList = new ArrayList<>(); for (Trainee_Education traineeEducation : traineeEducations) {
                Trainee_Detail traineeDetail = traineeEducation.getTraineeDetail();
                ResponseShortTraineeDetailDTO traineeDetailData = ResponseShortTraineeDetailDTO.builder()
                        .firstName(traineeDetail.getFirstName())
                        .lastName(traineeDetail.getLastName())
                        .batch(traineeDetail.getBatch().getBatchName().getDescription())
                        .build();

                ResponseTraineeEduGetDTO traineeEducationDTO = ResponseTraineeEduGetDTO.builder()
                        .institutionName(traineeEducation.getInstitutionName())
                        .fieldOfStudy(traineeEducation.getFieldOfStudy())
                        .graduationYear(traineeEducation.getGraduationYear().toString())
                        .cGPA(traineeEducation.getCGPA().toString())
                        .traineeDetail(traineeDetailData)
                        .build();

                traineeEduGetAllDTOList.add(traineeEducationDTO);
            }
            return traineeEduGetAllDTOList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch trainee education: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ResponseTraineeEduGetByTraineeDTO> getEducationByTraineeId(Authentication authentication) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found for email: " + authentication.getName()));
            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found for user ID: " + user.getId());
            }

            List<Trainee_Education> traineeEducations = traineeEducationRepo.findByTraineeDetailId(traineeDetail.getId());
            List<ResponseTraineeEduGetByTraineeDTO> traineeEduGetByTraineeDTOList = new ArrayList<>();
            for (Trainee_Education traineeEducation : traineeEducations) {
                ResponseTraineeEduGetByTraineeDTO traineeDTO = ResponseTraineeEduGetByTraineeDTO.builder()
                        .id(traineeEducation.getId())
                        .institutionName(traineeEducation.getInstitutionName())
                        .fieldOfStudy(traineeEducation.getFieldOfStudy())
                        .graduationYear(traineeEducation.getGraduationYear().toString())
                        .cGPA(traineeEducation.getCGPA().toString())
                        .build();
                traineeEduGetByTraineeDTOList.add(traineeDTO);
            }
            return traineeEduGetByTraineeDTOList;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseTraineeEduGetDTO getEducationById(String id) {
        try {
            Trainee_Education traineeEducation = traineeEducationRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Trainee education not found!"));
            Trainee_Detail traineeDetail = traineeEducation.getTraineeDetail();

            ResponseShortTraineeDetailDTO traineeDetailData = ResponseShortTraineeDetailDTO.builder()
                    .firstName(traineeDetail.getFirstName())
                    .lastName(traineeDetail.getLastName())
                    .batch(traineeDetail.getBatch().getBatchName().getDescription())
                    .build();

            return ResponseTraineeEduGetDTO.builder()
                    .institutionName(traineeEducation.getInstitutionName())
                    .fieldOfStudy(traineeEducation.getFieldOfStudy())
                    .graduationYear(traineeEducation.getGraduationYear().toString())
                    .cGPA(traineeEducation.getCGPA().toString())
                    .traineeDetail(traineeDetailData)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch trainee education: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseTraineeEduGetDTO updateEducation(Authentication authentication, RequestTraineeEduUpdateDTO requestTraineeEduUpdateDTO, String id) {
        try {
            System.out.println(authentication);
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found for email: " + authentication.getName()));
            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found for user ID: " + user.getId());
            }

            Trainee_Education traineeEducation = traineeEducationRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Trainee education not found!"));

            if (!traineeEducation.getTraineeDetail().equals(traineeDetail)) {
                throw new RuntimeException("You're not authorized to update this trainee's education!");
            }

            if (requestTraineeEduUpdateDTO.getInstitutionName() != null) {
                traineeEducation.setInstitutionName(requestTraineeEduUpdateDTO.getInstitutionName());
            }
            if (requestTraineeEduUpdateDTO.getFieldOfStudy() != null) {
                traineeEducation.setFieldOfStudy(requestTraineeEduUpdateDTO.getFieldOfStudy());
            }
            if (requestTraineeEduUpdateDTO.getGraduationYear() != null) {
                traineeEducation.setGraduationYear(Year.parse(requestTraineeEduUpdateDTO.getGraduationYear()));
            }
            if (requestTraineeEduUpdateDTO.getGpaScore() != null) {
                traineeEducation.setCGPA(Float.parseFloat(requestTraineeEduUpdateDTO.getGpaScore()));
            }

            traineeEducationRepo.save(traineeEducation);

            ResponseShortTraineeDetailDTO traineeDetailData = ResponseShortTraineeDetailDTO.builder()
                    .firstName(traineeDetail.getFirstName())
                    .lastName(traineeDetail.getLastName())
                    .batch(traineeDetail.getBatch().getBatchName().getDescription())
                    .build();

            return ResponseTraineeEduGetDTO.builder()
                    .institutionName(traineeEducation.getInstitutionName())
                    .fieldOfStudy(traineeEducation.getFieldOfStudy())
                    .graduationYear(traineeEducation.getGraduationYear().toString())
                    .cGPA(traineeEducation.getCGPA().toString())
                    .traineeDetail(traineeDetailData)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to update trainee education: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteEducation(String id) {
        try {
            traineeEducationRepo.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to delete trainee education: " + e.getMessage(), e);
        }
    }
}