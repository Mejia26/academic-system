package management_system.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger / OpenAPI 3 documentation.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "cookieAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Academic Management System API")
                        .version("1.0.0")
                        .description("REST API documentation for the ITLA Final Project. " +
                                "This API uses an HttpOnly cookie named 'token' for authentication. " +
                                "Once you log in via the /auth/login endpoint, the browser (and Swagger) " +
                                "will automatically attach the cookie to subsequent requests.")
                        .contact(new Contact()
                                .name("Backend Team (Group 44)")
                                .email("admin@academic.com")))
                // 1. Define the Security Scheme (Cookie based)
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name("token")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)
                                .description("JWT Token stored in an HttpOnly cookie.")))
                // 2. Apply it globally to all endpoints
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
    }
}