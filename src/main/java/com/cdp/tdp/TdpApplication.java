package com.cdp.tdp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TdpApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.properties"
            + "classpath:application-aws.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(TdpApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

}
