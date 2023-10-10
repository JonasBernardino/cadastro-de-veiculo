package com.cadastro.veiculo.services;

import com.cadastro.veiculo.dto.VeiculoDTOFull;
import com.cadastro.veiculo.dto.VeiculosTotalResponse;
import com.cadastro.veiculo.model.VeiculoModel;
import com.cadastro.veiculo.repositories.VeiculoRepository;
import com.cadastro.veiculo.veiculoException.VeiculoException;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Page<VeiculoDTOFull> findAllVeiculos(Pageable pageable){
        try {
            Page<VeiculoModel> veiculoModels = this.veiculoRepository.findAll(pageable);
            return veiculoModels.map(veiculoModel -> modelMapper.map(veiculoModel, VeiculoDTOFull.class));
        }catch (Exception e){
            throw new VeiculoException("Error interno identificado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public VeiculoDTOFull findByIdVeiculo(Long id) {
        Optional<VeiculoModel> optionalVeiculo = this.veiculoRepository.findById(id);
        if (optionalVeiculo.isPresent()) {
            VeiculoModel veiculoModel = optionalVeiculo.get();
            return modelMapper.map(veiculoModel, VeiculoDTOFull.class);
        } else {
            throw new VeiculoException("Veiculo não encontrado", HttpStatus.NOT_FOUND);
        }
    }


    public Boolean saveVeiculo(VeiculoDTOFull veiculoDTOFull) {
        try {
            fromVeiculo(veiculoDTOFull);
            return true;
        } catch (DataIntegrityViolationException m) {
            throw new VeiculoException("Error", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new VeiculoException("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Boolean updateVeiculo(VeiculoDTOFull veiculoDTOFull){
        VeiculoDTOFull existingVeiculo = findByIdVeiculo(veiculoDTOFull.getId());
        preUpdate(veiculoDTOFull);
        return true;
    }

    public Boolean deleteVeiculo(Long id){
        this.findByIdVeiculo(id);
        this.veiculoRepository.deleteById(id);
        return true;
    }

    public VeiculosTotalResponse findVeiculosNaoVendidos() {
        VeiculosTotalResponse response = new VeiculosTotalResponse();
        response.setVeiculos(this.veiculoRepository.findVeiculosNaoVendidos());
        response.setTotalVeiculos(this.veiculoRepository.countVeiculosNaoVendidos());
        return response;
    }

    public VeiculosTotalResponse findVeiculosPorDecada(Integer ano) {
        int decadaInicio = (ano / 10) * 10;
        int decadaFim = decadaInicio + 9;
        VeiculosTotalResponse response = new VeiculosTotalResponse();
        List<VeiculoModel> veiculoModelList = this.veiculoRepository.findVeiculosByDecada(decadaInicio, decadaFim);
        response.setVeiculos(veiculoModelList);
        response.setTotalVeiculos(veiculoModelList.size());
        return response;
    }

    public VeiculosTotalResponse findVeiculosPorMarca(String marca){
        VeiculosTotalResponse response = new VeiculosTotalResponse();
        List<VeiculoModel> veiculoModelList = this.veiculoRepository.findByMarca(marca);
        response.setVeiculos(veiculoModelList);
        response.setTotalVeiculos(veiculoModelList.size());
        return response;
    }

    public VeiculosTotalResponse findCarrosCriadosNaUltimaSemana(){
        VeiculosTotalResponse response = new VeiculosTotalResponse();
        List<VeiculoModel> veiculoModelList = this.veiculoRepository.findCarrosCriadosNaUltimaSemana();
        response.setVeiculos(veiculoModelList);
        response.setTotalVeiculos(veiculoModelList.size());
        return response;
    }

    public List<VeiculoModel> findVeiculosByMarcaAnoCor(String marca, Integer ano, String cor){
        return this.veiculoRepository.findVeiculosByMarcaAnoCor(marca, ano, cor);
    }


    public void preUpdate(VeiculoDTOFull veiculoDTOFull) {
        VeiculoDTOFull obj = findByIdVeiculo(veiculoDTOFull.getId());
        VeiculoDTOFull newOjb = new VeiculoDTOFull();
        newOjb.setId(obj.getId());
        newOjb.setVeiculo(veiculoDTOFull.getVeiculo());
        newOjb.setMarca(veiculoDTOFull.getMarca());
        newOjb.setAno(veiculoDTOFull.getAno());
        newOjb.setDescricao(veiculoDTOFull.getDescricao());
        newOjb.setCor(veiculoDTOFull.getCor());
        newOjb.setVendido(veiculoDTOFull.getVendido());
        newOjb.setCriado(obj.getCriado());
        newOjb.setAtualizado(veiculoDTOFull.getAtualizado());
        this.fromVeiculo(newOjb);
    }



    public void fromVeiculo(VeiculoDTOFull veiculoDTOFull) {
        VeiculoModel veiculoModel = modelMapper.map(veiculoDTOFull, VeiculoModel.class);
        this.veiculoRepository.save(veiculoModel);
    }

}
