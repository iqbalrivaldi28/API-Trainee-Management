package com.paradigm.tech.app.model.entityDTO.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseSigninDTO {
    private String email;
    private String token;
}
