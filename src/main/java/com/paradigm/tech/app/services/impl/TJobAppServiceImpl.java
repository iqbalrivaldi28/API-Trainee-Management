package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.model.entityDTO.request.JobApplication.RequestJobAppCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.JobApplication.RequestJobAppUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.JobApplication.*;
import com.paradigm.tech.app.model.entityDTO.response.ResponseShortJobVacancyDTO;
import com.paradigm.tech.app.model.entityDTO.response.ResponseShortTraineeDetailDTO;
import com.paradigm.tech.app.repository.*;
import com.paradigm.tech.app.services.*;
import com.paradigm.tech.app.utlls.EBatch;
import com.paradigm.tech.app.utlls.EStatus;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.*;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TJobAppServiceImpl implements TJobAppService{
    private final TJobApplicationRepo jobApplicationRepo;
    private final UserRepository userRepository;
    private final TraineeDetailRepository traineeDetailRepository;
    private final JobVacancyRepository jobVacancyRepository;
    private final StatusRepository statusRepository;
    private final BDDetailRepository bdDetailRepository;
    private final BDClientRepository bdClientRepository;

    @Override
    public ResponseJobAppCreateDTO createJobApp(Authentication authentication, RequestJobAppCreateDTO requestJobAppCreateDTO) {
        try {
            Job_Vacancy jobVacancy = jobVacancyRepository.findById(requestJobAppCreateDTO.getJobVacancyId()).get();
            if (jobVacancy.getQuota() < 1) {
                throw new RuntimeException("Quota is full, unable to apply!");
            }

            User user = userRepository.findByEmail(authentication.getName()).orElse(null);
            if (user != null) {
                Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
                Status status = statusRepository.findByStatus(EStatus.APPLIED).get();

                Instant timestamp = Instant.now();
                Date applicationDate = Date.from(timestamp);

                T_Job_Application jobApplication = T_Job_Application.builder()
                        .applicationDate(applicationDate)
                        .jobVacancy(jobVacancy)
                        .traineeDetail(traineeDetail)
                        .status(status)
                        .build();
                jobApplicationRepo.save(jobApplication);

                jobVacancy.setQuota(jobVacancy.getQuota()-1);
                jobVacancyRepository.save(jobVacancy);

                ResponseShortTraineeDetailDTO traineeDetailDTO = ResponseShortTraineeDetailDTO.builder()
                        .firstName(jobApplication.getTraineeDetail().getFirstName())
                        .lastName(jobApplication.getTraineeDetail().getLastName())
                        .batch(jobApplication.getTraineeDetail().getBatch().getBatchName().getDescription())
                        .build();

                ResponseShortJobVacancyDTO jobVacancyDTO = ResponseShortJobVacancyDTO.builder()
                        .clientName(jobApplication.getJobVacancy().getBdClient().getClientName())
                        .position(jobApplication.getJobVacancy().getPosition())
                        .description(jobApplication.getJobVacancy().getDescription())
                        .build();

                return ResponseJobAppCreateDTO.builder()
                        .traineeDetail(traineeDetailDTO)
                        .jobVacancy(jobVacancyDTO)
                        .status(jobApplication.getStatus().getStatus().getDescription())
                        .build();
            } else {
                throw new RuntimeException("User not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public List<ResponseJobAppGetDTO> getAllJobApp() {
        try {
            List<T_Job_Application> jobApplications = jobApplicationRepo.findAll();
            List<ResponseJobAppGetDTO> jobApplicationGetDTOList = new ArrayList<>();
            for (T_Job_Application jobApplication : jobApplications) {
                ResponseShortTraineeDetailDTO traineeDetailDTO = ResponseShortTraineeDetailDTO.builder()
                        .firstName(jobApplication.getTraineeDetail().getFirstName())
                        .lastName(jobApplication.getTraineeDetail().getLastName())
                        .batch(jobApplication.getTraineeDetail().getBatch().getBatchName().getDescription())
                        .build();

                ResponseShortJobVacancyDTO jobVacancyDTO = ResponseShortJobVacancyDTO.builder()
                        .clientName(jobApplication.getJobVacancy().getBdClient().getClientName())
                        .position(jobApplication.getJobVacancy().getPosition())
                        .description(jobApplication.getJobVacancy().getDescription())
                        .build();

                ResponseJobAppGetDTO jobApplicationGetDTO = ResponseJobAppGetDTO.builder()
                        .id(jobApplication.getId())
                        .traineeDetail(traineeDetailDTO)
                        .jobVacancy(jobVacancyDTO)
                        .status(jobApplication.getStatus().getStatus().getDescription())
                        .build();
                jobApplicationGetDTOList.add(jobApplicationGetDTO);
            }
            return jobApplicationGetDTOList;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public List<ResponseJobAppGetByTraineeDTO> getJobAppByTraineeId(Authentication authentication) {
        try {
            User user = userRepository.findByEmail(authentication.getName()).orElse(null);
            if (user != null) {
                Trainee_Detail traineeDetail = traineeDetailRepository.findByUserId(user.getId());
                List<T_Job_Application> jobApplications = jobApplicationRepo.findByTraineeDetailId(traineeDetail.getId());
                List<ResponseJobAppGetByTraineeDTO> jobAppGetByTraineeDTOList = new ArrayList<>();

                for (T_Job_Application jobApplication : jobApplications) {
                    ResponseShortJobVacancyDTO jobVacancyDTO = ResponseShortJobVacancyDTO.builder()
                            .id(jobApplication.getJobVacancy().getId())
                            .clientName(jobApplication.getJobVacancy().getBdClient().getClientName())
                            .position(jobApplication.getJobVacancy().getPosition())
                            .description(jobApplication.getJobVacancy().getDescription())
                            .build();

                    ResponseJobAppGetByTraineeDTO jobAppGetByTraineeDTO = ResponseJobAppGetByTraineeDTO.builder()
                            .jobVacancy(jobVacancyDTO)
                            .status(jobApplication.getStatus().getStatus().getDescription())
                            .build();

                    jobAppGetByTraineeDTOList.add(jobAppGetByTraineeDTO);
                }
                return jobAppGetByTraineeDTOList;
            } else {
                throw new RuntimeException("User not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public List<ResponseJobAppGetByJobVacancyDTO> getJobAppByJobVacancy(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElse(null);
        if (user !=  null) {
            BD_Detail bdDetail = bdDetailRepository.findByUserId(user.getId());
            List<BD_Client> bdClients = bdClientRepository.findByBdDetail(bdDetail);
            List<Job_Vacancy> jobVacanciesList = new ArrayList<>();
            for (BD_Client bdClient : bdClients) {
                List<Job_Vacancy> jobVacancies = jobVacancyRepository.findByBdClient(bdClient);
                jobVacanciesList.addAll(jobVacancies);
            }

            List<ResponseJobAppGetByJobVacancyDTO> jobAppGetByJobVacancyDTOList = new ArrayList<>();
            for (Job_Vacancy jobVacancy : jobVacanciesList) {
                List<T_Job_Application> jobApplications = jobApplicationRepo.findByJobVacancy(jobVacancy);
                List<ResponseJobAppDetailDTO> jobAppDetailDTOList = new ArrayList<>();
                for (T_Job_Application jobApplication : jobApplications) {
                    ResponseJobAppDetailDTO jobAppDetailDTO = ResponseJobAppDetailDTO.builder()
                            .id(jobApplication.getId())
                            .applicationDate(jobApplication.getApplicationDate())
                            .status(jobApplication.getStatus().getStatus().getDescription())
                            .firstName(jobApplication.getTraineeDetail().getFirstName())
                            .lastName(jobApplication.getTraineeDetail().getLastName())
                            .batch(jobApplication.getTraineeDetail().getBatch().getBatchName().getDescription())
                            .build();
                    jobAppDetailDTOList.add(jobAppDetailDTO);
                }

                ResponseJobAppGetByJobVacancyDTO jobVacancyDTO = ResponseJobAppGetByJobVacancyDTO.builder()
                        .id(jobVacancy.getId())
                        .clientName(jobVacancy.getBdClient().getClientName())
                        .quota(jobVacancy.getQuota())
                        .position(jobVacancy.getPosition())
                        .description(jobVacancy.getDescription())
                        .jobApp(jobAppDetailDTOList)
                        .build();
                jobAppGetByJobVacancyDTOList.add(jobVacancyDTO);
            }
            return jobAppGetByJobVacancyDTOList;
        } else {
            throw new RuntimeException("User not found!");
        }
    }

    @Override
    public ResponseJobAppGetStatusDTO getAllJobAppByStatus(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElse(null);
        if (user !=  null) {
            BD_Detail bdDetail = bdDetailRepository.findByUserId(user.getId());
            List<BD_Client> bdClients = bdClientRepository.findByBdDetail(bdDetail);
            List<Job_Vacancy> jobVacanciesList = new ArrayList<>();
            for (BD_Client bdClient : bdClients) {
                List<Job_Vacancy> jobVacancies = jobVacancyRepository.findByBdClient(bdClient);
                jobVacanciesList.addAll(jobVacancies);
            }
            List<ResponseJobAppDetailDTO> onBoard = new ArrayList<>();
            List<ResponseJobAppDetailDTO> failed = new ArrayList<>();
            for (Job_Vacancy jobVacancy : jobVacanciesList) {
                List<T_Job_Application> jobApplications = jobApplicationRepo.findByJobVacancy(jobVacancy);
                for (T_Job_Application jobApplication : jobApplications) {
                    if (jobApplication.getStatus().getStatus().equals(EStatus.ON_BOARD)) {
                        ResponseJobAppDetailDTO onBoardApp = ResponseJobAppDetailDTO.builder()
                                .id(jobApplication.getId())
                                .applicationDate(jobApplication.getApplicationDate())
                                .status(jobApplication.getStatus().getStatus().getDescription())
                                .firstName(jobApplication.getTraineeDetail().getFirstName())
                                .lastName(jobApplication.getTraineeDetail().getLastName())
                                .batch(jobApplication.getTraineeDetail().getBatch().getBatchName().getDescription())
                                .build();
                        onBoard.add(onBoardApp);
                    } else if (jobApplication.getStatus().getStatus().equals(EStatus.FAILED)) {
                        ResponseJobAppDetailDTO failedApp = ResponseJobAppDetailDTO.builder()
                                .id(jobApplication.getId())
                                .applicationDate(jobApplication.getApplicationDate())
                                .status(jobApplication.getStatus().getStatus().getDescription())
                                .firstName(jobApplication.getTraineeDetail().getFirstName())
                                .lastName(jobApplication.getTraineeDetail().getLastName())
                                .batch(jobApplication.getTraineeDetail().getBatch().getBatchName().getDescription())
                                .build();
                        failed.add(failedApp);
                    }
                }
            }
            return ResponseJobAppGetStatusDTO.builder()
                    .onBoard(onBoard)
                    .failed(failed)
                    .build();
        } else {
            throw new RuntimeException("User not found!");
        }
    }

    @Override
    public List<ResponseJobAppGetStatusAndBatchDTO> getJobAppByStatusAndBatch(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElse(null);
        if (user !=  null) {
            BD_Detail bdDetail = bdDetailRepository.findByUserId(user.getId());
            List<BD_Client> bdClients = bdClientRepository.findByBdDetail(bdDetail);
            List<Job_Vacancy> jobVacanciesList = new ArrayList<>();
            for (BD_Client bdClient : bdClients) {
                List<Job_Vacancy> jobVacancies = jobVacancyRepository.findByBdClient(bdClient);
                jobVacanciesList.addAll(jobVacancies);
            }
            List<ResponseJobAppGetStatusAndBatchDTO> jobAppGetStatusDTOList = new ArrayList<>();
            for (EBatch eBatch : EBatch.values()) {
                jobAppGetStatusDTOList.add(new ResponseJobAppGetStatusAndBatchDTO(eBatch.getDescription()));
            }
            for (Job_Vacancy jobVacancy : jobVacanciesList) {
                List<T_Job_Application> jobApplications = jobApplicationRepo.findByJobVacancy(jobVacancy);
                for (T_Job_Application jobApplication : jobApplications) {
                    for (ResponseJobAppGetStatusAndBatchDTO jobAppStatusAndBatch : jobAppGetStatusDTOList) {
                        if (jobAppStatusAndBatch.getBatch().equals(jobApplication.getTraineeDetail().getBatch().getBatchName().getDescription())) {
                            System.out.println(jobApplication.getTraineeDetail().getBatch().getBatchName().getDescription());
                            System.out.println(jobAppStatusAndBatch);
                            if (jobApplication.getStatus().getStatus().equals(EStatus.ON_BOARD)) {
                                ResponseJobAppDetailDTO onBoardApp = ResponseJobAppDetailDTO.builder()
                                        .id(jobApplication.getId())
                                        .applicationDate(jobApplication.getApplicationDate())
                                        .status(jobApplication.getStatus().getStatus().getDescription())
                                        .firstName(jobApplication.getTraineeDetail().getFirstName())
                                        .lastName(jobApplication.getTraineeDetail().getLastName())
                                        .batch(jobApplication.getTraineeDetail().getBatch().getBatchName().getDescription())
                                        .build();
                                jobAppStatusAndBatch.getOnBoard().add(onBoardApp);
                            } else if (jobApplication.getStatus().getStatus().equals(EStatus.FAILED)) {
                                ResponseJobAppDetailDTO failedApp = ResponseJobAppDetailDTO.builder()
                                        .id(jobApplication.getId())
                                        .applicationDate(jobApplication.getApplicationDate())
                                        .status(jobApplication.getStatus().getStatus().getDescription())
                                        .firstName(jobApplication.getTraineeDetail().getFirstName())
                                        .lastName(jobApplication.getTraineeDetail().getLastName())
                                        .batch(jobApplication.getTraineeDetail().getBatch().getBatchName().getDescription())
                                        .build();
                                jobAppStatusAndBatch.getFailed().add(failedApp);
                            }
                        }
                    }
                }
            }
            return jobAppGetStatusDTOList;
        } else {
            throw new RuntimeException("User not found!");
        }
    }

    @Override
    public ResponseJobAppGetDTO getJobAppById(String id) {
        T_Job_Application jobApplication = jobApplicationRepo.findById(id).get();

        ResponseShortTraineeDetailDTO traineeDetailDTO = ResponseShortTraineeDetailDTO.builder()
                .firstName(jobApplication.getTraineeDetail().getFirstName())
                .lastName(jobApplication.getTraineeDetail().getLastName())
                .batch(jobApplication.getTraineeDetail().getBatch().getBatchName().getDescription())
                .build();

        ResponseShortJobVacancyDTO jobVacancyDTO = ResponseShortJobVacancyDTO.builder()
                .clientName(jobApplication.getJobVacancy().getBdClient().getClientName())
                .position(jobApplication.getJobVacancy().getPosition())
                .description(jobApplication.getJobVacancy().getDescription())
                .build();

        return ResponseJobAppGetDTO.builder()
                .id(jobApplication.getId())
                .traineeDetail(traineeDetailDTO)
                .jobVacancy(jobVacancyDTO)
                .status(jobApplication.getStatus().getStatus().getDescription())
                .build();
    }

    @Override
    public ResponseJobAppUpdateDTO updateJobApp(String id, RequestJobAppUpdateDTO requestJobAppUpdateDTO) {
        T_Job_Application jobApplication = jobApplicationRepo.findById(id).get();

        String normalizeStatus = requestJobAppUpdateDTO.getStatus().toUpperCase().replace(" ", "_");
        EStatus eStatus = EStatus.valueOf(normalizeStatus);
        Status status = statusRepository.findByStatus(eStatus).get();

        jobApplication.setStatus(status);
        jobApplicationRepo.save(jobApplication);

        ResponseShortTraineeDetailDTO traineeDetailDTO = ResponseShortTraineeDetailDTO.builder()
                .firstName(jobApplication.getTraineeDetail().getFirstName())
                .lastName(jobApplication.getTraineeDetail().getLastName())
                .batch(jobApplication.getTraineeDetail().getBatch().getBatchName().getDescription())
                .build();

        ResponseShortJobVacancyDTO jobVacancyDTO = ResponseShortJobVacancyDTO.builder()
                .clientName(jobApplication.getJobVacancy().getBdClient().getClientName())
                .position(jobApplication.getJobVacancy().getPosition())
                .description(jobApplication.getJobVacancy().getDescription())
                .build();

        return ResponseJobAppUpdateDTO.builder()
                .traineeDetail(traineeDetailDTO)
                .jobVacancy(jobVacancyDTO)
                .status(jobApplication.getStatus().getStatus().getDescription())
                .build();
    }
}
