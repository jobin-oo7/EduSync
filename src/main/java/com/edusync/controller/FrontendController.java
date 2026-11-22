package com.edusync.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Controller
public class FrontendController {

    @Value("${edusync.frontend.html-path:}")
    private String configuredHtmlPath;

    @GetMapping(value = {"/", "/college-platform-connected.html"}, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> serveFrontend() {
        Path frontendPath = resolveFrontendPath();
        if (!Files.isRegularFile(frontendPath)) {
            return ResponseEntity.notFound().build();
        }

        Resource frontend = new FileSystemResource(frontendPath);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(0, TimeUnit.SECONDS).cachePrivate().mustRevalidate())
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"college-platform-connected.html\"")
                .contentType(MediaType.TEXT_HTML)
                .body(frontend);
    }

    private Path resolveFrontendPath() {
        String path = configuredHtmlPath == null ? "" : configuredHtmlPath.trim();
        if (path.isEmpty()) {
            return Paths.get(System.getProperty("user.home"), "Downloads", "college-platform-connected.html");
        }
        return Paths.get(path).toAbsolutePath().normalize();
    }
}
