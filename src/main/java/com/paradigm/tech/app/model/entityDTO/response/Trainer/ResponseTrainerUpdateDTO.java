package com.paradigm.tech.app.model.entityDTO.response;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class ResponseTrainerUpdateDTO {
    private String firstName;
    private String lastName;
    private String batch;
}
