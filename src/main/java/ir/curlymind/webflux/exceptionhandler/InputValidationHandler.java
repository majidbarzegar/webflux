package ir.curlymind.webflux.exceptionhandler;

import ir.curlymind.webflux.dto.InputFailedValidationResponse;
import ir.curlymind.webflux.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {
    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException ex) {
        InputFailedValidationResponse resp = new InputFailedValidationResponse();
        resp.setInput(ex.getInput());
        resp.setMessage(ex.getMessage());
        resp.setErrorCode(ex.getErrorCode());
        return ResponseEntity.badRequest().body(resp);
    }
}
