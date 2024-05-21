package com.paradigm.tech.app.controller;

import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.*;
import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.*;
import com.paradigm.tech.app.services.*;
import com.paradigm.tech.app.utlls.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION)
@RequiredArgsConstructor
public class SocialMediaController {
    private final SocialMediaService socialMediaService;

    @PostMapping(ApiPathConstant.SOCIAL_MEDIA + "/create")
    public  ResponseEntity<CustomResponseDTO<ResponseCreateSocialMediaDTO>> saveSocialMedia(@RequestBody RequestCreateSocialMediaDTO requestCreateSocialMediaDTO, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Social media created successfully!", socialMediaService.saveSocialMedia(requestCreateSocialMediaDTO, authentication)));
    }

    @GetMapping(ApiPathConstant.SOCIAL_MEDIA + "/all")
    public ResponseEntity<CustomResponseDTO<List<ResponseGetSocialMediaDTO>>> getAllListSocialMedia() {
        List<ResponseGetSocialMediaDTO> socialMediaList = socialMediaService.getAllSocialMedia();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Successfully retrieved all social media!", socialMediaList));
    }

    @GetMapping(ApiPathConstant.SOCIAL_MEDIA)
    public ResponseEntity<CustomResponseDTO<List<ResponseGetSocialMediaByTraineeDTO>>> getSocialMediaListByTraineeId(Authentication authentication) {
        List<ResponseGetSocialMediaByTraineeDTO> socialMediaList = socialMediaService.getSocialMediaByTraineeId(authentication);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Successfully retrieved social media by trainee!", socialMediaList));
    }


    @GetMapping(ApiPathConstant.SOCIAL_MEDIA + "/{id}")
    public ResponseEntity<CustomResponseDTO<ResponseGetSocialMediaDTO>> getSocialMediaById(@PathVariable String id) {
        ResponseGetSocialMediaDTO socialMedia = socialMediaService.getSocialMediaById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Successfully retrieved social media by ID!", socialMedia));
    }

    @PutMapping(ApiPathConstant.SOCIAL_MEDIA + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> updateSocialMedia(Authentication authentication, @PathVariable String id, @RequestBody RequestUpdateSocialMediaDTO requestUpdateSocialMediaDTO
    ) {
        ResponseGetSocialMediaDTO updatedSocialMedia = socialMediaService.updateSocialMedia(authentication, requestUpdateSocialMediaDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Social media updated successfully!", updatedSocialMedia));
    }

    @DeleteMapping(ApiPathConstant.SOCIAL_MEDIA + "/{id}")
    public ResponseEntity<CustomResponseDTO<Void>> deleteSocialMedia(@PathVariable String id, Authentication authentication) {
        socialMediaService.deleteSocialMedia(authentication, id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Social media deleted successfully!", null));
    }
}
