package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.*;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.*;
import org.springframework.security.core.*;

import java.util.*;

public interface SocialMediaService {
    ResponseCreateSocialMediaDTO saveSocialMedia(RequestCreateSocialMediaDTO requestCreateSocialMediaDTO, Authentication authentication);

    List<ResponseGetSocialMediaDTO> getAllSocialMedia();

    List<ResponseGetSocialMediaByTraineeDTO> getSocialMediaByTraineeId(Authentication authentication);

    ResponseGetSocialMediaDTO getSocialMediaById(String id);

    ResponseGetSocialMediaDTO updateSocialMedia(Authentication authentication, RequestUpdateSocialMediaDTO requestUpdateSocialMediaDTO, String id);

    void deleteSocialMedia(Authentication authentication, String id);
}
