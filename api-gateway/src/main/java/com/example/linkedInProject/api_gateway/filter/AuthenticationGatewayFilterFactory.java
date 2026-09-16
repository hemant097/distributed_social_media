package com.example.linkedInProject.api_gateway.filter;

import com.example.linkedInProject.api_gateway.service.JWTService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends
        AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final JWTService jwtService;

    public AuthenticationGatewayFilterFactory(JWTService jwtService){
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){

                log.info("Auth request: {}",exchange.getRequest().getURI());

                String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

                if (authHeader == null || !authHeader.startsWith("Bearer ")){
                    return writeErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Authorization header is missing");
                }

                String token = authHeader.substring(7).trim();

                try{
//                    log.info("Before JWT service");

                    Long userId = jwtService.getUserIdFromToken(token);

//                    log.info("After JWT service");

                    log.info("Jwt token valid for path: {}",exchange.getRequest().getURI().getPath());


                    //mutating the request, as mutate creates a builder, and thus a new request ,
                    ServerHttpRequest mutatedReq = exchange.getRequest()
                            .mutate()
                            .header("X-User-Id",userId.toString())
                            .build();


                    //mutated exchange with the new request
                    ServerWebExchange newExchange = exchange
                            .mutate()
                            .request(mutatedReq)
                            .build();

//                    OR we can simply combine the 2 steps into one, but used both for better clarity
//
//                    ServerWebExchange newExchange = exchange.mutate()
//                    .request( req -> req.header("X-User-Id",userId)
//                    .build()

                    //passing the new exchange down the filter chain
                    return chain.filter(newExchange);

                } catch (JwtException jwtException) {
                    log.error("Jwt exception {}",jwtException.getLocalizedMessage());
                    return writeErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Jwt Exception occurred");
                }

            }
        };
    }


    @Data
    public static class Config{
    }

    private Mono<Void> writeErrorResponse(ServerWebExchange exchange, HttpStatus status, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new LinkedHashMap<>();
        body.put("message", message);
        body.put("timestamp", Instant.now().toString());

        try {
            byte[] bytes = new ObjectMapper().writeValueAsBytes(body);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            return response.setComplete(); // safe fallback
        }
    }
}

