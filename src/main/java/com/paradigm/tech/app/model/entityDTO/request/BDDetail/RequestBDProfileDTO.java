package com.paradigm.tech.app.model.entityDTO.request.BDDetail;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestBDProfileDTO {
    private String email;
    private String firstName;
    private String lastName;
}
