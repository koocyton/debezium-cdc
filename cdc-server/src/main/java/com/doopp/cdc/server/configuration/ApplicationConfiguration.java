package com.doopp.youlin.server.configuration;

import com.doopp.youlin.util.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;


@Configuration
@Import({
        RedisConfiguration.class,
        MyBatisConfiguration.class,
        SwaggerConfiguration.class,
        OkHttpConfiguration.class
})
// @EnableAspectJAutoProxy(exposeProxy=true)
@EnableCaching
@ComponentScan(
    basePackages = {"com.doopp.youlin"},
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

    // @Bean
    // public static AliyunSMS aliyunSMS(@Qualifier("okHttpClient") OkHttpClient okHttpClient,
    //                                   @Value("${aliyun.sms.api_host}") String smsApiHost,
    //                                   @Value("${aliyun.oss.id}") String accessKeyId,
    //                                   @Value("${aliyun.oss.key}") String accessKeySecret) {
    //
    //     return new AliyunSMS(okHttpClient, smsApiHost, accessKeyId, accessKeySecret);
    // }

    // @Bean
    // public JavaMailSender mailSender(@Value("${mail.smtp.host}") String host,
    //                                  @Value("${mail.smtp.user}") String user,
    //                                  @Value("${mail.smtp.pass}") String pass,
    //                                  @Value("${mail.smtp.port:465}") int port) {
    //
    //     JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    //     mailSender.setHost(host);
    //     mailSender.setUsername(user);
    //     mailSender.setPassword(pass);
    //     mailSender.setPort(port);
    //     mailSender.setDefaultEncoding("UTF-8");
    //
    //     Properties properties = new Properties();
    //     properties.setProperty("mail.smtp.ssl.enable", "true");
    //     properties.setProperty("mail.smtp.auth", "true");//开启认证
    //     properties.setProperty("mail.debug", "false");//启用调试
    //     properties.setProperty("mail.smtp.timeout", "30000");//设置链接超时
    //     properties.setProperty("mail.smtp.port", Integer.toString(port));//设置端口
    //     properties.setProperty("mail.smtp.socketFactory.port", Integer.toString(port));//设置ssl端口
    //     properties.setProperty("mail.smtp.socketFactory.fallback", "false");
    //     properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    //     mailSender.setJavaMailProperties(properties);
    //
    //     return mailSender;
    // }
}
