package com.paradigm.tech.app.model.entityDTO.response.TraineeGrade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseTraineeGradeGetStatusDTO {
    private List<ResponseTraineeGradeDTO> backend;
    private List<ResponseTraineeGradeDTO> frontend;
    private List<ResponseTraineeGradeDTO> mobile;
}
