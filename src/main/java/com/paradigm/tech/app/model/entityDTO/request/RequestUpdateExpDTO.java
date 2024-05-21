package com.paradigm.tech.app.model.entityDTO.request;

import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUpdateExpDTO {
    private String companyName;
    private String position;
    private String description;
    private Date startDate;
    private Date endDate;
}
