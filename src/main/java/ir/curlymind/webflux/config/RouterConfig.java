package ir.curlymind.webflux.config;

import ir.curlymind.webflux.dto.InputFailedValidationResponse;
import ir.curlymind.webflux.exception.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {

    @Autowired
    private RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> serverFunction() {
        return RouterFunctions.route()
                .path("router", this::routerFunction)
                .build();
    }

    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("square/{input}", requestHandler::squareHandler)
//                .GET("square/{input}", RequestPredicates.path("*/1?"), requestHandler::squareHandler)
                .GET("square/{input}/with-validation", requestHandler::squareHandlerWithValidation)
                .GET("table/{input}", requestHandler::tableHandler)
                .GET("table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("multiply", requestHandler::multiplyHandler)
                .POST("multiply", requestHandler::multiplyHandler)
                .onError(InputValidationException.class, inputValidationExceptionHandler())
                .build();
    }

    private BiFunction<InputValidationException, ServerRequest, Mono<ServerResponse>> inputValidationExceptionHandler() {
        return (e, request) -> {
            InputFailedValidationResponse resp = new InputFailedValidationResponse();
            resp.setErrorCode(e.getErrorCode());
            resp.setInput(e.getInput());
            resp.setMessage(e.getMessage());
            return ServerResponse.badRequest().bodyValue(resp);
        };
    }
}
