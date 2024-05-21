package com.paradigm.tech.app.model.entityDTO.response.JobApplication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseJobAppDetailDTO {
    private String id;
    private Date applicationDate;
    private String status;
    private String firstName;
    private String lastName;
    private String batch;
}
