package br.com.fiap.dindin.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.dindin.exceptions.RestNotFoundException;
import br.com.fiap.dindin.models.Despesa;
import br.com.fiap.dindin.models.RestValidationError;
import br.com.fiap.dindin.repository.ContaRepository;
import br.com.fiap.dindin.repository.DespesaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/despesas")
@Slf4j
public class DespesaController {

    @Autowired
    DespesaRepository despesaRepository;

    @Autowired
    ContaRepository contaRepository;

    @GetMapping
    public Page<Despesa> index(@RequestParam(required = false) String busca, @PageableDefault(size = 5) Pageable pageable){
        if (busca == null) return despesaRepository.findAll(pageable);
        return despesaRepository.findByDescricaoContaining(busca, pageable);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid Despesa despesa){
        log.info("cadastrando despesa " + despesa);
        despesaRepository.save(despesa);
        despesa.setConta(contaRepository.findById(despesa.getConta().getId()).get());
        return ResponseEntity.status(HttpStatus.CREATED).body(despesa);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Despesa> show(@PathVariable Long id){
        log.info("detalhando despesa " + id);
        var despesa = despesaRepository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("despesa não encontrada"));

        return ResponseEntity.ok(despesa);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Despesa> destroy(@PathVariable Long id){
        log.info("apagando despesa " + id);
        var despesa = despesaRepository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("Erro ao apagar, despesa não encontrada"));

        despesaRepository.delete(despesa);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Despesa> update(@PathVariable Long id, @RequestBody @Valid Despesa despesa){
        log.info("atualizando despesa " + id);
        despesaRepository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("Erro ao apagar, despesa não encontrada"));

        despesa.setId(id);
        despesaRepository.save(despesa);

        return ResponseEntity.ok(despesa);
    }
    
}