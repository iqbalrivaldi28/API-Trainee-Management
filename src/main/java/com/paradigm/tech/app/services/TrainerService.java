package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entityDTO.request.RequestTrainerCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.RequestTrainerUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.ResponseTrainerCreateDTO;
import com.paradigm.tech.app.model.entityDTO.response.ResponseTrainerGetDTO;
import com.paradigm.tech.app.model.entityDTO.response.ResponseTrainerUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.Trainer.ResponseTrainerGetAllDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface TrainerService {
    ResponseTrainerCreateDTO createTrainer(RequestTrainerCreateDTO requestTrainerCreateDTO);

    List<ResponseTrainerGetAllDTO> getAllTrainers();

    List<ResponseTrainerGetDTO> getActiveTrainers();

    ResponseTrainerGetDTO getTrainerProfile(Authentication authentication);

    ResponseTrainerUpdateDTO updateTrainer(RequestTrainerUpdateDTO requestTrainerUpdateDTO);

    void softDeleteTrainer(String id);
}
