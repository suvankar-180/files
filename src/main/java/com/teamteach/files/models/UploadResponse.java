package com.teamteach.files.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadResponse {
    private String s3Url;

    public UploadResponse(String url){
        this.s3Url = url;
    }
    
}
