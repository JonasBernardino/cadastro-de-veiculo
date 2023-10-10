//package com.cadastro.config.loca;
//
//import com.cadastro.veiculo.model.VeiculoModel;
//import com.cadastro.veiculo.repositories.VeiculoRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//import java.util.Date;
//import java.util.List;
//
//@Configuration
//@Profile("local")
//public class LocalConfig {
//
//    @Autowired
//    private VeiculoRepository veiculoRepository;
//
//    @Bean
//    public void startBD(){
//        VeiculoModel v1 = new VeiculoModel(null,"Moto", "Honda", 2021, "Descrição 2", "Vermelha", true, new Date(), new Date());
//        VeiculoModel v2 = new VeiculoModel(null, "Carro", "Ford", 2020, "Descrição 4", "Preto", true, new Date(), new Date());
//
//        this.veiculoRepository.saveAll(List.of(v1, v2));
//
//    }
//}
