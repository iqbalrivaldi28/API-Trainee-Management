package com.paradigm.tech.app.model.entityDTO.response.JobApplication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseJobAppGetStatusAndBatchDTO {
    private String batch;
    private List<ResponseJobAppDetailDTO> onBoard;
    private List<ResponseJobAppDetailDTO> failed;

    public ResponseJobAppGetStatusAndBatchDTO(String batch) {
        this.batch = batch;
        this.onBoard = new ArrayList<>();
        this.failed = new ArrayList<>();
    }
}
