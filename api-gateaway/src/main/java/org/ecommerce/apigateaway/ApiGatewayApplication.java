package org.ecommerce.apigateaway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoutes() {
        return RouterFunctions.route()
                .GET("/fallback/user", request -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "User Service is currently unavailable");
                    response.put("status", "Service Unavailable");
                    return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(response);
                })
                .GET("/fallback/product", request -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Product Service is currently unavailable");
                    response.put("status", "Service Unavailable");
                    return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(response);
                })
                .GET("/fallback/order", request -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Order Service is currently unavailable");
                    response.put("status", "Service Unavailable");
                    return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(response);
                })
                .GET("/fallback/payment", request -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Payment Service is currently unavailable");
                    response.put("status", "Service Unavailable");
                    return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(response);
                })
                .GET("/fallback/notification", request -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Notification Service is currently unavailable");
                    response.put("status", "Service Unavailable");
                    return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(response);
                })
                .build();
    }
}