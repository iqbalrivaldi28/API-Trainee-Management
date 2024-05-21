package com.paradigm.tech.app.model.entityDTO.request.BDClient;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestBDClientUpdateDTO {
    private String clientName;
    private String clientAddress;
    private String clientEmail;
}
