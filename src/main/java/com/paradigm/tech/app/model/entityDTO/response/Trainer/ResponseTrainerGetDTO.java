package com.paradigm.tech.app.model.entityDTO.response;

import com.paradigm.tech.app.utlls.*;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class ResponseTrainerGetDTO {
    private String email;
    private String firstName;
    private String lastName;
    private ERole role;
    private String batch;
}
