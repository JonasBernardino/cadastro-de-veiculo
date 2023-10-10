package com.cadastro.exception.handler;

import com.cadastro.exception.model.Response;
import com.cadastro.veiculo.veiculoException.VeiculoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ResourceHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Map<String, String>>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> erros = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(erro -> {
            String campo = ((FieldError) erro).getField();
            String mensagem = erro.getDefaultMessage();
            erros.put(campo, mensagem);
        });
        Response<Map<String, String>> response = new Response<>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setData(erros);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }



    @ExceptionHandler(VeiculoException.class)
    public ResponseEntity<Response<String>> handlerVeiculoException(VeiculoException veiculoException){
        Response<String> response = new Response<>();
        response.setStatusCode(veiculoException.getHttpStatus().value());
        response.setData(veiculoException.getMessage());
        return ResponseEntity.status(veiculoException.getHttpStatus()).body(response);

    }
}
