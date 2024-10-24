package com.yahve.config;

import com.yahve.command.OperationCommand;
import com.yahve.listener.OperationsConsoleListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

@Configuration
@ComponentScan(basePackages = "com.yahve")
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("application.properties"));
        return configurer;
    }

    @Bean
    public OperationsConsoleListener operationsConsoleListener(List<OperationCommand> commands) {
        return new OperationsConsoleListener(commands);
    }


}
