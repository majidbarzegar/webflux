package ir.curlymind.webflux.controller;

import ir.curlymind.webflux.dto.MultiplyRequestDto;
import ir.curlymind.webflux.dto.Response;
import ir.curlymind.webflux.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {
    @Autowired
    private ReactiveMathService service;

    @GetMapping("square/{input}")
    public Mono<Response> square(@PathVariable int input) {
        return this.service.findSquare(input);
    }

    @GetMapping("table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input) {
//        AbstractJackson2Encoder
        return this.service.multiplication(input);
    }

    @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input) {
        return this.service.multiplication(input);
    }

    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> dtoMono) {
        return this.service.multiply(dtoMono);
    }

}
