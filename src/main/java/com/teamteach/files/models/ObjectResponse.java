package com.teamteach.files.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ObjectResponse {
    private boolean success;
    private String message;
    private Object object;
}
