package com.paradigm.tech.app.model.entityDTO.response.TraineeDetail;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class ResponseCreateSocialMediaDTO {
    private String platformName;
    private String url;
}
