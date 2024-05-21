package com.paradigm.tech.app.controller;

import com.paradigm.tech.app.model.entityDTO.request.BDDetail.RequestBDCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.BDDetail.RequestBDProfileDTO;
import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.model.entityDTO.response.BDDetail.ResponseBDCreateDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDDetail.ResponseBDProfileGetAllDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDDetail.ResponseBDProfileGetDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDDetail.ResponseBDProfileUpdateDTO;
import com.paradigm.tech.app.services.*;
import com.paradigm.tech.app.utlls.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION)
public class BDDetailController {
    private final BDDetailService bdDetailService;

    @GetMapping(ApiPathConstant.BD_DETAIL + "/profile")
    public ResponseEntity<CustomResponseDTO<?>> getBDProfile(Authentication authentication) {
        ResponseBDProfileGetDTO responseBDProfileGetDTO = bdDetailService.getBDProfile(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "BD profile fetched", responseBDProfileGetDTO));
    }

    @GetMapping(ApiPathConstant.BD_DETAIL + "/all")
    public ResponseEntity<CustomResponseDTO<?>> getAllBDProfile() {
        List<ResponseBDProfileGetAllDTO> responseBDProfileGetAllDTO = bdDetailService.getAllBDProfile();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "All BD details fetched", responseBDProfileGetAllDTO));
    }

    @GetMapping(ApiPathConstant.BD_DETAIL)
    public ResponseEntity<CustomResponseDTO<?>> getAllAvailBDProfile() {
        List<ResponseBDProfileGetAllDTO> responseBDProfileGetAllDTO = bdDetailService.getAllAvailBDProfile();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "All Available BD details fetched", responseBDProfileGetAllDTO));
    }

    @PutMapping(ApiPathConstant.BD_DETAIL)
    public ResponseEntity<CustomResponseDTO<?>> updateBDProfile(@RequestBody RequestBDProfileDTO requestBDProfileDTO) {
        ResponseBDProfileUpdateDTO responseBDProfileUpdateDTO = bdDetailService.updateBDProfile(requestBDProfileDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "BD profile successfully updated", responseBDProfileUpdateDTO));
    }

    @PostMapping(ApiPathConstant.BD_DETAIL + "/create")
    public ResponseEntity<CustomResponseDTO<?>> createBD(@RequestBody RequestBDCreateDTO requestBDCreateDTO) {
        ResponseBDCreateDTO result = bdDetailService.createBD(requestBDCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponseDTO<>(true, 200, "success created BD", result));
    }

    @DeleteMapping(ApiPathConstant.BD_DETAIL + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> deleteBDProfile(@PathVariable String id) {
        bdDetailService.deleteBDProfile(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "BD Profile deleted", null));
    }
}
