package com.project.billingSoftware.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileDownloadController {

    // Endpoint for downloading PDF
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
        	Path path = Paths.get("src/main/resources/static/pdf").resolve(fileName);

            Resource resource = new UrlResource(path.toUri());

            // Check if the file exists and is readable
            if (resource.exists() || resource.isReadable()) {
                // Set the content type and content disposition for the response
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                        .body(resource);
            } else {
                // If file not found, throw exception
                throw new FileNotFoundException("File not found: " + fileName);
            }
        } catch (Exception e) {
            // Handle errors (file not found, etc.)
            throw new RuntimeException("Error while serving PDF file", e);
        }
    }
}
