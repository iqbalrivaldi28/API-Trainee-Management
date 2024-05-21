package com.paradigm.tech.app.model.entityDTO.response.JobApplication;

import com.paradigm.tech.app.model.entityDTO.response.ResponseShortJobVacancyDTO;
import com.paradigm.tech.app.model.entityDTO.response.ResponseShortTraineeDetailDTO;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseJobAppGetDTO {
    private String id;
    private ResponseShortTraineeDetailDTO traineeDetail;
    private ResponseShortJobVacancyDTO jobVacancy;
    private String status;
}
