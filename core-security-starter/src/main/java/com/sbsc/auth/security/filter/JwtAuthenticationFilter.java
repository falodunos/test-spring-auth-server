package com.sbsc.auth.security.filter;

import com.sbsc.auth.security.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return path.startsWith("/api/public/");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        String header = request.getHeader("Authorization");

        // No Authorization header
        if (header == null || !header.startsWith("Bearer ")) {
            log.debug("No Bearer token found for {} {}", method, path);
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {
            // Validate token
            boolean isValid = jwtService.isTokenValid(token);

            if (!isValid) {
                log.warn("Invalid JWT token for {} {}", method, path);
                filterChain.doFilter(request, response);
                return;
            }

            // Extract claims
            String username = jwtService.extractUsername(token);
            Long userId = jwtService.extractUserId(token);
            List<String> roles = jwtService.extractRoles(token);

            log.debug("JWT validated for user='{}', userId={}, roles={}, endpoint={} {}",
                    username, userId, roles, method, path);
            // Convert roles to authorities
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            // Build authentication object
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                    );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception ex) {
            log.error("JWT processing failed for {} {}: {}", method, path, ex.getMessage());
            // Do NOT block request here - let Spring Security handle it (returns 401)
        }

        filterChain.doFilter(request, response);
    }
}