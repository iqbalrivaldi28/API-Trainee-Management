package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entityDTO.request.BDDetail.RequestBDCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.BDDetail.RequestBDProfileDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDDetail.ResponseBDProfileGetAllDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDDetail.ResponseBDProfileGetDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDDetail.ResponseBDProfileUpdateDTO;
import org.springframework.security.core.Authentication;
import com.paradigm.tech.app.model.entityDTO.response.BDDetail.ResponseBDCreateDTO;

import java.util.List;

public interface BDDetailService {
    List<ResponseBDProfileGetAllDTO> getAllAvailBDProfile();
    List<ResponseBDProfileGetAllDTO> getAllBDProfile();
    ResponseBDProfileGetDTO getBDProfile(Authentication authentication);
    ResponseBDProfileUpdateDTO updateBDProfile(RequestBDProfileDTO requestBDProfileDTO);
    ResponseBDCreateDTO createBD(RequestBDCreateDTO requestBDCreateDTO);
    void deleteBDProfile(String id);
}
