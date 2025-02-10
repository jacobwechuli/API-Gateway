package com.api.gateway.config;

import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter implements WebFilter {

    private final JwtUtil jwtUtil;
    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostConstruct
    public void init() {
        System.out.println("🚀 JwtAuthFilter Initialized: API Gateway is Using This Filter!");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if (path.startsWith("/auth") || path.startsWith("/actuator")) {
            return chain.filter(exchange);
        }
        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return this.onError(exchange, "Missing Authorization header", HttpStatus.UNAUTHORIZED);
        }
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return this.onError(exchange, "Invalid Authorization header", HttpStatus.UNAUTHORIZED);
        }
        String token = authHeader.substring(7);
        System.out.println("Extracted Token: " + token);
        System.out.println("Roles: " + jwtUtil.extractRoles(token));

        if (!jwtUtil.validateToken(token)) {
            return this.onError(exchange, "Invalid JWT Token", HttpStatus.UNAUTHORIZED);
        }
        if (request.getURI().getPath().startsWith("/orders")){
            if (!jwtUtil.extractRoles(token).contains("ADMIN")) {
                return this.onError(exchange, "Forbidden: Admins only", HttpStatus.FORBIDDEN);
            }
        }
        return chain.filter(exchange);
    }
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}
