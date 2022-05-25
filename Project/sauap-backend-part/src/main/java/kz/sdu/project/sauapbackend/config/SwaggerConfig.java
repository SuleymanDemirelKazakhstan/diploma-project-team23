package kz.sdu.project.sauapbackend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfig {
    public static final Contact DEFAULT_CONTACT = new Contact("Arsen Gizatov", "https://sauap.kz", "arsen.gizatov@gmail.com");

    public static ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "Sauap app Backend API", "API для приложений SAUAP", "1.0",
            "urn:tos", DEFAULT_CONTACT,
            "GNU AGPLv3", "https://www.gnu.org/licenses/agpl-3.0.ru.html", Collections.emptyList());
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Arrays.asList("application/json"));

    @Bean
    public Docket api() {
        log.info("Starting Swagger API Doc");
        StopWatch watch = new StopWatch();
        watch.start();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("kz.sdu.project.sauapbackend.controller"))
                .paths(PathSelectors.any())
                .build();
        watch.stop();
        log.info("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }
}
