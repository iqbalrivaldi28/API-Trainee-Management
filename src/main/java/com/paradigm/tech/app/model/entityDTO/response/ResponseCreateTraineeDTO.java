package com.paradigm.tech.app.model.entityDTO.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseCreateTraineeDTO {
    private String email;
    private String firstName;

}
