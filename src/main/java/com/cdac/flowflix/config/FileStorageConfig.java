package com.cdac.flowflix.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileStorageConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(
            ResourceHandlerRegistry registry) {

        registry.addResourceHandler(
                "/uploads/posters/**"
        ).addResourceLocations(
                "file:uploads/posters/"
        );


        registry.addResourceHandler(
                "/uploads/banners/**"
        ).addResourceLocations(
                "file:uploads/banners/"
        );


        registry.addResourceHandler(
                "/uploads/videos/**"
        ).addResourceLocations(
                "file:uploads/videos/"
        );


        registry.addResourceHandler(
                "/uploads/trailers/**"
        ).addResourceLocations(
                "file:uploads/trailers/"
        );
    }
}