package com.paradigm.tech.app.model.entityDTO.response.TraineeGrade;

import com.paradigm.tech.app.model.entityDTO.response.ResponseShortTraineeDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseTraineeGradeDTO {
    private String id;
    private String status;
    private Float grade;
}
