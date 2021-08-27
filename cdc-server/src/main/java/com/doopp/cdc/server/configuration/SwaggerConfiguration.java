package com.doopp.youlin.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2WebMvc
public class SwaggerConfiguration {

    @Bean(value="adminApi")
    public Docket adminApi(@Value("${swagger.client_ticket}") String clientTicket) {

        ParameterBuilder ticketPar = new ParameterBuilder();
        ticketPar.name("Admin-Token") // Authentication
                .description("Header : Admin token")
                .defaultValue(clientTicket)
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build();

        ParameterBuilder ticketPar2 = new ParameterBuilder();
        ticketPar2.name("Authentication") // Authentication
                .description("Header : Authentication")
                .defaultValue(clientTicket)
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build();

        List<Parameter> pars = new ArrayList<>();
        pars.add(ticketPar.build());
        pars.add(ticketPar2.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact(
                "liuyi",
                "https://blog.doopp.com",
                "liuyi@doopp.com"
        );
        return new ApiInfoBuilder()
                .title("API接口")
                .description("API接口")
                .contact(contact)
                .version("1.0")
                .build();
    }
}
