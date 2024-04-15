package ir.curlymind.webflux.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class CalculatorRouterConfig {
    @Autowired
    private CalculatorHandler handler;

    @Bean
    public RouterFunction<ServerResponse> calculatorFunction() {
        return RouterFunctions.route()
                .path("calculator", this::routerFunction)
                .build();
    }

    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("{a}/{b}", isOperation("+"), handler::additionalHandler)
                .GET("{a}/{b}", isOperation("-"), handler::subtractionHandler)
                .GET("{a}/{b}", isOperation("*"), handler::multiplicationHandler)
                .GET("{a}/{b}", isOperation("/"), handler::divisionHandler)
                .GET("{a}/{b}", request -> ServerResponse.badRequest().bodyValue("OP header should be + - * /"))
                .build();
    }

    private RequestPredicate isOperation(String operation) {
        return RequestPredicates.headers(header -> operation.equals(header.asHttpHeaders().toSingleValueMap().get("OP")));
    }

}




