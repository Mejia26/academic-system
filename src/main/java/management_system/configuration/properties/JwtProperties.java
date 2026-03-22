package management_system.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Strongly typed configuration properties for JWT.
 * Maps values from application.properties starting with "application.security.jwt".
 */
@ConfigurationProperties(prefix = "application.security.jwt")
public record JwtProperties(
        String secretKey,
        long expiration
) {}