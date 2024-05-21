package com.paradigm.tech.app.model.entityDTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseShortJobVacancyDTO {
    private String id;
    private String clientName;
    private String position;
    private String description;
}
