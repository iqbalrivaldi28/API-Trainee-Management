package com.paradigm.tech.app.model.entityDTO.response.BDDetail;

import com.paradigm.tech.app.utlls.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseBDProfileGetDTO {
    private String email;
    private String firstName;
    private String lastName;
    private ERole bdRole;
}
