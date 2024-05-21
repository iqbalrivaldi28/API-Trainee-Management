package com.paradigm.tech.app.model.entityDTO.request.TraineeDetail;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class RequestCreateTraineeSkillDTO {
    private String skill;
    private String level;
}
