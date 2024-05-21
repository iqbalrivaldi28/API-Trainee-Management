package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entityDTO.response.Enums.ResponseMLanguageDTO;

import java.util.*;

public interface MLanguageService {

//    MLanguage saveMLanguage(String language);

    List<ResponseMLanguageDTO> getAllMLanguage();

    ResponseMLanguageDTO getMLanguageById(String id);
}
