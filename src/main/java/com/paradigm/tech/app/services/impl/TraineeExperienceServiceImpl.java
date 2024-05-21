package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.*;
import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.*;
import com.paradigm.tech.app.repository.*;
import com.paradigm.tech.app.services.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;

import java.text.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TraineeExperienceServiceImpl implements TraineeExpService {
    private final TraineeExperienceRepo traineeExperienceRepo;
    private final UserRepository userRepository;
    private final TraineeDetailRepository traineeDetailRepository;

    @Override
    public ResponseCreateTraineeExpDTO saveExperience(RequestCreateTraineeExpDTO requestCreateTraineeExpDTO, Authentication authentication) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found for email: " + authentication.getName()));

            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found for user ID: " + user.getId());
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
            Date startDate = dateFormat.parse(requestCreateTraineeExpDTO.getStartDate());
            Date endDate = dateFormat.parse(requestCreateTraineeExpDTO.getEndDate());

            Trainee_Experience traineeExperience = Trainee_Experience.builder()
                    .companyName(requestCreateTraineeExpDTO.getCompanyName())
                    .position(requestCreateTraineeExpDTO.getPosition())
                    .description(requestCreateTraineeExpDTO.getDescription())
                    .startDate(startDate)
                    .endDate(endDate)
                    .traineeDetail(traineeDetail)
                    .build();

            traineeExperienceRepo.save(traineeExperience);

            return buildResponseCreateTraineeExpDTO(traineeExperience);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save trainee experience: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ResponseGetTraineeExpDTO> getAllExperience() {
        try {
            List<Trainee_Experience> traineeExperiences = traineeExperienceRepo.findAll();
            List<ResponseGetTraineeExpDTO> getAllTraineeExpDTOList = new ArrayList<>();
            for (Trainee_Experience traineeExperience : traineeExperiences) {
                getAllTraineeExpDTOList.add(buildResponseGetTraineeExpDTO(traineeExperience));
            }
            return getAllTraineeExpDTOList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch trainee education: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ResponseGetTraineeExpByTraineeDTO> getExperienceByTraineeId(Authentication authentication) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found for email: " + authentication.getName()));
            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found for user ID: " + user.getId());
            }

            List<Trainee_Experience> traineeExperiences = traineeExperienceRepo.findByTraineeDetail(traineeDetail);
            List<ResponseGetTraineeExpByTraineeDTO> getTraineeExpByTraineeDTOS = new ArrayList<>();
            for (Trainee_Experience traineeExperience : traineeExperiences) {
                getTraineeExpByTraineeDTOS.add(buildResponseGetTraineeExpByTraineeDTO(traineeExperience));
            }
            return getTraineeExpByTraineeDTOS;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseGetTraineeExpDTO getExperienceById(String id) {
        try {
            Trainee_Experience traineeExperience = traineeExperienceRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Experience not found for id: " + id));

            return buildResponseGetTraineeExpDTO(traineeExperience);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch trainee experience: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseGetTraineeExpDTO updateExperience(Authentication authentication, RequestUpdateTraineeExpDTO requestUpdateTraineeExpDTO, String id) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found");
            }

            Trainee_Experience traineeExperience = traineeExperienceRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Experience not found for id: " + id));

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");

            if (requestUpdateTraineeExpDTO.getCompanyName() != null) {
                traineeExperience.setCompanyName(requestUpdateTraineeExpDTO.getCompanyName());
            }
            if (requestUpdateTraineeExpDTO.getPosition() != null) {
                traineeExperience.setPosition(requestUpdateTraineeExpDTO.getPosition());
            }
            if (requestUpdateTraineeExpDTO.getDescription() != null) {
                traineeExperience.setDescription(requestUpdateTraineeExpDTO.getDescription());
            }
            if (requestUpdateTraineeExpDTO.getStartDate() != null) {
                Date startDate = dateFormat.parse(requestUpdateTraineeExpDTO.getStartDate());
                traineeExperience.setStartDate(startDate);
            }
            if (requestUpdateTraineeExpDTO.getEndDate() != null) {
                Date endDate = dateFormat.parse(requestUpdateTraineeExpDTO.getEndDate());
                traineeExperience.setEndDate(endDate);
            }

            traineeExperienceRepo.save(traineeExperience);

            return buildResponseGetTraineeExpDTO(traineeExperience);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update trainee experience: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteExperience(Authentication authentication, String id) {
        try {
            traineeExperienceRepo.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseCreateTraineeExpDTO buildResponseCreateTraineeExpDTO(Trainee_Experience traineeExperience) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
        return ResponseCreateTraineeExpDTO.builder()
                .companyName(traineeExperience.getCompanyName())
                .position(traineeExperience.getPosition())
                .description(traineeExperience.getDescription())
                .startDate(dateFormat.format(traineeExperience.getStartDate()))
                .endDate(dateFormat.format(traineeExperience.getEndDate()))
                .build();
    }

    private ResponseGetTraineeExpDTO buildResponseGetTraineeExpDTO(Trainee_Experience traineeExperience) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
        ResponseShortTraineeDetailDTO traineeDetailData = ResponseShortTraineeDetailDTO.builder()
                .firstName(traineeExperience.getTraineeDetail().getFirstName())
                .lastName(traineeExperience.getTraineeDetail().getLastName())
                .batch(traineeExperience.getTraineeDetail().getBatch().getBatchName().getDescription())
                .build();

        return ResponseGetTraineeExpDTO.builder()
                .companyName(traineeExperience.getCompanyName())
                .position(traineeExperience.getPosition())
                .description(traineeExperience.getDescription())
                .startDate(dateFormat.format(traineeExperience.getStartDate()))
                .endDate(dateFormat.format(traineeExperience.getEndDate()))
                .traineeDetail(traineeDetailData)
                .build();
    }

    private ResponseGetTraineeExpByTraineeDTO buildResponseGetTraineeExpByTraineeDTO(Trainee_Experience traineeExperience) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
        return ResponseGetTraineeExpByTraineeDTO.builder()
                .id(traineeExperience.getId())
                .companyName(traineeExperience.getCompanyName())
                .position(traineeExperience.getPosition())
                .description(traineeExperience.getDescription())
                .startDate(dateFormat.format(traineeExperience.getStartDate()))
                .endDate(dateFormat.format(traineeExperience.getEndDate()))
                .build();
    }
}
