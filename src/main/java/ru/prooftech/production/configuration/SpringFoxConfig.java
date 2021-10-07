package ru.prooftech.production.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration

@Import({BeanValidatorPluginsConfiguration.class, SpringDataRestConfiguration.class})
public class SpringFoxConfig {
    public static final String PRODUCT_TAG = "products";
    public static final String MATERIAL_TAG = "materials";
    public static final String ORDER_TAG = "orders";
    public static final String PERSON_TAG = "persons";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.prooftech.production"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Production REST API")
                .description("Test task for company Prooftech.")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(new Contact("Artem Zuev", "http://git.holyzlo.ru", "info@holyzlo.com"))
                .build();
    }
}
