package com.paradigm.tech.app.model.entityDTO.response.BDDetail;

import com.paradigm.tech.app.utlls.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseBDCreateDTO {
    private String email;
    private ERole bdRole;
}
