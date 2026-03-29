package com.sbsc.auth.security.config;

import com.sbsc.auth.security.jwt.JwtService;
import com.sbsc.auth.security.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
@Import(SecurityConfig.class)
public class SecurityAutoConfiguration {

    @Bean
    public JwtService jwtService(SecurityProperties securityProperties) {
        return new JwtService(securityProperties);
    }

}
