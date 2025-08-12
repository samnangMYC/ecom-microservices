package com.samnang.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BuildInfoController {
    private final BuildInfo buildInfo;


    @GetMapping("/build-info")
    public String getBuildInfo() {
        return "Build ID: " + buildInfo.getId() + "  Version: " + buildInfo.getVersion() + "  Name: " + buildInfo.getName();
    }
}
