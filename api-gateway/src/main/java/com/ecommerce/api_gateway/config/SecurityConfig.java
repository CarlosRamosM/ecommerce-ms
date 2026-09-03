package com.ecommerce.api_gateway.config;

import com.ecommerce.api_gateway.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain for the API Gateway.
     * Enables CSRF protection and OAuth2 resource server with JWT authentication.
     *
     * @param httpSecurity the server HTTP security configuration
     * @return the configured security web filter chain
     */
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                .pathMatchers("/eureka/**")
                .permitAll()
                .pathMatchers(HttpMethod.GET, "/api/products/**")
                .permitAll()
                .pathMatchers(HttpMethod.GET, "/api/inventory/**")
                .permitAll()
                .pathMatchers("/api/products/**")
                .hasRole(Role.ADMIN.name())
                .pathMatchers("/api/inventory/**")
                .hasRole(Role.ADMIN.name())
                .pathMatchers(HttpMethod.POST, "/api/orders")
                .hasRole(Role.USER.name())
                .pathMatchers(HttpMethod.GET, "/api/orders/**")
                .hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                .pathMatchers(HttpMethod.DELETE, "/api/orders/**")
                .hasRole(Role.ADMIN.name())
                .pathMatchers(HttpMethod.PUT, "/api/orders/**")
                .hasRole(Role.ADMIN.name())
                .anyExchange()
                .authenticated())
            .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                .jwt(jwtSpec -> jwtSpec
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return httpSecurity.build();
    }

    private ReactiveJwtAuthenticationConverterAdapter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> claims = (Map<String, Object>) jwt.getClaims().get("realm_access");
            if (claims == null || claims.isEmpty()) {
                return Collections.emptyList();
            }
            Collection<String> roles = (Collection<String>) claims.get("roles");
            return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        });
        return new ReactiveJwtAuthenticationConverterAdapter(converter);
    }
}
