package ir.curlymind.webflux.config;

import ir.curlymind.webflux.dto.MultiplyRequestDto;
import ir.curlymind.webflux.dto.Response;
import ir.curlymind.webflux.exception.InputValidationException;
import ir.curlymind.webflux.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {
    @Autowired
    private ReactiveMathService mathService;

    public Mono<ServerResponse> squareHandler(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        Mono<Response> square = this.mathService.findSquare(input);
        return ServerResponse.ok().body(square, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        Flux<Response> multiplication = this.mathService.multiplication(input);
        return ServerResponse.ok().body(multiplication, Response.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        Flux<Response> multiplication = this.mathService.multiplication(input);
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(multiplication, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest request) {
        Mono<MultiplyRequestDto> dtoMono = request.bodyToMono(MultiplyRequestDto.class);
        Mono<Response> multiply = this.mathService.multiply(dtoMono);
        return ServerResponse.ok().body(multiply, Response.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        if (input < 10 || input > 20) {
            return Mono.error(new InputValidationException(input));
        }
        Mono<Response> square = this.mathService.findSquare(input);
        return ServerResponse.ok().body(square, Response.class);
    }

}
