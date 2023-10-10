package com.cadastro.veiculo.veiculoException;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class VeiculoException extends RuntimeException{

    private final HttpStatus httpStatus;

    public VeiculoException(final String mensagem, HttpStatus httpStatus) {
        super((mensagem));
        this.httpStatus = httpStatus;
    }
}
