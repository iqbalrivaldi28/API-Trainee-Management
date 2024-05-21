package com.paradigm.tech.app.model.entityDTO.response.TraineeGrade;

import lombok.*;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseTraineeGradeBatchAndTraineeDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String batch;
    private List<ResponseTraineeGradeDTO> traineeGrade;
}
