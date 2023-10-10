package br.com.fiap.dindin.controllers;

//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.dindin.exceptions.RestNotFoundException;
import br.com.fiap.dindin.models.Conta;
//import br.com.fiap.dindin.models.Despesa;
//import br.com.fiap.dindin.models.RestValidationError;
import br.com.fiap.dindin.repository.ContaRepository;
//import br.com.fiap.dindin.repository.DespesaRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ContaRepository repository;

    @GetMapping
    public List<Conta> index(){
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Conta> create(@RequestBody @Valid Conta conta){
        log.info("cadastrando conta " + conta);
        repository.save(conta);
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Conta> show(@PathVariable Long id){
        log.info("detalhando conta " + id);
        return ResponseEntity.ok(getConta(id));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Conta> destroy(@PathVariable Long id){
        log.info("apagando conta " + id);
        getConta(id);
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Conta> update(@PathVariable Long id, @RequestBody @Valid Conta conta){
        log.info("atualizando conta " + id);
        getConta(id);
        conta.setId(id);
        repository.save(conta);
        return ResponseEntity.ok(conta);
    }

    private Conta getConta(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("conta não encontrada"));
    }
    
}