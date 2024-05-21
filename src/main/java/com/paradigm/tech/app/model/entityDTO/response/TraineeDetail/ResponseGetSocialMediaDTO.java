package com.paradigm.tech.app.model.entityDTO.response.TraineeDetail;

import com.paradigm.tech.app.model.entityDTO.response.*;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class ResponseGetSocialMediaDTO {
    private ResponseShortTraineeDetailDTO traineeDetail;
    private String platformName;
    private String url;
}
