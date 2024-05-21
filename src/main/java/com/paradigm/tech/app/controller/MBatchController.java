package com.paradigm.tech.app.controller;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.model.entityDTO.response.Enums.ResponseMBatchDTO;
import com.paradigm.tech.app.services.*;
import com.paradigm.tech.app.utlls.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION)
@RequiredArgsConstructor
public class MBatchController {
    private final MBatchService mBatchService;

    @GetMapping(ApiPathConstant.M_BATCH)
    public ResponseEntity<CustomResponseDTO<?>> getAllMBatch(){
        List<ResponseMBatchDTO> mBatches = mBatchService.getAllMBatch();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "All batches fetched", mBatches));
    }

    @GetMapping(ApiPathConstant.M_BATCH + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> getMBatchById(@PathVariable String id){
        ResponseMBatchDTO mBatch = mBatchService.getMBatchById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Batch fetched", mBatch));
    }
}
