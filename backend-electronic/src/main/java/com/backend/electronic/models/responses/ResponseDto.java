package com.backend.electronic.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// TODO: ¿ELIMINAR ESTE DTO?
public class ResponseDto {
    private String code;
    private String message;
    private Object data;
}
