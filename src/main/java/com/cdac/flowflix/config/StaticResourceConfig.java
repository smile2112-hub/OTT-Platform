/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
package com.cdac.flowflix.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig
        implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(
            ResourceHandlerRegistry registry) {

        Path uploadPath =
                Paths.get(

// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

                        System.getProperty("user.dir"),
                        "uploads"
                )
                .toAbsolutePath()
                .normalize();

        String uploadLocation =
                uploadPath.toUri().toString();

        if (!uploadLocation.endsWith("/")) {

            uploadLocation += "/";
        }

        registry
            .addResourceHandler(
                    "/uploads/**"
            )
            .addResourceLocations(
                    uploadLocation
            );
    }
}
