package com.paradigm.tech.app.model.entityDTO.response.TraineeDetail;

import com.paradigm.tech.app.model.entityDTO.response.ResponseShortTraineeDetailDTO;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class ResponseGetTraineeExpDTO {
    private ResponseShortTraineeDetailDTO traineeDetail;
    private String companyName;
    private String position;
    private String description;
    private String startDate;
    private String endDate;
}
