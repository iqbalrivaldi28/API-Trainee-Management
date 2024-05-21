package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.model.entity.M_Batch;
import com.paradigm.tech.app.model.entityDTO.response.Enums.ResponseMBatchDTO;
import com.paradigm.tech.app.repository.*;
import com.paradigm.tech.app.services.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MBatchServiceImpl implements MBatchService {

    private final MBatchRepository mBatchRepository;

    @Override
    public List<ResponseMBatchDTO> getAllMBatch() {
        try {
            List<M_Batch> mBatches = mBatchRepository.findAll();
            List<ResponseMBatchDTO> mBatchDTOList = new ArrayList<>();
            for (M_Batch mBatch : mBatches) {
                ResponseMBatchDTO mBatchDTO = ResponseMBatchDTO.builder()
                        .id(mBatch.getId())
                        .batchName(mBatch.getBatchName().getDescription())
                        .build();

                mBatchDTOList.add(mBatchDTO);
            }
            return mBatchDTOList;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseMBatchDTO getMBatchById(String id) {
        try {
            M_Batch mBatch = mBatchRepository.findById(id).get();
            return ResponseMBatchDTO.builder()
                    .id(mBatch.getId())
                    .batchName(mBatch.getBatchName().getDescription())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }
}