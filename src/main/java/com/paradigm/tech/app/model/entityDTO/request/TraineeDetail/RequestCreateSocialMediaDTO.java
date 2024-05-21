package com.paradigm.tech.app.model.entityDTO.request.TraineeDetail;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class RequestCreateSocialMediaDTO {
    private String platformName;
    private String url;
}
