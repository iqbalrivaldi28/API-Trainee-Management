package com.paradigm.tech.app.model.entityDTO.response.TraineeDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseTraineeDetailUpdateDTO {
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String placeOfBirth;
    private String phone;
    private Date joinedAt;
    private String batch;
    private String status;
}
