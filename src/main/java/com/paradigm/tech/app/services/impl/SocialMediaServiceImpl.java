package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.model.entity.Social_Media;
import com.paradigm.tech.app.model.entity.Trainee_Detail;
import com.paradigm.tech.app.model.entity.User;
import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.*;
import com.paradigm.tech.app.model.entityDTO.response.ResponseShortTraineeDetailDTO;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.*;
import com.paradigm.tech.app.repository.*;
import com.paradigm.tech.app.services.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class SocialMediaServiceImpl implements SocialMediaService {

    private final SocialMediaRepository socialMediaRepository;
    private final TraineeDetailService traineeDetailService;
    private final UserRepository userRepository;
    private final TraineeDetailRepository traineeDetailRepository;

    @Override
    public ResponseCreateSocialMediaDTO saveSocialMedia(RequestCreateSocialMediaDTO request, Authentication auth) {
        try {
            User user = userRepository.findByEmail(auth.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found");
            }

            Social_Media socialMedia = Social_Media.builder()
                    .platformName(request.getPlatformName())
                    .url(request.getUrl())
                    .traineeDetail(traineeDetail)
                    .build();

            socialMediaRepository.save(socialMedia);

            return ResponseCreateSocialMediaDTO.builder()
                    .platformName(socialMedia.getPlatformName())
                    .url(socialMedia.getUrl())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to save social media: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ResponseGetSocialMediaDTO> getAllSocialMedia() {
        try {
            List<Social_Media> socialMediaList = socialMediaRepository.findAll();
            List<ResponseGetSocialMediaDTO> responseSocialMediaList = new ArrayList<>();
            for (Social_Media socialMedia : socialMediaList) {
                ResponseGetSocialMediaDTO responseGetSocialMediaDTO = ResponseGetSocialMediaDTO.builder()
                        .traineeDetail(toShortTraineeDetailDTO(socialMedia.getTraineeDetail()))
                        .platformName(socialMedia.getPlatformName())
                        .url(socialMedia.getUrl())
                        .build();
                responseSocialMediaList.add(responseGetSocialMediaDTO);
            }
            return responseSocialMediaList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch social media: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ResponseGetSocialMediaByTraineeDTO> getSocialMediaByTraineeId(Authentication auth) {
        try {
            User user = userRepository.findByEmail(auth.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found");
            }

            List<Social_Media> socialMediaList = socialMediaRepository.findByTraineeDetail(traineeDetail);
            List<ResponseGetSocialMediaByTraineeDTO> responseSocialMediaList = new ArrayList<>();
            for (Social_Media socialMedia : socialMediaList) {
                ResponseGetSocialMediaByTraineeDTO responseGetSocialMediaByTraineeDTO = ResponseGetSocialMediaByTraineeDTO.builder()
                        .id(socialMedia.getId())
                        .platformName(socialMedia.getPlatformName())
                        .url(socialMedia.getUrl())
                        .build();
                responseSocialMediaList.add(responseGetSocialMediaByTraineeDTO);
            }
            return responseSocialMediaList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch social media: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseGetSocialMediaDTO getSocialMediaById(String id) {
        try {
            Social_Media socialMedia = socialMediaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Social media not found for id: " + id));

            return ResponseGetSocialMediaDTO.builder()
                    .traineeDetail(toShortTraineeDetailDTO(socialMedia.getTraineeDetail()))
                    .platformName(socialMedia.getPlatformName())
                    .url(socialMedia.getUrl())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch social media by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseGetSocialMediaDTO updateSocialMedia(Authentication auth, RequestUpdateSocialMediaDTO request, String id) {
        try {
            User user = userRepository.findByEmail(auth.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
            if (traineeDetail == null) {
                throw new RuntimeException("Trainee detail not found");
            }

            Social_Media socialMedia = socialMediaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Social media not found"));

            if (request.getPlatformName() != null) {
                socialMedia.setPlatformName(request.getPlatformName());
            }
            if (request.getUrl() != null) {
                socialMedia.setUrl(request.getUrl());
            }

            socialMediaRepository.save(socialMedia);

            return ResponseGetSocialMediaDTO.builder()
                    .traineeDetail(toShortTraineeDetailDTO(traineeDetail))
                    .platformName(socialMedia.getPlatformName())
                    .url(socialMedia.getUrl())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to update social media: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteSocialMedia(Authentication auth, String id) {
        try {
            socialMediaRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete social media: " + e.getMessage(), e);
        }
    }

    private ResponseShortTraineeDetailDTO toShortTraineeDetailDTO(Trainee_Detail traineeDetail) {
        return ResponseShortTraineeDetailDTO.builder()
                .firstName(traineeDetail.getFirstName())
                .lastName(traineeDetail.getLastName())
                .batch(traineeDetail.getBatch().getBatchName().getDescription())
                .build();
    }
}
