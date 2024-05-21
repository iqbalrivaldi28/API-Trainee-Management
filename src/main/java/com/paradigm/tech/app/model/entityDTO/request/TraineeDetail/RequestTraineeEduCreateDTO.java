package com.paradigm.tech.app.model.entityDTO.request.TraineeDetail;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestTraineeEduCreateDTO {
    private String institutionName;
    private String fieldOfStudy;
    private String graduationYear;
    private String gpaScore;
}
