package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entityDTO.response.Enums.ResponseMBatchDTO;

import java.util.*;

public interface MBatchService {

    List<ResponseMBatchDTO> getAllMBatch();

    ResponseMBatchDTO getMBatchById(String id);
}
