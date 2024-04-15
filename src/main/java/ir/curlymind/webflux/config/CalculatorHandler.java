package ir.curlymind.webflux.config;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
public class CalculatorHandler {

    public Mono<ServerResponse> additionalHandler(ServerRequest req) {
        return process(req, (first, second) -> ServerResponse.ok().bodyValue(first + second));
    }

    public Mono<ServerResponse> subtractionHandler(ServerRequest req) {
        return process(req, (first, second) -> ServerResponse.ok().bodyValue(first - second));
    }

    public Mono<ServerResponse> multiplicationHandler(ServerRequest req) {
        return process(req, (first, second) -> ServerResponse.ok().bodyValue(first * second));
    }

    public Mono<ServerResponse> divisionHandler(ServerRequest req) {
        return process(req, (first, second) ->
                second != 0 ? ServerResponse.ok().bodyValue(first / second) : ServerResponse.badRequest().bodyValue("second value cant not be 0")
        );
    }


    private Mono<ServerResponse> process(ServerRequest req,
                                         BiFunction<Integer, Integer, Mono<ServerResponse>> opLogic) {
        int first = getValue(req, "a");
        int second = getValue(req, "b");
        return opLogic.apply(first, second);
    }

    private int getValue(ServerRequest req, String key) {
        return Integer.parseInt(req.pathVariable(key));
    }
}
