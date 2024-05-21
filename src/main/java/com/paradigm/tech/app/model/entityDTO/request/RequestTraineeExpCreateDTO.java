package com.paradigm.tech.app.model.entityDTO.request;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestTraineeExpCreateDTO {
    private String companyName;
    private String position;
    private String description;
    private Date startDate;
    private Date endDate;
}
