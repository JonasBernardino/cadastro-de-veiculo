package com.cadastro.veiculo.controller;

import com.cadastro.exception.model.Response;
import com.cadastro.veiculo.dto.VeiculoDTOFull;
import com.cadastro.veiculo.dto.VeiculosTotalResponse;
import com.cadastro.veiculo.model.VeiculoModel;
import com.cadastro.veiculo.services.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/veiculo")
public class VeiculoController {

    private static final String ID = "/{id}";

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping(value = ID)
    public ResponseEntity<Response<VeiculoDTOFull>>  findByIdVeiculos(@PathVariable Long id){
        Response<VeiculoDTOFull> response = new Response<>();
        response.setData(veiculoService.findByIdVeiculo(id));
        response.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public Response<Page<VeiculoDTOFull>> findAllVeiculos(Pageable pageable) {
        Response<Page<VeiculoDTOFull>> response = new Response<>();
        response.setData(this.veiculoService.findAllVeiculos(pageable));
        return response;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Response<Boolean>> saveVeiculo(@Valid @RequestBody VeiculoDTOFull veiculoDTOFull){
        Response<Boolean> response = new Response<>();
        response.setData(this.veiculoService.saveVeiculo(veiculoDTOFull));
        response.setStatusCode(HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Response<Boolean>> updateVeiculo(@Valid @RequestBody VeiculoDTOFull veiculoDTOFull){
        Response<Boolean> response = new Response<>();
        response.setData(this.veiculoService.updateVeiculo(veiculoDTOFull));
        response.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<Response<Boolean>>  deleteVeiculo(@PathVariable Long id){
        Response<Boolean> response = new Response<>();
        response.setData(veiculoService.deleteVeiculo(id));
        response.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/vendidos")
    public ResponseEntity<Response<VeiculosTotalResponse>> findByNaoVendidos(){
        Response<VeiculosTotalResponse> response = new Response<>();
        response.setData(veiculoService.findVeiculosNaoVendidos());
        response.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/decada/{ano}")
    public ResponseEntity<Response<VeiculosTotalResponse>> findByPorDecada(@PathVariable Integer ano){
        Response<VeiculosTotalResponse> response = new Response<>();
        response.setData(veiculoService.findVeiculosPorDecada(ano));
        response.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/marca/{marca}")
    public ResponseEntity<Response<VeiculosTotalResponse>> findVeiculosPorMarca(@PathVariable String marca){
        Response<VeiculosTotalResponse> response = new Response<>();
        response.setData(veiculoService.findVeiculosPorMarca(marca));
        response.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/ultimaSemana")
    public ResponseEntity<Response<VeiculosTotalResponse>> findCarrosCriadosNaUltimaSemana(){
        Response<VeiculosTotalResponse> response = new Response<>();
        response.setData(veiculoService.findCarrosCriadosNaUltimaSemana());
        response.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/buscar")
    public ResponseEntity<Response<List<VeiculoModel>>> buscarPorMarcaAnoCor(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String cor){
        Response<List<VeiculoModel>> response = new Response<>();
        response.setData(veiculoService.findVeiculosByMarcaAnoCor(marca, ano, cor));
        response.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }




}
