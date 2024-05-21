package com.paradigm.tech.app.model.entityDTO.request.TraineeDetail;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUpdateTraineeLangDTO {
    private String language;
    private String level;
}
