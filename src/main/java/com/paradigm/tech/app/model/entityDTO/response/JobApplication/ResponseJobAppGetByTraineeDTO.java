package com.paradigm.tech.app.model.entityDTO.response.JobApplication;

import com.paradigm.tech.app.model.entityDTO.response.ResponseShortJobVacancyDTO;
import com.paradigm.tech.app.model.entityDTO.response.ResponseShortTraineeDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseJobAppGetByTraineeDTO {
    private ResponseShortJobVacancyDTO jobVacancy;
    private String status;
}
