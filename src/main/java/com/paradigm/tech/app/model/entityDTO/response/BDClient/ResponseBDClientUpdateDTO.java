package com.paradigm.tech.app.model.entityDTO.response.BDClient;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseBDClientUpdateDTO {
    private String clientName;
    private String clientAddress;
    private String clientEmail;
}
