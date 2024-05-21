package com.paradigm.tech.app.model.entityDTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseShortTraineeDetailDTO {
    private String firstName;
    private String lastName;
    private String batch;
}
