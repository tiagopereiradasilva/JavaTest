package br.com.sigabem.web.controller;

import br.com.sigabem.exception.StandardErrorResponse;
import br.com.sigabem.exception.ViaCepConsumerException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class SigaBemExceptionController {

    @ExceptionHandler(ViaCepConsumerException.class)
    public ResponseEntity<StandardErrorResponse> viaCepConsumerException(ViaCepConsumerException exception,
                                                                         HttpServletRequest request){
        StandardErrorResponse standardErrorResponse = new StandardErrorResponse(exception.getMessage());
        standardErrorResponse.setTimestamp(Instant.now());
        standardErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        standardErrorResponse.setPath(request.getRequestURI());

        return ResponseEntity.status(standardErrorResponse.getStatus()).body(standardErrorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request){
        List<String> erros = exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(
                        error -> error.getDefaultMessage()
                ).collect(Collectors.toList());

        StandardErrorResponse standardErrorResponse = new StandardErrorResponse(erros);
        standardErrorResponse.setTimestamp(Instant.now());
        standardErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        standardErrorResponse.setPath(request.getRequestURI());
        return ResponseEntity.status(standardErrorResponse.getStatus()).body(standardErrorResponse);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<StandardErrorResponse> invalidFornatException(InvalidFormatException exception,
                                                                        HttpServletRequest request){
        StandardErrorResponse standardErrorResponse = new StandardErrorResponse("Certifique-se de que todos os campos estãos preenchidos corretamente");
        standardErrorResponse.setTimestamp(Instant.now());
        standardErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        standardErrorResponse.setPath(request.getRequestURI());

        return ResponseEntity.status(standardErrorResponse.getStatus()).body(standardErrorResponse);
    }
}
