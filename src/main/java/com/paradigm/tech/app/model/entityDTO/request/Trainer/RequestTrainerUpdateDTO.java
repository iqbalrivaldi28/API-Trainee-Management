package com.paradigm.tech.app.model.entityDTO.request;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class RequestTrainerUpdateDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String batch;
}
