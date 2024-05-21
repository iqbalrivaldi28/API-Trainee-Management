package com.paradigm.tech.app.model.entityDTO.request.TraineeDetail;

import lombok.*;


import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestTraineeDetailUpdateDTO {
    private String email;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String placeOfBirth;
    private String phone;
}
