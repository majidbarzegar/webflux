package ir.curlymind.webflux.controller;

import ir.curlymind.webflux.dto.Response;
import ir.curlymind.webflux.exception.InputValidationException;
import ir.curlymind.webflux.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathValidationController {

    @Autowired
    private ReactiveMathService service;

    @GetMapping("square/{input}/throw")
    public Mono<Response> square(@PathVariable int input) {
        if (input < 10 || input > 20) {
            throw new InputValidationException(input);
        }
        return this.service.findSquare(input);
    }

    @GetMapping("square/{input}/mono-error")
    public Mono<Response> monoError(@PathVariable int input) {
        return Mono
                .just(input)
                .handle((i, sink) -> { // filter + map
                    if (input < 10 || input > 20) {
                        sink.error(new InputValidationException(input));
                    } else {
                        sink.next(i);
                    }
                })
                .cast(Integer.class)
                .flatMap(i -> this.service.findSquare(i));

    }

    @GetMapping("square/{input}/assignment")
    public Mono<ResponseEntity<Response>> assignment(@PathVariable int input) {
        return Mono
                .just(input)
                .filter(i -> i >= 10 && i <= 20)
                .flatMap(i -> this.service.findSquare(i))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());

    }
}


