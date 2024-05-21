package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.model.entity.BD_Detail;
import com.paradigm.tech.app.model.entity.Role;
import com.paradigm.tech.app.model.entity.User;
import com.paradigm.tech.app.model.entityDTO.request.BDDetail.RequestBDCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.BDDetail.RequestBDProfileDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDDetail.ResponseBDProfileGetAllDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDDetail.ResponseBDProfileGetDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDDetail.ResponseBDProfileUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDDetail.ResponseBDCreateDTO;
import com.paradigm.tech.app.repository.BDDetailRepository;
import com.paradigm.tech.app.repository.UserRepository;
import com.paradigm.tech.app.services.BDDetailService;
import com.paradigm.tech.app.utlls.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BDDetailServiceImpl implements BDDetailService {
    private final UserRepository userRepository;
    private final BDDetailRepository bdDetailRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<ResponseBDProfileGetAllDTO> getAllAvailBDProfile() {
        try {
            List<BD_Detail> bdDetails = bdDetailRepository.findAllByIsDeletedIsFalse();
            if (bdDetails != null) {
                List<ResponseBDProfileGetAllDTO> bdProfileGetAllDTOList = new ArrayList<>();
                for (BD_Detail bdDetail : bdDetails) {
                    User user = userRepository.findById(bdDetail.getUser().getId()).get();
                    ResponseBDProfileGetAllDTO bdProfileDTO = ResponseBDProfileGetAllDTO.builder()
                            .email(user.getEmail())
                            .firstName(bdDetail.getFirstName())
                            .lastName(bdDetail.getLastName())
                            .build();

                    bdProfileGetAllDTOList.add(bdProfileDTO);
                }
                return bdProfileGetAllDTOList;
            } else {
                throw new RuntimeException("BD Detail not found!");
            }

        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public List<ResponseBDProfileGetAllDTO> getAllBDProfile() {
        try {
            List<BD_Detail> bdDetails = bdDetailRepository.findAll();
            if (bdDetails != null) {
                List<ResponseBDProfileGetAllDTO> bdProfileGetAllDTOList = new ArrayList<>();
                for (BD_Detail bdDetail : bdDetails) {
                    User user = userRepository.findById(bdDetail.getUser().getId()).get();
                    ResponseBDProfileGetAllDTO bdProfileDTO = ResponseBDProfileGetAllDTO.builder()
                            .email(user.getEmail())
                            .firstName(bdDetail.getFirstName())
                            .lastName(bdDetail.getLastName())
                            .build();

                    bdProfileGetAllDTOList.add(bdProfileDTO);
                }
                return bdProfileGetAllDTOList;
            } else {
                throw new RuntimeException("BD Detail not found!");
            }

        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseBDProfileGetDTO getBDProfile(Authentication authentication) {
        try {
            User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found!"));
            BD_Detail bdDetail = bdDetailRepository.findByUserId(user.getId());

            if (bdDetail != null) {
                ResponseBDProfileGetDTO responseBDProfileGetDTO = new ResponseBDProfileGetDTO();
                responseBDProfileGetDTO.setEmail(user.getEmail());
                responseBDProfileGetDTO.setFirstName(bdDetail.getFirstName());
                responseBDProfileGetDTO.setLastName(bdDetail.getLastName());
                responseBDProfileGetDTO.setBdRole(user.getRole().getRole());

                return responseBDProfileGetDTO;
            } else {
                throw new RuntimeException("BD Detail not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseBDProfileUpdateDTO updateBDProfile(RequestBDProfileDTO requestBDProfileDTO) {
        try {
            User user = userRepository.findByEmail(requestBDProfileDTO.getEmail()).orElse(null);
            System.out.println(user);

            if (user != null) {
                BD_Detail bdDetail = bdDetailRepository.findByUserId(user.getId());
                if (requestBDProfileDTO.getFirstName() == null) {
                    bdDetail.setFirstName(bdDetail.getFirstName());
                } else {
                    bdDetail.setFirstName(requestBDProfileDTO.getFirstName());
                }
                if (requestBDProfileDTO.getLastName() == null) {
                    bdDetail.setLastName(bdDetail.getLastName());
                } else {
                    bdDetail.setLastName(requestBDProfileDTO.getLastName());
                }

                bdDetailRepository.save(bdDetail);

                ResponseBDProfileUpdateDTO responseBDProfileUpdateDTO = new ResponseBDProfileUpdateDTO();
                responseBDProfileUpdateDTO.setFirstName(bdDetail.getFirstName());
                responseBDProfileUpdateDTO.setLastName(bdDetail.getLastName());

                return responseBDProfileUpdateDTO;
            } else {
                throw new RuntimeException("User not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseBDCreateDTO createBD(RequestBDCreateDTO requestBDCreateDTO) {
        try {
            Instant timestamp = Instant.now();
            User user = User.builder()
                    .email(requestBDCreateDTO.getEmail())
                    .password(passwordEncoder.encode(requestBDCreateDTO.getPassword()))
                    .role(Role.builder()
                            .role(ERole.BUSINESS_DEVELOPMENT)
                            .build())
                    .createdAt(timestamp)
                    .build();
            userRepository.save(user);

            BD_Detail bdDetail = BD_Detail.builder()
                    .firstName(requestBDCreateDTO.getFirstName())
                    .user(user)
                    .isDeleted(Boolean.FALSE)
                    .build();

            bdDetailRepository.save(bdDetail);

            return ResponseBDCreateDTO.builder()
                    .email(user.getEmail())
                    .bdRole(user.getRole().getRole())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public void deleteBDProfile(String id) {
        try {
            List<BD_Detail> bdDetails = bdDetailRepository.findAllByIsDeletedIsFalse();
            for (BD_Detail bdDetail : bdDetails) {
                if (bdDetail.getId().equals(id)) {
                    bdDetailRepository.deleteById(id);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }
}
