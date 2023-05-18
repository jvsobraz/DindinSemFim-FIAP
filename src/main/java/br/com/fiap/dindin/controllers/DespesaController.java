package br.com.fiap.dindin.controllers;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.com.fiap.dindin.repository.ContaRepository;
import br.com.fiap.dindin.repository.DespesaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/despesas")
@Slf4j
@SecurityRequirement(name = "bearer-key")
@Tag(name = "despesa")
public class DespesaController {

    @Autowired
    DespesaRepository despesaRepository;

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca, @ParameterObject @PageableDefault(size = 5) Pageable pageable){
        Page<Despesa> despesas = (busca == null)?
            despesaRepository.findAll(pageable):
            despesaRepository.findByDescricaoContaining(busca, pageable);
        
        return assembler.toModel(despesas.map(Despesa::toEntityModel));
    }

    @PostMapping
    @ApiResponses ({
        @ApiResponse(responseCode = "201", description = "Despesa cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Os campos enviados são inválidos")
    })
    public ResponseEntity<Object> create(@RequestBody @Valid Despesa despesa){
        log.info("cadastrando despesa " + despesa);
        despesaRepository.save(despesa);
        despesa.setConta(contaRepository.findById(despesa.getConta().getId()).get());
        return ResponseEntity.status(HttpStatus.CREATED).body(despesa.toEntityModel());
    }
    
    @GetMapping("{id}")
    @Operation(
        summary = "Detalhar despesa",
        description = "Endpoint que recebe um id e retorna os dados da despesa. O id deve ser ..."
        
    )
    public EntityModel<Despesa> show(@PathVariable Long id){
        log.info("detalhando despesa " + id);
        var despesa = despesaRepository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("despesa não encontrada"));

        return despesa.toEntityModel();
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
    public EntityModel<Despesa> update(@PathVariable Long id, @RequestBody @Valid Despesa despesa){
        log.info("atualizando despesa " + id);
        despesaRepository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("Erro ao apagar, despesa não encontrada"));

        despesa.setId(id);
        despesaRepository.save(despesa);

        return despesa.toEntityModel();
    }
    
}