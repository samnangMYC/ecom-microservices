package com.samnang.configuration;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BuildInfoController {

    @Value("${build.id:unknown}")
    private String id;

    @Value("${build.version:unknown}")
    private String version;

    @Value("${build.name:unknown}")
    private String name;

    @GetMapping("/build-info")
    public String getBuildInfo() {
        return "Build ID: " + id + "  Version: " + version + "  Name: " + name;
    }
}


