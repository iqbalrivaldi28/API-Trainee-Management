package com.paradigm.tech.app.model.entityDTO.request.TraineeDetail;

import com.paradigm.tech.app.model.entity.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCreateTraineeLangDTO {
    private String language;
    private String level;
 }
