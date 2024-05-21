package com.paradigm.tech.app.model.entityDTO.response.TraineeDetail;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUpdateTraineeSkillDTO {
    private String skill;
    private String level;
}
