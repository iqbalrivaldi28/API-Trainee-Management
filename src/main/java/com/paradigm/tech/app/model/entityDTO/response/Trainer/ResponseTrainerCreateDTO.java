package com.paradigm.tech.app.model.entityDTO.response;

import com.paradigm.tech.app.utlls.*;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class ResponseTrainerCreateDTO {
    private String email;
    private String firstName;
    private ERole role;
    private String batch;
}
