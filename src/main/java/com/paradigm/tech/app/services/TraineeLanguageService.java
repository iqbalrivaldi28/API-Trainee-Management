package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entityDTO.request.TraineeDetail.*;
import com.paradigm.tech.app.model.entityDTO.response.TraineeDetail.*;
import org.springframework.security.core.*;

import java.util.*;

public interface TraineeLanguageService {
    ResponseGetTraineeLangDTO saveLanguage(Authentication authentication, RequestCreateTraineeLangDTO requestCreateTraineeLangDTO);

    List<ResponseGetTraineeLangDTO> getLanguageAll();

    List<ResponseGetTraineeLangByTraineeDTO> getLanguageByTraineeId(Authentication authentication);

    ResponseGetTraineeLangDTO getLanguageById(String id);

    ResponseUpdateTraineeLangDTO updateLanguage(Authentication authentication, RequestUpdateTraineeLangDTO requestUpdateTraineeLangDTO, String id);

    void deleteLanguage(Authentication authentication, String id);

}
