package com.brian.newfriday.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
public class LogController {
    private static final String LOG_PATH = "/app/logs/newfriday.log";

    @GetMapping("/logs")
    public ResponseEntity<Resource> downloadLogs() throws IOException {
        File file = new File(LOG_PATH);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=newfriday.log")
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }
}
