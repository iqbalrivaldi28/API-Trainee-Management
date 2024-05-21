package com.paradigm.tech.app.model.entityDTO.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestSignupDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
