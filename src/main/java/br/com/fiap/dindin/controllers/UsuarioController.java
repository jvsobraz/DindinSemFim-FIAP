package br.com.fiap.dindin.controllers;

import org.aspectj.apache.bcel.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.dindin.models.Usuario;
import br.com.fiap.dindin.repository.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
public class UsuarioController {
    
    @Autowired
    UsuarioRepository repository;

    @PostMapping("/api/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody @Valid Usuario usuario){
        repository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PostMapping("/api/login")
    public ResponseEntity<Object> login(@RequestBody @Valid Credencial credencial){
       var usuario = repository.findByEmailAndSenha(credencial.email(), credencial.senha());

       if (usuario.isEmpty()) 
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok().build();
        
    }

}
