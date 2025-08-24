package com.noithat.backend;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class EndpointPrinter {

    @Bean
    public ApplicationRunner applicationRunner(ApplicationContext applicationContext) {
        return args -> {
            System.out.println("Danh sách tất cả các endpoint:");
            RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
            mapping.getHandlerMethods().forEach((key, value) -> {
                System.out.println( key + " => " + value.getMethod().getName());
            });
        };
    }
}

