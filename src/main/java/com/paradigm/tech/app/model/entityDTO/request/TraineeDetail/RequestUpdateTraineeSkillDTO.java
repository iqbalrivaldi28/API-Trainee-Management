package com.paradigm.tech.app.model.entityDTO.request.TraineeDetail;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUpdateTraineeSkillDTO {
    private String skill;
    private String level;
}
