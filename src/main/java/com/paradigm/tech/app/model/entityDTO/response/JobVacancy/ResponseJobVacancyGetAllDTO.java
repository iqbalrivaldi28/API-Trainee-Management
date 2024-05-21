package com.paradigm.tech.app.model.entityDTO.response.JobVacancy;

import com.paradigm.tech.app.model.entityDTO.response.BDClient.ResponseBDClientUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseJobVacancyGetAllDTO {
    private String id;
    private String position;
    private String description;
    private Integer quota;
    private String status;
    private ResponseBDClientUpdateDTO bdClient;
}
