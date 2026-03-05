package com.github.cidarosa.ms.produto.exceptions.dto;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorDto extends CustomErrorDto {

    private List<FieldMessageDto> errors = new ArrayList<>();

    public ValidationErrorDto(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public void addError(String fieldName, String message){
        errors.removeIf(x -> x.getFieldName().equals(fieldName));

        errors.add(new FieldMessageDto(fieldName, message));
    }
}
