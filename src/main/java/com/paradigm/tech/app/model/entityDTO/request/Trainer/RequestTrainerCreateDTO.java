package com.paradigm.tech.app.model.entityDTO.request;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class RequestTrainerCreateDTO {
    private String email;
    private String password;
    private String firstName;
    private String batch;
}
