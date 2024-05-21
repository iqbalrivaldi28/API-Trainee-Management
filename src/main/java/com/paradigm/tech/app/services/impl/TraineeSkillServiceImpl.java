package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.*;
import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.*;
import com.paradigm.tech.app.repository.*;
import com.paradigm.tech.app.services.*;
import com.paradigm.tech.app.utlls.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
@RequiredArgsConstructor
public class TraineeSkillServiceImpl implements TraineeSkillService {
    private final TraineeDetailRepository traineeDetailRepository;
    private final UserRepository userRepository;
    private final TraineeSkillRepo traineeSkillRepo;
    private final MSkillRepository mSkillRepository;

    @Override
    public ResponseCreateTraineeSkillDTO saveTraineeSkill(RequestCreateTraineeSkillDTO requestCreateTraineeSkillDTO, Authentication authentication) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found for email: " + authentication.getName()));

            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found for user ID: " + user.getId());
            }

            String normalizedSkill = requestCreateTraineeSkillDTO.getSkill().toUpperCase();
            ESkill skill = ESkill.valueOf(normalizedSkill);

            M_Skill mSkill = mSkillRepository.findBySkillName(skill)
                    .orElseThrow(() -> new RuntimeException("Skill with name '" + skill + "' not found"));

            String normalizedLevel = requestCreateTraineeSkillDTO.getLevel().toUpperCase();
            ELevel level = ELevel.valueOf(normalizedLevel);

            Trainee_Skill traineeSkill = Trainee_Skill.builder()
                    .skill(mSkill)
                    .level(level)
                    .traineeDetail(traineeDetail)
                    .build();

            traineeSkillRepo.save(traineeSkill);

            return buildResCreateTraineeSkillDTO(traineeSkill);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid skill or level: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to save trainee skill: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ResponseGetTraineeSkillDTO> getAllTraineeSkill() {
        try {
            List<Trainee_Skill> allTraineeSkills = traineeSkillRepo.findAll();

            List<ResponseGetTraineeSkillDTO> responseDTOs = allTraineeSkills.stream()
                    .map(this::buildResGetTraineeSkillDTO)
                    .collect(Collectors.toList());

            return responseDTOs;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch trainee education: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ResponseGetTraineeSkillByTraineeDTO> getTraineeSkillByTraineeId(Authentication authentication) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found");
            }

            List<Trainee_Skill> traineeSkills = traineeSkillRepo.findByTraineeDetail(traineeDetail);

            List<ResponseGetTraineeSkillByTraineeDTO> responseDTOs = traineeSkills.stream()
                    .map(this::buildResGetTraineeSkillByTraineeDTO)
                    .collect(Collectors.toList());

            return responseDTOs;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch trainee skills: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseGetTraineeSkillDTO getTraineeSkillById(String id) {
        try {
            Trainee_Skill traineeSkill = traineeSkillRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Trainee skill not found for ID: " + id));

            return buildResGetTraineeSkillDTO(traineeSkill);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch trainee skill: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseUpdateTraineeSkillDTO updateTraineeSkill(Authentication authentication, RequestUpdateTraineeSkillDTO requestUpdateTraineeSkillDTO, String id) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found for email: " + authentication.getName()));

            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found for user ID: " + user.getId());
            }

            Trainee_Skill existingTraineeSkill = traineeSkillRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Trainee language not found for ID: " + id));

            if (!existingTraineeSkill.getTraineeDetail().equals(traineeDetail)) {
                throw new RuntimeException("You're not authorized to update this trainee's skill!");
            }

            if (requestUpdateTraineeSkillDTO.getSkill() != null) {
                String normalizedSkill = requestUpdateTraineeSkillDTO.getSkill().toUpperCase();
                ESkill skill = ESkill.valueOf(normalizedSkill);

                M_Skill mSkill = mSkillRepository.findBySkillName(skill)
                        .orElseThrow(() -> new RuntimeException("Skill not found for name: " + skill));
                existingTraineeSkill.setSkill(mSkill);
            }

            if (requestUpdateTraineeSkillDTO.getLevel() != null) {
                String normalizedLevel = requestUpdateTraineeSkillDTO.getLevel().toUpperCase();
                ELevel level = ELevel.valueOf(normalizedLevel);
                existingTraineeSkill.setLevel(level);
            }

            Trainee_Skill updatedTraineeSkill = traineeSkillRepo.save(existingTraineeSkill);

            return buildResUpdateTraineeSkillDTO(updatedTraineeSkill);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update trainee skill: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteTraineeSkill(Authentication authentication, String id) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found");
            }

            traineeSkillRepo.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseCreateTraineeSkillDTO buildResCreateTraineeSkillDTO(Trainee_Skill traineeSkill) {
        return ResponseCreateTraineeSkillDTO.builder()
                .skill(traineeSkill.getSkill().getSkillName().toString())
                .level(traineeSkill.getLevel().toString())
                .build();
    }

    private ResponseGetTraineeSkillDTO buildResGetTraineeSkillDTO(Trainee_Skill traineeSkill) {
        Trainee_Detail traineeDetail = traineeSkill.getTraineeDetail();

        ResponseShortTraineeDetailDTO traineeDetailData = ResponseShortTraineeDetailDTO.builder()
                .firstName(traineeDetail.getFirstName())
                .lastName(traineeDetail.getLastName())
                .batch(traineeDetail.getBatch().getBatchName().getDescription())
                .build();

        return ResponseGetTraineeSkillDTO.builder()
                .traineeDetail(traineeDetailData)
                .skill(traineeSkill.getSkill().getSkillName().toString())
                .level(traineeSkill.getLevel().toString())
                .build();
    }

    private ResponseGetTraineeSkillByTraineeDTO buildResGetTraineeSkillByTraineeDTO(Trainee_Skill traineeSkill) {
        return ResponseGetTraineeSkillByTraineeDTO.builder()
                .id(traineeSkill.getId())
                .skill(traineeSkill.getSkill().getSkillName().toString())
                .level(traineeSkill.getLevel().toString())
                .build();
    }

    private ResponseUpdateTraineeSkillDTO buildResUpdateTraineeSkillDTO(Trainee_Skill traineeSkill) {
        ResponseUpdateTraineeSkillDTO responseDTO = new ResponseUpdateTraineeSkillDTO();
        responseDTO.setSkill(traineeSkill.getSkill().getSkillName().toString());
        responseDTO.setLevel(traineeSkill.getLevel().toString());
        return responseDTO;
    }
}