package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entityDTO.response.Enums.ResponseStatusDTO;

import java.util.*;

public interface StatusService {
    List<ResponseStatusDTO> getGradeStatus();

    List<ResponseStatusDTO> getApplicationStatus();

    List<ResponseStatusDTO> getVacancyStatus();

    List<ResponseStatusDTO> getBootcampStatus();

    List<ResponseStatusDTO> getAllStatus();

    ResponseStatusDTO getStatusById(String id);
}
