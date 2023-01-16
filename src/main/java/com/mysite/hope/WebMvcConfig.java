package com.mysite.hope;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    // todo: 허락해줄 경로를 설정해줌. 앞에 file:/// 를 붙여줌
    String PermittedPath = "file:///" +System.getProperty("user.dir") + "/src/main/resources/static/files";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files") // PermittedPath 경로들/+a
                .addResourceLocations(PermittedPath); // 로컬에 저장된 파일을 읽어 올 root 경로 설정
    }
}