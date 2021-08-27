package com.doopp.cdc.server.configuration;

import com.doopp.cdc.util.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;


@Configuration
@Import({
        OkHttpConfiguration.class
})
// @EnableAspectJAutoProxy(exposeProxy=true)
// @EnableCaching
@ComponentScan(
    basePackages = {"com.doopp.cdc"},
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class})
    }
)
public class ApplicationConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new FileSystemResource(System.getProperty("applicationPropertiesConfig")));
        return configurer;
    }

    @Bean
    public static IdWorker idWorker(@Value("${id-worker.workerId}") Long workerId, @Value("${id-worker.dataCenterId}") Long dataCenterId) {
        return new IdWorker(workerId, dataCenterId);
    }
}
