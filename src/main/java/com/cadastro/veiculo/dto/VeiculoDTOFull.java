package com.cadastro.veiculo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VeiculoDTOFull {

    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank(message = "Informe o veiculo.")
    private String veiculo;

    @NotNull
    @NotEmpty
    @NotBlank(message = "Informe a marca.")
    private String marca;

    private Integer ano;

    private String descricao;

    private String cor;

    private Boolean vendido;

    private Date criado;

    private Date atualizado;

    public VeiculoDTOFull(Long id, String veiculo, String marca, Integer ano, String descricao, String cor, Boolean vendido) {
        this.id = id;
        this.veiculo = veiculo;
        this.marca = marca;
        this.ano = ano;
        this.descricao = descricao;
        this.cor = cor;
        this.vendido = vendido;

    }
}
