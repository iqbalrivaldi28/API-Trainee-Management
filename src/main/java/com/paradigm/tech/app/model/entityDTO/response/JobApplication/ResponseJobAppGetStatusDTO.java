package com.paradigm.tech.app.model.entityDTO.response.JobApplication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseJobAppGetStatusDTO {
    private List<ResponseJobAppDetailDTO> onBoard;
    private List<ResponseJobAppDetailDTO> failed;
}
