package com.cadastro.veiculo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class VeiculoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Temporal(TemporalType.TIMESTAMP)
    private Date criado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date atualizado;

    public VeiculoModel(String veiculo, String marca, Integer ano, String descricao, String cor, Boolean vendido) {
        this.veiculo = veiculo;
        this.marca = marca;
        this.ano = ano;
        this.descricao = descricao;
        this.cor = cor;
        this.vendido = vendido;
    }

    public VeiculoModel(Long id, String veiculo, String marca, Integer ano, String descricao, String cor, Boolean vendido) {
        this.id = id;
        this.veiculo = veiculo;
        this.marca = marca;
        this.ano = ano;
        this.descricao = descricao;
        this.cor = cor;
        this.vendido = vendido;
    }

    @PrePersist
    protected void onCreate() {
        if (this.criado == null && this.id == null) {
            this.criado = new Date();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.atualizado = new Date();
    }


}
