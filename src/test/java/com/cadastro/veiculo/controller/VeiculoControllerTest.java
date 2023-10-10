package com.cadastro.veiculo.controller;

import com.cadastro.exception.model.Response;
import com.cadastro.veiculo.dto.VeiculoDTOFull;
import com.cadastro.veiculo.dto.VeiculosTotalResponse;
import com.cadastro.veiculo.model.VeiculoModel;
import com.cadastro.veiculo.services.VeiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class VeiculoControllerTest {
    @Mock
    private VeiculoService veiculoService;

    @InjectMocks
    private VeiculoController veiculoController;

    @Mock
    private ModelMapper modelMapper;

    private VeiculoDTOFull veiculoDTOFull;

    List<VeiculoDTOFull> veiculos = new ArrayList<>();
    List<VeiculoModel> modelList = new ArrayList<>();
    VeiculosTotalResponse totalResponse = new VeiculosTotalResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        veiculoDTOFull = new VeiculoDTOFull(
                1L,
                "Moto",
                "Honda",
                2020,
                "Exemplo de moto",
                "Preto",
                false);
    }

    @Test
    void findByIdVeiculosSuccess() {
        when(veiculoService.findByIdVeiculo(Mockito.anyLong())).thenReturn(veiculoDTOFull);
        when(modelMapper.map(any(), eq(VeiculoDTOFull.class))).thenReturn(veiculoDTOFull);
        ResponseEntity<Response<VeiculoDTOFull>> response = veiculoController.findByIdVeiculos(1L);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(VeiculoDTOFull.class, response.getBody().getData().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void findAllVeiculos() {
        Pageable pageable = Pageable.ofSize(10).withPage(0);
        veiculos.add(veiculoDTOFull);
        Page<VeiculoDTOFull> veiculoPage = new PageImpl<>(veiculos, pageable, veiculos.size());
        when(veiculoService.findAllVeiculos(pageable)).thenReturn(veiculoPage);
        Response<Page<VeiculoDTOFull>> response = veiculoController.findAllVeiculos(pageable);
        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(veiculos.size(), response.getData().getContent().size());
    }

    @Test
    void saveVeiculo() {
        when(veiculoService.saveVeiculo(any())).thenReturn(true);
        when(modelMapper.map(any(), eq(VeiculoDTOFull.class))).thenReturn(veiculoDTOFull);
        ResponseEntity<Response<Boolean>> responseEntity = veiculoController.saveVeiculo(veiculoDTOFull);
        assertNotNull(responseEntity);
        Response<Boolean> response = responseEntity.getBody();
        assertNotNull(response);
        assertTrue(response.getData());
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    void updateVeiculo() {
        when(veiculoService.updateVeiculo(any())).thenReturn(true);
        when(modelMapper.map(any(), eq(VeiculoDTOFull.class))).thenReturn(veiculoDTOFull);
        ResponseEntity<Response<Boolean>> responseEntity = veiculoController.updateVeiculo(veiculoDTOFull);
        assertNotNull(responseEntity);
        Response<Boolean> response = responseEntity.getBody();
        assertNotNull(response);
        assertTrue(response.getData());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    void deleteVeiculo() {
        when(veiculoService.deleteVeiculo(anyLong())).thenReturn(true);
        when(modelMapper.map(any(), eq(VeiculoDTOFull.class))).thenReturn(veiculoDTOFull);
        ResponseEntity<Response<Boolean>> responseEntity = veiculoController.deleteVeiculo(1L);
        assertNotNull(responseEntity);
        Response<Boolean> response = responseEntity.getBody();
        assertNotNull(response);
        assertTrue(response.getData());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    void findByNaoVendidos() {
        when(veiculoService.findVeiculosNaoVendidos()).thenReturn(totalResponse);
        when(modelMapper.map(any(), eq(VeiculoDTOFull.class))).thenReturn(veiculoDTOFull);
        ResponseEntity<Response<VeiculosTotalResponse>> response = veiculoController.findByNaoVendidos();
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(VeiculosTotalResponse.class, response.getBody().getData().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findByPorDecada() {
        when(veiculoService.findVeiculosPorDecada(anyInt())).thenReturn(totalResponse);
        when(modelMapper.map(any(), eq(VeiculoDTOFull.class))).thenReturn(veiculoDTOFull);
        ResponseEntity<Response<VeiculosTotalResponse>> response = veiculoController.findByPorDecada(2020);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(VeiculosTotalResponse.class, response.getBody().getData().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findVeiculosPorMarca() {
        when(veiculoService.findVeiculosPorMarca(anyString())).thenReturn(totalResponse);
        when(modelMapper.map(any(), eq(VeiculoDTOFull.class))).thenReturn(veiculoDTOFull);
        ResponseEntity<Response<VeiculosTotalResponse>> response = veiculoController.findVeiculosPorMarca("Honda");
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(VeiculosTotalResponse.class, response.getBody().getData().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findCarrosCriadosNaUltimaSemana() {
        when(veiculoService.findCarrosCriadosNaUltimaSemana()).thenReturn(totalResponse);
        when(modelMapper.map(any(), eq(VeiculoDTOFull.class))).thenReturn(veiculoDTOFull);
        ResponseEntity<Response<VeiculosTotalResponse>> response = veiculoController.findCarrosCriadosNaUltimaSemana();
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(VeiculosTotalResponse.class, response.getBody().getData().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void buscarPorMarcaAnoCor() {
        when(veiculoService.findVeiculosByMarcaAnoCor(anyString(), anyInt(), anyString())).thenReturn(modelList);
        ResponseEntity<Response<List<VeiculoModel>>> response = veiculoController.buscarPorMarcaAnoCor("Honda", 2020, "Preto");
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}