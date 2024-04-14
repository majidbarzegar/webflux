package ir.curlymind.webflux.controller;

import ir.curlymind.webflux.dto.Response;
import ir.curlymind.webflux.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("math")
public class MathController {
    @Autowired
    private MathService service;

    @GetMapping("square/{input}")
    public Response square(@PathVariable int input) {
        return this.service.findSquare(input);
    }

    @GetMapping("table/{input}")
    public List<Response> multiplicationTable(@PathVariable int input) {
        return this.service.multiplication(input);
    }
}
