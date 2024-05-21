package com.paradigm.tech.app.model.entityDTO.request.TraineeDetail;

import lombok.*;


import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class RequestTraineeDetailCreateDTO {
    private String email;
    private String password;
    private String firstName;
    private Date joinedAt;
    private String batch;
}
