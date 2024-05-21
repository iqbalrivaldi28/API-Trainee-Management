package com.paradigm.tech.app.model.entityDTO.response.TraineeDetail;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class ResponseGetTraineeExpByTraineeDTO {
    private String id;
    private String companyName;
    private String position;
    private String description;
    private String startDate;
    private String endDate;
}
