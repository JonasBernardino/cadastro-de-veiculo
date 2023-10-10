package com.cadastro.veiculo.dto;

import com.cadastro.veiculo.model.VeiculoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VeiculosTotalResponse {
    private List<VeiculoModel> veiculos;
    private int totalVeiculos;
}
