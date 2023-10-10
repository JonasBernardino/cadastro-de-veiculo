package com.cadastro.veiculo.repositories;

import com.cadastro.veiculo.model.VeiculoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<VeiculoModel, Long> {

    @Query(value = "FROM VeiculoModel v WHERE v.vendido = false")
    List<VeiculoModel> findVeiculosNaoVendidos();

    @Query(value = "SELECT COUNT(v) FROM VeiculoModel v WHERE v.vendido = false")
    Integer countVeiculosNaoVendidos();

    @Query("SELECT v FROM VeiculoModel v WHERE v.ano >= :anoInicio AND v.ano <= :anoFim")
    List<VeiculoModel> findVeiculosByDecada(@Param("anoInicio") Integer anoInicio, @Param("anoFim") Integer anoFim);

    List<VeiculoModel> findByMarca(@Param("marca") String marca);

    @Query("SELECT v FROM VeiculoModel v WHERE v.criado >= CURRENT_DATE - 7")
    List<VeiculoModel> findCarrosCriadosNaUltimaSemana();

    @Query("SELECT v FROM VeiculoModel v WHERE 1 = 1 AND" +
            "(:marca IS NULL OR v.marca = :marca) " +
            "AND (:ano IS NULL OR v.ano = :ano) " +
            "AND (:cor IS NULL OR v.cor = :cor)")
    List<VeiculoModel> findVeiculosByMarcaAnoCor(
            @Param("marca") String marca,
            @Param("ano") Integer ano,
            @Param("cor") String cor);
}