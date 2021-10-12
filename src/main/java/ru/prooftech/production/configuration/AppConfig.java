package ru.prooftech.production.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("ru.prooftech.production")
@EnableJpaRepositories("ru.prooftech.production.repositories")
@EntityScan("ru.prooftech.production.entities")
public class AppConfig {

}
