package com.paradigm.tech.app.model.entityDTO.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestSigninDTO {
    private String email;
    private String password;
}
