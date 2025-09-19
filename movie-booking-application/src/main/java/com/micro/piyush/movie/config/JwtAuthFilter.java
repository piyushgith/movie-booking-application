package com.micro.piyush.movie.config;

import com.micro.piyush.movie.service.JWTService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private JWTService jwtService;

    @Autowired
    @Lazy
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        logger.debug("Processing request for path: {}", path);

        // Skip JWT processing for public endpoints
        if (isPublicEndpoint(path)) {
            logger.debug("Skipping JWT processing for public endpoint: {}", path);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String emailId = null;

        logger.debug("Authorization header: {}", authHeader != null ? "Present" : "Missing");

        try {
            if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                logger.debug("Extracted token from header");

                emailId = jwtService.extractEmailId(token);
                logger.debug("Extracted email from token: {}", emailId);
            }

            if (StringUtils.hasText(emailId) && SecurityContextHolder.getContext().getAuthentication() == null) {
                logger.debug("Loading user details for email: {}", emailId);

                UserDetails userDetails = userDetailsService.loadUserByUsername(emailId);
                logger.debug("User details loaded successfully. Authorities: {}", userDetails.getAuthorities());

                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    logger.debug("Authentication set successfully for user: {}", emailId);
                } else {
                    logger.warn("Token validation failed for user: {}", emailId);
                }
            }
        } catch (ExpiredJwtException ex) {
            logger.warn("JWT token expired: {}", ex.getMessage());
            handleAuthenticationError(response, "JWT token expired. Please login again.", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (UnsupportedJwtException ex) {
            logger.warn("Unsupported JWT token: {}", ex.getMessage());
            handleAuthenticationError(response, "Unsupported JWT token", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (MalformedJwtException ex) {
            logger.warn("Invalid JWT token format: {}", ex.getMessage());
            handleAuthenticationError(response, "Invalid JWT token format", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (IllegalArgumentException ex) {
            logger.warn("JWT token compact of handler are invalid: {}", ex.getMessage());
            handleAuthenticationError(response, "Invalid JWT token", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception ex) {
            logger.error("Authentication error: ", ex);
            handleAuthenticationError(response, "Authentication failed", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String path) {
        return path.startsWith("/h2-console/") ||
                path.startsWith("/swagger-ui/") ||
                path.startsWith("/v3/api-docs/") ||
                path.equals("/swagger-ui.html") ||
                path.startsWith("/api/user/") ||
                path.equals("/error");
    }

    private void handleAuthenticationError(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = String.format("{\"error\": \"%s\", \"timestamp\": \"%s\"}",
                message, java.time.Instant.now().toString());
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}