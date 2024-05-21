package com.paradigm.tech.app.model.entityDTO.response.TraineeDetail;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseTraineeEduGetByTraineeDTO {
    private String id;
    private String institutionName;
    private String fieldOfStudy;
    private String graduationYear;
    private String cGPA;
}
