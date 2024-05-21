package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.model.entityDTO.request.BDClient.RequestBDClientCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.BDClient.RequestBDClientUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDClient.ResponseBDClientCreateDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDClient.ResponseBDClientGetAllAvailDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDClient.ResponseBDClientGetIdDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDClient.ResponseBDClientUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDDetail.ResponseBDProfileUpdateDTO;
import com.paradigm.tech.app.repository.*;
import com.paradigm.tech.app.services.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BDClientServiceImpl implements BDClientService {
    private final BDClientRepository bdClientRepository;
    private final BDDetailRepository bdDetailRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseBDClientCreateDTO createBDClient(Authentication authentication, RequestBDClientCreateDTO requestBDClientCreateDTO) {
        try {
            Optional<User> user = userRepository.findByEmail(authentication.getName());
            if (user.isPresent()) {
                BD_Detail bdDetail =  bdDetailRepository.findByUserId(user.get().getId());
                BD_Client bdClient = BD_Client.builder()
                        .clientName(requestBDClientCreateDTO.getClientName())
                        .clientAddress(requestBDClientCreateDTO.getClientAddress())
                        .clientEmail(requestBDClientCreateDTO.getClientEmail())
                        .isDeleted(Boolean.FALSE)
                        .bdDetail(bdDetail)
                        .build();
                bdClientRepository.save(bdClient);

                ResponseBDProfileUpdateDTO bdDetailData = ResponseBDProfileUpdateDTO.builder()
                        .firstName(bdDetail.getFirstName())
                        .lastName(bdDetail.getLastName())
                        .build();

                return ResponseBDClientCreateDTO.builder()
                        .clientName(bdClient.getClientName())
                        .clientAddress(bdClient.getClientAddress())
                        .clientEmail(bdClient.getClientEmail())
                        .bdDetail(bdDetailData)
                        .build();
            } else {
                throw new RuntimeException("User not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseBDClientGetIdDTO getBDClientById(String id) {
        try {
            Optional<BD_Client> bdClient = bdClientRepository.findById(id);
            if (bdClient.isPresent()) {
                String bdDetailId = bdClient.get().getBdDetail().getId();
                BD_Detail bdDetail = bdDetailRepository.findById(bdDetailId).get();

                ResponseBDProfileUpdateDTO bdDetailData = ResponseBDProfileUpdateDTO.builder()
                        .firstName(bdDetail.getFirstName())
                        .lastName(bdDetail.getLastName())
                        .build();

                return ResponseBDClientGetIdDTO.builder()
                        .clientName(bdClient.get().getClientName())
                        .clientAddress(bdClient.get().getClientAddress())
                        .clientEmail(bdClient.get().getClientEmail())
                        .bdDetail(bdDetailData)
                        .build();
            } else {
                throw new RuntimeException("BD Client not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public List<ResponseBDClientGetAllAvailDTO> getAllAvailBDClient() {
        try {
            List<BD_Client> bdClients = bdClientRepository.findAllByIsDeletedIsFalse();
            if (bdClients != null) {
                List<ResponseBDClientGetAllAvailDTO> bdClientDTOList = new ArrayList<>();
                for (BD_Client bdClient : bdClients) {
                    String bdDetailId = bdClient.getBdDetail().getId();
                    BD_Detail bdDetail = bdDetailRepository.findById(bdDetailId).get();

                    ResponseBDProfileUpdateDTO bdDetailData = ResponseBDProfileUpdateDTO.builder()
                            .firstName(bdDetail.getFirstName())
                            .lastName(bdDetail.getLastName())
                            .build();

                    ResponseBDClientGetAllAvailDTO bdClientDTO = ResponseBDClientGetAllAvailDTO.builder()
                            .bdClientId(bdClient.getId())
                            .clientName(bdClient.getClientName())
                            .clientAddress(bdClient.getClientAddress())
                            .clientEmail(bdClient.getClientEmail())
                            .bdDetail(bdDetailData)
                            .build();
                    bdClientDTOList.add(bdClientDTO);
                }
                return bdClientDTOList;
            } else {
                throw new RuntimeException("BD Clients not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public List<ResponseBDClientGetAllAvailDTO> getAllBDClient() {
        try {
            List<BD_Client> bdClients = bdClientRepository.findAll();
            if (bdClients != null) {
                List<ResponseBDClientGetAllAvailDTO> bdClientDTOList = new ArrayList<>();
                for (BD_Client bdClient : bdClients) {
                    String bdDetailId = bdClient.getBdDetail().getId();
                    BD_Detail bdDetail = bdDetailRepository.findById(bdDetailId).get();

                    ResponseBDProfileUpdateDTO bdDetailData = ResponseBDProfileUpdateDTO.builder()
                            .firstName(bdDetail.getFirstName())
                            .lastName(bdDetail.getLastName())
                            .build();

                    ResponseBDClientGetAllAvailDTO bdClientDTO = ResponseBDClientGetAllAvailDTO.builder()
                            .bdClientId(bdClient.getId())
                            .clientName(bdClient.getClientName())
                            .clientAddress(bdClient.getClientAddress())
                            .clientEmail(bdClient.getClientEmail())
                            .bdDetail(bdDetailData)
                            .build();
                    bdClientDTOList.add(bdClientDTO);
                }
                return bdClientDTOList;
            } else {
                throw new RuntimeException("BD Clients not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseBDClientUpdateDTO updateBDClient(String id, RequestBDClientUpdateDTO requestBDClientUpdateDTO) {
        try {
            Optional<BD_Client> updateClient = bdClientRepository.findById(id);

            if (updateClient.isPresent()) {
                if (requestBDClientUpdateDTO.getClientName() == null) {
                    updateClient.get().setClientName(updateClient.get().getClientName());
                } else {
                    updateClient.get().setClientName(requestBDClientUpdateDTO.getClientName());
                }
                if (requestBDClientUpdateDTO.getClientAddress() == null) {
                    updateClient.get().setClientAddress(updateClient.get().getClientAddress());
                } else {
                    updateClient.get().setClientAddress(requestBDClientUpdateDTO.getClientAddress());
                }
                if (requestBDClientUpdateDTO.getClientEmail() == null) {
                    updateClient.get().setClientEmail(updateClient.get().getClientEmail());
                } else {
                    updateClient.get().setClientEmail(requestBDClientUpdateDTO.getClientEmail());
                }

                bdClientRepository.save(updateClient.get());

                return ResponseBDClientUpdateDTO.builder()
                        .clientName(updateClient.get().getClientName())
                        .clientAddress(updateClient.get().getClientAddress())
                        .clientEmail(updateClient.get().getClientEmail())
                        .build();
            } else {
                throw new RuntimeException("BD Client not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public void deleteBDClient(String id) {
        try {
            List<BD_Client> bdClients = bdClientRepository.findAllByIsDeletedIsFalse();
            for (BD_Client bdClient : bdClients) {
                if (bdClient.getId().equals(id)) {
                    bdClientRepository.deleteById(id);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }
}
