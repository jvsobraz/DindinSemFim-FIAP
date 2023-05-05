package br.com.fiap.dindin.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.dindin.models.Conta;
import br.com.fiap.dindin.models.Despesa;
import br.com.fiap.dindin.models.Usuario;
import br.com.fiap.dindin.repository.ContaRepository;
import br.com.fiap.dindin.repository.DespesaRepository;
import br.com.fiap.dindin.repository.UsuarioRepository;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    DespesaRepository despesaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        Conta c1 = new Conta(1L, "itau", new BigDecimal(100), "money", true);
        Conta c2 = new Conta(2L, "bradesco", new BigDecimal(56), "money", true);
        Conta c3 = new Conta(3L, "carteira", new BigDecimal(3), "coin", true);
        contaRepository.saveAll(List.of(c1,c2,c3));

        despesaRepository.saveAll(List.of(
            Despesa.builder().valor(new BigDecimal(34)).descricao("cinema").data(LocalDate.now()).conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(54)).descricao("aluguel").data(LocalDate.now()).conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(76)).descricao("netflix").data(LocalDate.now()).conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(12)).descricao("estacionamento").data(LocalDate.now()).conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(54)).descricao("imposto").data(LocalDate.now()).conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(55)).descricao("ifood").data(LocalDate.now()).conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(100)).descricao("restaurante").data(LocalDate.now()).conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(78)).descricao("cinema").data(LocalDate.now()).conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(87)).descricao("internet").data(LocalDate.now()).conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(78)).descricao("tarifa").data(LocalDate.now()).conta(c1).build()
        ));

        usuarioRepository.save(Usuario.builder()
            .nome("Joao Carlos")
            .email("joao@fiap.com.br")
            .senha("$2a$12$pMH3uGhwRXAaEq21jmmqn.PzxykI/HJyVAXM6sIQlcQ/2emqevaWC")
            .build()
        );
       
    }
    
}