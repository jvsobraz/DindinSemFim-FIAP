package br.com.fiap.dindin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.dindin.models.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    // @Query("SELECT d FROM Despesa d WHERE d.descricao LIKE %?1%") //JPQL
    Page<Despesa> findByDescricaoContaining(String busca, Pageable pageable);

    // @Query("SELECT d FROM Despesa d ORDER BY d.id LIMIT ?1 OFFSET ?2")
    // List<Despesa> buscarPaginado(int tamanho, int offset);
    
}