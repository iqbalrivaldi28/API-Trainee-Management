package com.paradigm.tech.app.model.entityDTO.request.BDDetail;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestBDCreateDTO {
    private String email;
    private String password;
    private String firstName;
}
