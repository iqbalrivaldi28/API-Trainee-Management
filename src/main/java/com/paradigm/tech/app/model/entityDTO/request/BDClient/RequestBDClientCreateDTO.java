package com.paradigm.tech.app.model.entityDTO.request.BDClient;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestBDClientCreateDTO {
//    private String bdDetailId;
    private String clientName;
    private String clientAddress;
    private String clientEmail;
}
