package com.paradigm.tech.app.model.entityDTO.response.Trainer;

import com.paradigm.tech.app.utlls.ERole;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class ResponseTrainerGetAllDTO {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private ERole role;
    private String batch;
}
