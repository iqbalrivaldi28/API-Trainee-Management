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
public class TraineeLanguageController {

    private final TraineeLanguageService traineeLanguageService;

    @PostMapping(ApiPathConstant.TRAINEE_LANGUAGE + "/create")
    public ResponseEntity<CustomResponseDTO<ResponseGetTraineeLangDTO>> saveTraineeLanguage(
            @RequestBody RequestCreateTraineeLangDTO requestCreateTraineeLangDTO,
            Authentication authentication
    ){
        ResponseGetTraineeLangDTO savedTraineeLanguage = traineeLanguageService.saveLanguage(authentication, requestCreateTraineeLangDTO);
        return new ResponseEntity<>(new CustomResponseDTO<>(true, HttpStatus.CREATED.value(), "Language created successfully!", savedTraineeLanguage), HttpStatus.CREATED);
    }

    @GetMapping(ApiPathConstant.TRAINEE_LANGUAGE)
    public ResponseEntity<CustomResponseDTO<List<ResponseGetTraineeLangByTraineeDTO>>> getLanguageByTraineeId(Authentication authentication) {
        List<ResponseGetTraineeLangByTraineeDTO> languages = traineeLanguageService.getLanguageByTraineeId(authentication);
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Successfully retrieved languages!", languages));
    }

    @GetMapping(ApiPathConstant.TRAINEE_LANGUAGE + "/all")
    public ResponseEntity<CustomResponseDTO<List<ResponseGetTraineeLangDTO>>> getAllTraineeLanguages(Authentication authentication) {
        List<ResponseGetTraineeLangDTO> languages = traineeLanguageService.getLanguageAll();
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Successfully retrieved all languages!", languages));
    }

    @GetMapping(ApiPathConstant.TRAINEE_LANGUAGE + "/{id}")
    public ResponseEntity<CustomResponseDTO<ResponseGetTraineeLangDTO>> getTraineeLanguageById(@PathVariable String id) {
        ResponseGetTraineeLangDTO language = traineeLanguageService.getLanguageById(id);
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Successfully retrieved language by ID!", language));
    }

    @PutMapping(ApiPathConstant.TRAINEE_LANGUAGE + "/{id}")
    public ResponseEntity<CustomResponseDTO<ResponseUpdateTraineeLangDTO>> updateTraineeLanguage(
            Authentication authentication,
            @RequestBody RequestUpdateTraineeLangDTO requestUpdateTraineeLangDTO,
            @PathVariable String id
    ) {
        ResponseUpdateTraineeLangDTO updatedTraineeLanguage = traineeLanguageService.updateLanguage(authentication, requestUpdateTraineeLangDTO, id);
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Language updated successfully!", updatedTraineeLanguage));
    }

    @DeleteMapping(ApiPathConstant.TRAINEE_LANGUAGE + "/{id}")
    public ResponseEntity<CustomResponseDTO<Void>> deleteTraineeLanguage(Authentication authentication, @PathVariable String id) {
        traineeLanguageService.deleteLanguage(authentication, id);
        return ResponseEntity.ok(new CustomResponseDTO<>(true, HttpStatus.OK.value(), "Success delete language!", null));
    }
}
