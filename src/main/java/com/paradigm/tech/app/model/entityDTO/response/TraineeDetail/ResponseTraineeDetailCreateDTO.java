package com.paradigm.tech.app.model.entityDTO.response.TraineeDetail;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseTraineeDetailCreateDTO {
    private String email;
    private String firstName;
    private String batch;
    private String status;
}
