package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entityDTO.request.BDClient.RequestBDClientCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.BDClient.RequestBDClientUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDClient.ResponseBDClientCreateDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDClient.ResponseBDClientGetAllAvailDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDClient.ResponseBDClientGetIdDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDClient.ResponseBDClientUpdateDTO;
import org.springframework.security.core.*;

import java.util.*;

public interface BDClientService {
    ResponseBDClientCreateDTO createBDClient(Authentication authentication, RequestBDClientCreateDTO requestBDClientCreateDTO);
    ResponseBDClientGetIdDTO getBDClientById(String id);
    List<ResponseBDClientGetAllAvailDTO> getAllAvailBDClient();
    List<ResponseBDClientGetAllAvailDTO> getAllBDClient();
    ResponseBDClientUpdateDTO updateBDClient(String id, RequestBDClientUpdateDTO requestBDClientUpdateDTO);
    void deleteBDClient(String id);
}
