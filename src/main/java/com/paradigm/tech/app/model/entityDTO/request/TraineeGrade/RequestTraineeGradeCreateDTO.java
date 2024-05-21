package com.paradigm.tech.app.model.entityDTO.request.TraineeGrade;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestTraineeGradeCreateDTO {
    private String traineeDetailId;
    private String status;
    private Float grade;
}
