package com.teamteach.files.services;

import java.io.File;

import com.teamteach.files.models.ScormObject;

import org.springframework.stereotype.Component;

@Component
public class ScormService {
    public ScormObject analyseContent(String contentFolder, File content) {
        String contentName = content.getName();

        // TODO : get more out of 'content' package and stuff it in ScormObject

        return (ScormObject.builder()
                        .contentFolder(contentFolder)
                        .contentName(contentName)
                        .build());
    }
}
