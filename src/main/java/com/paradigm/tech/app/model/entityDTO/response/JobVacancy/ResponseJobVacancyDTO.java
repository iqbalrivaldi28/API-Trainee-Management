package com.paradigm.tech.app.model.entityDTO.response.JobVacancy;

import com.paradigm.tech.app.model.entityDTO.response.BDClient.ResponseBDClientUpdateDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseJobVacancyDTO {
    private String position;
    private String description;
    private Integer quota;
    private String status;
    private ResponseBDClientUpdateDTO bdClient;
}
