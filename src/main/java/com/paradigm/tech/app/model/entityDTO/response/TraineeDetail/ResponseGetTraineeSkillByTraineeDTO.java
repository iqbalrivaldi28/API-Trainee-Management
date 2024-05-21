package com.paradigm.tech.app.model.entityDTO.response.TraineeDetail;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class ResponseGetTraineeSkillByTraineeDTO {
    private String id;
    private String skill;
    private String level;
}
