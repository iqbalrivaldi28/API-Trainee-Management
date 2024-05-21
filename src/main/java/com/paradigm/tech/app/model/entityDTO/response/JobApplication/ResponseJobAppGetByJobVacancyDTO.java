package com.paradigm.tech.app.model.entityDTO.response.JobApplication;

import com.paradigm.tech.app.model.entityDTO.response.ResponseShortJobVacancyDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseJobAppGetByJobVacancyDTO {
    private String id;
    private String clientName;
    private Integer quota;
    private String position;
    private String description;
    private List<ResponseJobAppDetailDTO> jobApp;
}
