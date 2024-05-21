package com.paradigm.tech.app.controller;

import com.paradigm.tech.app.model.entityDTO.request.BDClient.RequestBDClientCreateDTO;
import com.paradigm.tech.app.model.entityDTO.request.BDClient.RequestBDClientUpdateDTO;
import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.model.entityDTO.response.BDClient.ResponseBDClientCreateDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDClient.ResponseBDClientGetAllAvailDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDClient.ResponseBDClientGetIdDTO;
import com.paradigm.tech.app.model.entityDTO.response.BDClient.ResponseBDClientUpdateDTO;
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
public class BDClientController {
    private final BDClientService bdClientService;

    @PostMapping(ApiPathConstant.BD_CLIENT + "/create")
    public ResponseEntity<CustomResponseDTO<?>> createBDClient(Authentication authentication, @RequestBody RequestBDClientCreateDTO requestBDClientCreateDTO) {
        ResponseBDClientCreateDTO result = bdClientService.createBDClient(authentication, requestBDClientCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponseDTO<>(true, 200, "BD client successfully created", result));
    }

    @GetMapping(ApiPathConstant.BD_CLIENT)
    public ResponseEntity<CustomResponseDTO<?>> getAllAvailBDClient() {
        List<ResponseBDClientGetAllAvailDTO> bdClients = bdClientService.getAllAvailBDClient();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "All available BD clients fetched", bdClients));
    }

    @GetMapping(ApiPathConstant.BD_CLIENT + "/all")
    public ResponseEntity<CustomResponseDTO<?>> getAllBDClient() {
        List<ResponseBDClientGetAllAvailDTO> bdClients = bdClientService.getAllBDClient();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "All available BD clients fetched", bdClients));
    }

    @GetMapping(ApiPathConstant.BD_CLIENT + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> getBDClientById(@PathVariable String id) {
        ResponseBDClientGetIdDTO bdClient = bdClientService.getBDClientById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "BD client successfully fetched", bdClient));
    }

    @PutMapping(ApiPathConstant.BD_CLIENT + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> updateBDClient(@PathVariable String id, @RequestBody RequestBDClientUpdateDTO requestBDClientUpdateDTO) {
        ResponseBDClientUpdateDTO updateClient = bdClientService.updateBDClient(id, requestBDClientUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "BD client successfully updated", updateClient));
    }

    @DeleteMapping(ApiPathConstant.BD_CLIENT + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> deleteBDClient(@PathVariable String id) {
        bdClientService.deleteBDClient(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "BD Client deleted", null));
    }
}
