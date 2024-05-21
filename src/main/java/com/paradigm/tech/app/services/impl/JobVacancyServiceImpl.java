package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.model.entityDTO.request.JobVacancy.RequestJobVacancyCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.JobVacancy.RequestJobVacancyUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDClient.ResponseBDClientUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.JobVacancy.ResponseJobVacancyDTO;
import com.paradigm.tech.app.model.entityDTO.response.JobVacancy.ResponseJobVacancyGetAllDTO;
import com.paradigm.tech.app.repository.*;
import com.paradigm.tech.app.services.*;
import com.paradigm.tech.app.utlls.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JobVacancyServiceImpl implements JobVacancyService {
    private final JobVacancyRepository jobVacancyRepository;
    private final BDClientRepository bdClientRepository;
    private final StatusRepository statusRepository;

    @Override
    public ResponseJobVacancyDTO createJobVacancy(RequestJobVacancyCreateDTO requestJobVacancyCreateDTO) {
        try {
            Instant timestamp = Instant.now();
            Optional<BD_Client> bdClient = bdClientRepository.findById(requestJobVacancyCreateDTO.getBdClientId());
            Status status = statusRepository.findByStatus(EStatus.OPEN).get();
            Job_Vacancy jobVacancy = Job_Vacancy.builder()
                    .createdAt(timestamp)
                    .position(requestJobVacancyCreateDTO.getPosition())
                    .description(requestJobVacancyCreateDTO.getDescription())
                    .quota(requestJobVacancyCreateDTO.getQuota())
                    .bdClient(bdClient.get())
                    .status(status)
                    .build();
            jobVacancyRepository.save(jobVacancy);

            ResponseBDClientUpdateDTO bdClientData = ResponseBDClientUpdateDTO.builder()
                    .clientName(bdClient.get().getClientName())
                    .clientAddress(bdClient.get().getClientAddress())
                    .clientEmail(bdClient.get().getClientEmail())
                    .build();

            return ResponseJobVacancyDTO.builder()
                    .position(jobVacancy.getPosition())
                    .description(jobVacancy.getDescription())
                    .quota(jobVacancy.getQuota())
                    .status(jobVacancy.getStatus().getStatus().getDescription())
                    .bdClient(bdClientData)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseJobVacancyDTO getJobVacancyById(String id) {
        try {
            Job_Vacancy jobVacancy = jobVacancyRepository.findById(id).get();
            BD_Client bdClient = jobVacancy.getBdClient();

            ResponseBDClientUpdateDTO bdClientData = ResponseBDClientUpdateDTO.builder()
                    .clientName(bdClient.getClientName())
                    .clientAddress(bdClient.getClientAddress())
                    .clientEmail(bdClient.getClientEmail())
                    .build();

            return ResponseJobVacancyDTO.builder()
                    .position(jobVacancy.getPosition())
                    .description(jobVacancy.getDescription())
                    .quota(jobVacancy.getQuota())
                    .status(jobVacancy.getStatus().getStatus().getDescription())
                    .bdClient(bdClientData)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public List<ResponseJobVacancyGetAllDTO> getAllOpenJobVacancy() {
        try {
            List<ResponseJobVacancyGetAllDTO> result = new ArrayList<>();
            List<Job_Vacancy> jobVacancies = jobVacancyRepository.findAll();
            for (Job_Vacancy jobVacancy : jobVacancies) {
                if (jobVacancy.getStatus().getStatus().toString().equalsIgnoreCase("open")) {
                    BD_Client bdClient = jobVacancy.getBdClient();

                    ResponseBDClientUpdateDTO bdClientData = ResponseBDClientUpdateDTO.builder()
                            .clientName(bdClient.getClientName())
                            .clientAddress(bdClient.getClientAddress())
                            .clientEmail(bdClient.getClientEmail())
                            .build();

                    ResponseJobVacancyGetAllDTO responseJobVacancyDTO = ResponseJobVacancyGetAllDTO.builder()
                            .id(jobVacancy.getId())
                            .position(jobVacancy.getPosition())
                            .description(jobVacancy.getDescription())
                            .quota(jobVacancy.getQuota())
                            .status(jobVacancy.getStatus().getStatus().getDescription())
                            .bdClient(bdClientData)
                            .build();

                    result.add(responseJobVacancyDTO);
                }
//            result.add(jobVacancy);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public List<ResponseJobVacancyGetAllDTO> getAllJobVacancy() {
        try {
            List<ResponseJobVacancyGetAllDTO> result = new ArrayList<>();
            List<Job_Vacancy> jobVacancies = jobVacancyRepository.findAll();
            for (Job_Vacancy jobVacancy : jobVacancies) {
                BD_Client bdClient = jobVacancy.getBdClient();

                ResponseBDClientUpdateDTO bdClientData = ResponseBDClientUpdateDTO.builder()
                        .clientName(bdClient.getClientName())
                        .clientAddress(bdClient.getClientAddress())
                        .clientEmail(bdClient.getClientEmail())
                        .build();

                ResponseJobVacancyGetAllDTO responseJobVacancyDTO = ResponseJobVacancyGetAllDTO.builder()
                        .id(jobVacancy.getId())
                        .position(jobVacancy.getPosition())
                        .description(jobVacancy.getDescription())
                        .quota(jobVacancy.getQuota())
                        .status(jobVacancy.getStatus().getStatus().getDescription())
                        .bdClient(bdClientData)
                        .build();

                result.add(responseJobVacancyDTO);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseJobVacancyDTO updateJobVacancy(String id, RequestJobVacancyUpdateDTO requestJobVacancyUpdateDTO) {
        try {
            Job_Vacancy updateVacancy = jobVacancyRepository.findById(id).orElse(null);
//            System.out.println(jobVacancy.getStatus());
//            System.out.println(jobVacancy.getStatus().getStatus());
//            System.out.println(updateVacancy.getStatus());

            if (updateVacancy != null) {
                updateVacancy.setCreatedAt(updateVacancy.getCreatedAt());
                if (requestJobVacancyUpdateDTO.getPosition() == null) {
                    updateVacancy.setPosition(updateVacancy.getPosition());
                } else {
                    updateVacancy.setPosition(requestJobVacancyUpdateDTO.getPosition());
                }
                if (requestJobVacancyUpdateDTO.getDescription() == null) {
                    updateVacancy.setDescription(updateVacancy.getDescription());
                } else {
                    updateVacancy.setDescription(requestJobVacancyUpdateDTO.getDescription());
                }
                if (requestJobVacancyUpdateDTO.getQuota() == null) {
                    updateVacancy.setQuota(updateVacancy.getQuota());
                } else {
                    updateVacancy.setQuota(requestJobVacancyUpdateDTO.getQuota());
                }
                if (requestJobVacancyUpdateDTO.getStatus() == null) {
                    updateVacancy.setStatus(updateVacancy.getStatus());
                } else {
                    System.out.println(requestJobVacancyUpdateDTO.getStatus().toUpperCase());
                    if (EStatus.valueOf(requestJobVacancyUpdateDTO.getStatus().toUpperCase()).equals(EStatus.CLOSED)) {
                        Status status = statusRepository.findByStatus(EStatus.valueOf(requestJobVacancyUpdateDTO.getStatus().toUpperCase())).get();
                        updateVacancy.setStatus(status);
                    } else if (EStatus.valueOf(requestJobVacancyUpdateDTO.getStatus().toUpperCase()).equals(EStatus.OPEN)){
                        Status status = statusRepository.findByStatus(EStatus.valueOf(requestJobVacancyUpdateDTO.getStatus().toUpperCase())).get();
                        updateVacancy.setStatus(status);
                    } else {
                        throw new RuntimeException("Status not applicable!");
                    }
                }
                jobVacancyRepository.save(updateVacancy);

                BD_Client bdClient = updateVacancy.getBdClient();

                ResponseBDClientUpdateDTO bdClientData = ResponseBDClientUpdateDTO.builder()
                        .clientName(bdClient.getClientName())
                        .clientAddress(bdClient.getClientAddress())
                        .clientEmail(bdClient.getClientEmail())
                        .build();

                return ResponseJobVacancyDTO.builder()
                        .position(updateVacancy.getPosition())
                        .description(updateVacancy.getDescription())
                        .quota(updateVacancy.getQuota())
                        .status(updateVacancy.getStatus().getStatus().getDescription())
                        .bdClient(bdClientData)
                        .build();
            } else {
                throw new RuntimeException("Job Vacancy not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

//    @Override
//    public JobVacancyDTO getJobVacancyApplication(String id) {
//        return null;
//    }
//
//    @Override
//    public List<JobVacancyDTO> getAllJobVacancyApplication() {
//        return List.of();
//    }
}
