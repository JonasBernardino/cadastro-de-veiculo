package com.cadastro.veiculo.services;

import com.cadastro.veiculo.dto.VeiculoDTOFull;
import com.cadastro.veiculo.dto.VeiculosTotalResponse;
import com.cadastro.veiculo.model.VeiculoModel;
import com.cadastro.veiculo.repositories.VeiculoRepository;
import com.cadastro.veiculo.veiculoException.VeiculoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.*;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class VeiculoServiceTest {

    private VeiculoModel veiculoModel = new VeiculoModel(   1L,
            "Moto",
            "Honda",
            2020,
            "Exemplo de moto",
            "Preto",
            false);

    private VeiculoModel veiculo;
    @Mock
    private VeiculoRepository veiculoRepository;
    @InjectMocks
    private VeiculoService veiculoService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private Logger log;
    private VeiculoDTOFull veiculoDTOFull;
    List<VeiculoModel> veiculoModelList = new ArrayList<>();
    private Optional<VeiculoModel> veiculoModelOptional;


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
        veiculoModelOptional = Optional.of(new VeiculoModel(
                1L,
                "Moto",
                "Honda",
                2020,
                "Exemplo de moto",
                "Preto",
                false
        ));

    }

    @Test
    void findAllVeiculos() {
        Pageable pageable = mock(Pageable.class);
        Page<VeiculoModel> veiculoModelsPage = new PageImpl<>(veiculoModelList);
        when(veiculoRepository.findAll()).thenReturn(veiculoModelList);
        when(veiculoRepository.findAll(pageable)).thenReturn(veiculoModelsPage);
        Page<VeiculoDTOFull> response = veiculoService.findAllVeiculos(pageable);
        assertNotNull(response);
        assertEquals(veiculoModelsPage.getTotalElements(), response.getTotalElements());
    }

    @Test
    void findByIdVeiculo_VeiculoEncontrado() {
        when(veiculoRepository.findById(Mockito.anyLong())).thenReturn(veiculoModelOptional);
        when(modelMapper.map(any(), eq(VeiculoDTOFull.class))).thenReturn(veiculoDTOFull);
        VeiculoDTOFull response = veiculoService.findByIdVeiculo(1L);
        assertNotNull(response);
    }
    @Test
    void findByIdVeiculo_VeiculoNaoEncontrado() {
        when(veiculoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        VeiculoException exception = assertThrows(VeiculoException.class, () -> veiculoService.findByIdVeiculo(2L));
        assertEquals("Veiculo não encontrado", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void saveVeiculo() {
        when(veiculoRepository.save(any())).thenReturn(veiculoModel);
        when(modelMapper.map(any(), eq(VeiculoModel.class))).thenReturn(veiculoModel);
        Boolean result = veiculoService.saveVeiculo(veiculoDTOFull);
        assertTrue(result);
    }

    @Test
    void saveVeiculo_BAD_REQUEST() {
        when(veiculoRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
        VeiculoException exception = assertThrows(VeiculoException.class, () -> veiculoService.saveVeiculo(veiculoDTOFull));
        assertEquals("Error", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void saveVeiculo_INTERNAL_SERVER_ERROR() {
        when(veiculoRepository.save(any())).thenThrow(VeiculoException.class);
        VeiculoException exception = assertThrows(VeiculoException.class, () -> veiculoService.saveVeiculo(veiculoDTOFull));
        assertEquals("Error interno", exception.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void updateVeiculo() {
        when(veiculoRepository.findById(Mockito.anyLong())).thenReturn(veiculoModelOptional);
        when(modelMapper.map(any(), eq(VeiculoDTOFull.class))).thenReturn(veiculoDTOFull);
        veiculoService.saveVeiculo(veiculoDTOFull);
        veiculoDTOFull.setCor("Rosa");
        Boolean result = veiculoService.updateVeiculo(veiculoDTOFull);
        assertEquals(VeiculoDTOFull.class, veiculoDTOFull.getClass());
        assertTrue(result);
    }

    @Test
    void deleteVeiculo_VeiculoExists() {
        when(veiculoRepository.findById(Mockito.anyLong())).thenReturn(veiculoModelOptional);
        when(modelMapper.map(any(), eq(VeiculoDTOFull.class))).thenReturn(veiculoDTOFull);
        Boolean result = veiculoService.deleteVeiculo(1L);
        verify(veiculoRepository, times(1)).deleteById(1L);
        assertTrue(result);

    }
    @Test
    void deleteVeiculo_VeiculoNotFound(){
        when(veiculoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        VeiculoException exception = assertThrows(VeiculoException.class, () -> veiculoService.deleteVeiculo(2L));
        assertEquals("Veiculo não encontrado", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void findVeiculosNaoVendidos() {
        when(veiculoRepository.findVeiculosNaoVendidos()).thenReturn(veiculoModelList);
        when(modelMapper.map(any(), eq(VeiculoDTOFull.class))).thenReturn(veiculoDTOFull);
        veiculoModelList.add(veiculoModel);
        VeiculosTotalResponse response = veiculoService.findVeiculosNaoVendidos();
        assertNotNull(response);
        assertEquals(false, response.getVeiculos().get(0).getVendido());

    }
    @Test
    void findVeiculosPorDecada() {
        when(veiculoRepository.findVeiculosByDecada(Mockito.any(Integer.class), Mockito.any(Integer.class)))
                .thenReturn(veiculoModelList);
        when(modelMapper.map(any(), eq(VeiculoDTOFull.class))).thenReturn(veiculoDTOFull);
        veiculoModelList.add(veiculoModel);
        VeiculosTotalResponse response = veiculoService.findVeiculosPorDecada(2020);
        assertNotNull(response);
        assertEquals(veiculoModelList, response.getVeiculos());
        assertEquals(veiculoModelList.size(), response.getTotalVeiculos());
    }

    @Test
    void findVeiculosPorMarca() {
        when(veiculoRepository.findByMarca(Mockito.anyString())).thenReturn(veiculoModelList);
        when(modelMapper.map(any(), eq(VeiculoDTOFull.class))).thenReturn(veiculoDTOFull);
        veiculoModelList.add(veiculoModel);
        VeiculosTotalResponse response = veiculoService.findVeiculosPorMarca("Honda");
        assertNotNull(response);
        assertEquals(response.getVeiculos().get(0).getMarca(), veiculoModel.getMarca());
        assertEquals(response.getTotalVeiculos(), 1);
        assertEquals(veiculoModelList, response.getVeiculos());
        assertEquals(veiculoModelList.size(), response.getTotalVeiculos());
    }

    @Test
    void findCarrosCriadosNaUltimaSemana() {
        when(veiculoRepository.findCarrosCriadosNaUltimaSemana()).thenReturn(veiculoModelList);
        when(modelMapper.map(any(), eq(VeiculoDTOFull.class))).thenReturn(veiculoDTOFull);
        veiculoModelList.add(veiculoModel);
        VeiculosTotalResponse response = veiculoService.findCarrosCriadosNaUltimaSemana();
        assertNotNull(response);
        assertEquals(response.getTotalVeiculos(), 1);
        assertEquals(veiculoModelList, response.getVeiculos());
        assertEquals(veiculoModelList.size(), response.getTotalVeiculos());
    }

    @Test
    void findVeiculosByMarcaAnoCor() {
        when(veiculoRepository.findVeiculosByMarcaAnoCor(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString())).thenReturn(veiculoModelList);
        when(modelMapper.map(any(), eq(VeiculoDTOFull.class))).thenReturn(veiculoDTOFull);
        veiculoModelList.add(veiculoModel);
        List<VeiculoModel> response = veiculoService.findVeiculosByMarcaAnoCor("Honda",2020, "Preto");
        assertNotNull(response);
        assertEquals(veiculoModelList, response);
        assertEquals(veiculoModelList.size(), response.size());
    }
}