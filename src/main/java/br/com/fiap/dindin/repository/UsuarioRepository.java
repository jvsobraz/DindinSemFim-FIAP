package br.com.fiap.dindin.repository;

public class UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    void findByEmailAndSenha(String email, String senha);
}
