package br.com.fiap.dindin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.dindin.models.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    
}