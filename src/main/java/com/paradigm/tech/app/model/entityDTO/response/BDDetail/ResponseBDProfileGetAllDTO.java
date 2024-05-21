package com.paradigm.tech.app.model.entityDTO.response.BDDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseBDProfileGetAllDTO {
    private String email;
    private String firstName;
    private String lastName;
}
