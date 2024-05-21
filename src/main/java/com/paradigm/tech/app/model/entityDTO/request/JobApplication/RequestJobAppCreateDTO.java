package com.paradigm.tech.app.model.entityDTO.request.JobApplication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestJobAppCreateDTO {
    private String jobVacancyId;
}
