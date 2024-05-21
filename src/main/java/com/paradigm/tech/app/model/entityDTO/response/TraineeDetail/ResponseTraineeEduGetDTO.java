package com.paradigm.tech.app.model.entityDTO.response.TraineeDetail;

import com.paradigm.tech.app.model.entityDTO.response.ResponseShortTraineeDetailDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseTraineeEduGetDTO {
    private String institutionName;
    private String fieldOfStudy;
    private String graduationYear;
    private String cGPA;
    private ResponseShortTraineeDetailDTO traineeDetail;
}
