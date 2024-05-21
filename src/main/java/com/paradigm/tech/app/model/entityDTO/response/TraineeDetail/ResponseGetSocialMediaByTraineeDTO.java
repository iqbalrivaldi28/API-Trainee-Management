package com.paradigm.tech.app.model.entityDTO.response.TraineeDetail;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class ResponseGetSocialMediaByTraineeDTO {
    private String id;
    private String platformName;
    private String url;
}
