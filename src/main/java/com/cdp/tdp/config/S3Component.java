package com.cdp.tdp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cloud.aws.s3")
@Getter
@Setter
public class S3Component {
    private String bucket;
}