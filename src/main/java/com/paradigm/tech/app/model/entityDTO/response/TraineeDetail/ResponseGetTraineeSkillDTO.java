package com.paradigm.tech.app.model.entityDTO.response.TraineeDetail;

import com.paradigm.tech.app.model.entityDTO.response.*;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class ResponseGetTraineeSkillDTO {
    private ResponseShortTraineeDetailDTO traineeDetail;
    private String skill;
    private String level;
}
