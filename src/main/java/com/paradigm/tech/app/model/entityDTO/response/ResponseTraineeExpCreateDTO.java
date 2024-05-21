package com.paradigm.tech.app.model.entityDTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseTraineeExpCreateDTO {
    private String companyName;
    private String position;
    private String description;
    private Date startDate;
    private Date endDate;
    private ResponseShortTraineeDetailDTO traineeDetail;
}
