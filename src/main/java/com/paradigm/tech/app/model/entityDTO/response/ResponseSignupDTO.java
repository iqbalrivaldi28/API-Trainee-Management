package com.paradigm.tech.app.model.entityDTO.response;

import com.paradigm.tech.app.utlls.*;
import lombok.*;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ResponseSignupDTO {
    private String email;
    private ERole role;
}
