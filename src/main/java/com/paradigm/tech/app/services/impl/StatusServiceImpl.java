package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.model.entityDTO.response.Enums.ResponseStatusDTO;
import com.paradigm.tech.app.repository.*;
import com.paradigm.tech.app.services.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    @Override
    public List<ResponseStatusDTO> getGradeStatus() {
        return convertToResponseStatusList(statusRepository.findGradeStatus());
    }

    @Override
    public List<ResponseStatusDTO> getApplicationStatus() {
        return convertToResponseStatusList(statusRepository.findApplicationStatus());
    }

    @Override
    public List<ResponseStatusDTO> getVacancyStatus() {
        return convertToResponseStatusList(statusRepository.findVacancyStatus());
    }

    @Override
    public List<ResponseStatusDTO> getBootcampStatus() {
        return convertToResponseStatusList(statusRepository.findBootcampStatus());
    }

    @Override
    public List<ResponseStatusDTO> getAllStatus() {
        return convertToResponseStatusList(statusRepository.findAll());
    }

    @Override
    public ResponseStatusDTO getStatusById(String id) {
        Optional<Status> statusOptional = statusRepository.findById(id);
        return statusOptional.map(status -> ResponseStatusDTO.builder()
                .id(status.getId())
                .status(status.getStatus().toString())
                .build()).orElse(null);
    }

    private List<ResponseStatusDTO> convertToResponseStatusList(List<Status> statuses) {
        List<ResponseStatusDTO> responseStatusDTOList = new ArrayList<>();
        for (Status status : statuses) {
            ResponseStatusDTO responseStatusDTO = ResponseStatusDTO.builder()
                    .id(status.getId())
                    .status(status.getStatus().toString())
                    .build();
            responseStatusDTOList.add(responseStatusDTO);
        }
        return responseStatusDTOList;
    }
}
