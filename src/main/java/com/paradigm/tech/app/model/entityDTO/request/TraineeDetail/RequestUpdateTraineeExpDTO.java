package com.paradigm.tech.app.model.entityDTO.request.TraineeDetail;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class RequestUpdateTraineeExpDTO {
    private String companyName;
    private String position;
    private String description;
    private String startDate;
    private String endDate;
}
