package org.ecommerce.apigateaway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Vérifier si l'en-tête Authorization est présent
        String authHeader = request.getHeaders().getFirst("Authorization");

        if (authHeader != null) {
            // Propagation du token à toutes les requêtes sortantes
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("Authorization", authHeader)
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // S'assurer que ce filtre s'exécute avant les autres
        return -1;
    }
}