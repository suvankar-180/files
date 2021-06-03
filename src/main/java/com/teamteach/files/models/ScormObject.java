package com.teamteach.files.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScormObject {
    private String contentFolder;    
    private String contentName;
}