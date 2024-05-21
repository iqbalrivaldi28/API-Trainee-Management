package com.paradigm.tech.app.model.entityDTO.request.TraineeDetail;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestTraineeEduUpdateDTO {
    private String institutionName;
    private String fieldOfStudy;
    private String graduationYear;
    private String gpaScore;
}
