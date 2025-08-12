package com.samnang.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "build")
@Data
public class BuildInfo {
   private String id;
   private String version;
   private String name;
}
