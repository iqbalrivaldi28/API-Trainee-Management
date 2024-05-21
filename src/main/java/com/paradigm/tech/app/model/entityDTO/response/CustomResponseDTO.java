package com.paradigm.tech.app.model.entityDTO.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponseDTO<T> {
        private boolean status;
        private int code;
        private String message;
        private T data;
}
