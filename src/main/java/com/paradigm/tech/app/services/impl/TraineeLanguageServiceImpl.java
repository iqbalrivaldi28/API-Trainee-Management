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

@Service
@RequiredArgsConstructor

public class TraineeLanguageServiceImpl implements TraineeLanguageService {

    private final TraineeDetailRepository traineeDetailRepository;
    private final UserRepository userRepository;
    private final TraineeLanguageRepo traineeLanguageRepo;
    private final MLanguageRepository mLanguageRepository;

    @Override
    public ResponseGetTraineeLangDTO saveLanguage(Authentication authentication, RequestCreateTraineeLangDTO requestCreateTraineeLangDTO) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found for email: " + authentication.getName()));

            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found for user ID: " + user.getId());
            }

            String normalizedLanguage = requestCreateTraineeLangDTO.getLanguage().toUpperCase();
            ELanguage language = ELanguage.valueOf(normalizedLanguage);

            M_Language mLanguage = mLanguageRepository.findByLanguageName(language)
                    .orElseThrow(() -> new RuntimeException("Language with name '" + language + "' not found"));

            String normalizedLevel = requestCreateTraineeLangDTO.getLevel().toUpperCase();
            ELevel level = ELevel.valueOf(normalizedLevel);

            Trainee_Language traineeLanguage = Trainee_Language.builder()
                    .traineeDetail(traineeDetail)
                    .language(mLanguage)
                    .level(level)
                    .build();

            traineeLanguageRepo.save(traineeLanguage);

            return buildResponseGetTraineeLangDTO(traineeLanguage);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid language or level: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to save trainee language: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ResponseGetTraineeLangDTO> getLanguageAll() {
        try {
            List<Trainee_Detail> traineeDetails = traineeDetailRepository.findAll();
            List<ResponseGetTraineeLangDTO> responseDTOs = new ArrayList<>();

            for (Trainee_Detail traineeDetail : traineeDetails) {
                List<Trainee_Language> traineeLanguages = traineeLanguageRepo.findByTraineeDetail(traineeDetail);

                for (Trainee_Language language : traineeLanguages) {
                    ResponseGetTraineeLangDTO dto = buildResponseGetTraineeLangDTO(language);
                    responseDTOs.add(dto);
                }
            }
            return responseDTOs;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch trainee languages: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ResponseGetTraineeLangByTraineeDTO> getLanguageByTraineeId(Authentication authentication) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found");
            }

            List<Trainee_Language> traineeLanguages = traineeLanguageRepo.findByTraineeDetail(traineeDetail);
            List<ResponseGetTraineeLangByTraineeDTO> responseDTOs = new ArrayList<>();
            for (Trainee_Language language : traineeLanguages) {
                ResponseGetTraineeLangByTraineeDTO dto = buildResponseGetTraineeLangByTraineeDTO(language);
                responseDTOs.add(dto);
            }
            return responseDTOs;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch trainee languages: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseGetTraineeLangDTO getLanguageById(String id) {
        try {
            Trainee_Language traineeLanguage = traineeLanguageRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Trainee language not found for ID: " + id));

            return buildResponseGetTraineeLangDTO(traineeLanguage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch trainee language: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseUpdateTraineeLangDTO updateLanguage(Authentication authentication, RequestUpdateTraineeLangDTO requestUpdateTraineeLangDTO, String id) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found for email: " + authentication.getName()));

            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found for user ID: " + user.getId());
            }

            Trainee_Language existingTraineeLanguage = traineeLanguageRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Trainee language not found for ID: " + id));

            if (!existingTraineeLanguage.getTraineeDetail().equals(traineeDetail)) {
                throw new RuntimeException("You're not authorized to update this trainee's language!");
            }

            if (requestUpdateTraineeLangDTO.getLanguage() != null) {
                String normalizedLanguage = requestUpdateTraineeLangDTO.getLanguage().toUpperCase();
                ELanguage language = ELanguage.valueOf(normalizedLanguage);

                M_Language mLanguage = mLanguageRepository.findByLanguageName(language)
                        .orElseThrow(() -> new RuntimeException("Language not found for name: " + language));
                existingTraineeLanguage.setLanguage(mLanguage);
            }

            if (requestUpdateTraineeLangDTO.getLevel() != null) {
                String normalizedLevel = requestUpdateTraineeLangDTO.getLevel().toUpperCase();
                ELevel level = ELevel.valueOf(normalizedLevel);
                existingTraineeLanguage.setLevel(level);
            }

            Trainee_Language updatedTraineeLanguage = traineeLanguageRepo.save(existingTraineeLanguage);

            return buildResponseUpdateTraineeLangDTO(updatedTraineeLanguage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update trainee language: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteLanguage(Authentication authentication, String id) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found");
            }

            traineeLanguageRepo.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseGetTraineeLangDTO buildResponseGetTraineeLangDTO(Trainee_Language traineeLanguage) {
        Trainee_Detail traineeDetail = traineeLanguage.getTraineeDetail();

        ResponseShortTraineeDetailDTO traineeDetailData = ResponseShortTraineeDetailDTO.builder()
                .firstName(traineeDetail.getFirstName())
                .lastName(traineeDetail.getLastName())
                .batch(traineeDetail.getBatch().getBatchName().getDescription())
                .build();

        return ResponseGetTraineeLangDTO.builder()
                .language(traineeLanguage.getLanguage().getLanguageName().toString())
                .level(traineeLanguage.getLevel().toString())
                .traineeDetail(traineeDetailData)
                .build();
    }

    private ResponseGetTraineeLangByTraineeDTO buildResponseGetTraineeLangByTraineeDTO(Trainee_Language traineeLanguage) {
        return ResponseGetTraineeLangByTraineeDTO.builder()
                .id(traineeLanguage.getId())
                .language(traineeLanguage.getLanguage().getLanguageName().toString())
                .level(traineeLanguage.getLevel().toString())
                .build();
    }

    private ResponseUpdateTraineeLangDTO buildResponseUpdateTraineeLangDTO(Trainee_Language traineeLanguage) {
        return ResponseUpdateTraineeLangDTO.builder()
                .language(traineeLanguage.getLanguage().getLanguageName().toString())
                .level(traineeLanguage.getLevel().toString())
                .build();
    }
}