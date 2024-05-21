package com.paradigm.tech.app.model.entityDTO.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestSocMed {
    private String traineeDetailId;
    private String socMedUrl;
}
