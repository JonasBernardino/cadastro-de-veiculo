package com.cadastro.veiculo.repositories;

import com.cadastro.veiculo.model.VeiculoModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VeiculoRepositoryTest {

    VeiculoModel veiculoModel = new VeiculoModel("Carro", "Ford", 2020, "Exemplo", "Verde", false);
    List<VeiculoModel> veiculoModelList = new ArrayList<>();
    @Autowired
    private VeiculoRepository veiculoRepository;

    @Test
    void salvar_objeto_veiculo() {
        VeiculoModel veiculo = veiculoRepository.save(veiculoModel);
        assertNotNull(veiculo);
        assertTrue(veiculo.getId() > 0 );
        assertEquals(veiculoModel, veiculo);
    }

    @Test
    void findVeiculosNaoVendidos() {
        veiculoRepository.save(veiculoModel);
        List<VeiculoModel>veiculoModels = veiculoRepository.findVeiculosNaoVendidos();
        assertNotNull(veiculoModels);
        assertEquals(veiculoModel.getVendido(), false);
        assertEquals(1, veiculoModels.size());
    }

    @Test
    void findVeiculosByDecada() {
        Integer anoInicio = 2020;
        Integer anoFim = 2029;
        veiculoRepository.save(veiculoModel);
        veiculoModelList.add(veiculoModel);
        veiculoRepository.findVeiculosByDecada(anoInicio, anoFim);
        assertNotNull(veiculoModelList);
        assertEquals(1, veiculoModelList.size());
    }

    @Test
    void findByMarca() {
        veiculoRepository.save(veiculoModel);
        veiculoModelList.add(veiculoModel);
        veiculoRepository.findByMarca(veiculoModel.getMarca());
        assertNotNull(veiculoModelList);
        assertEquals(1, veiculoModelList.size());
    }

    @Test
    void findCarrosCriadosNaUltimaSemana() {
        veiculoRepository.save(veiculoModel);
        veiculoModelList.add(veiculoModel);
        List<VeiculoModel> v1 = veiculoRepository.findCarrosCriadosNaUltimaSemana();
        assertNotNull(v1);
    }

    @Test
    void findVeiculosByMarcaAnoCor() {
        veiculoRepository.save(veiculoModel);
        veiculoModelList.add(veiculoModel);
        veiculoRepository.findVeiculosByMarcaAnoCor(veiculoModel.getCor(), veiculoModel.getAno(), veiculoModel.getMarca());
        assertNotNull(veiculoModelList);
        assertEquals(1, veiculoModelList.size());
    }
}