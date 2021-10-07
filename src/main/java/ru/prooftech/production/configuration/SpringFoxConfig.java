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
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;

@Configuration
@EnableSwagger2
@Import({BeanValidatorPluginsConfiguration.class, SpringDataRestConfiguration.class})
public class SpringFoxConfig {
    public static final String PRODUCT_TAG = "product";
    public static final String MATERIAL_TAG = "material";
    public static final String ORDER_TAG = "order";
    public static final String PERSON_TAG = "person";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag(PRODUCT_TAG, "Производимая продукция"),
                        new Tag(MATERIAL_TAG, "Материалы используемые в производстве"),
                        new Tag(ORDER_TAG, "Заказы клиентов"),
                        new Tag(PERSON_TAG, "Клиенты"))
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
