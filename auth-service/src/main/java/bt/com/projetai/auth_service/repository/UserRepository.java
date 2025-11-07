package bt.com.projetai.auth_service.repository;

import bt.com.projetai.auth_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Diz ao Spring: "Isto é um repositório (arquivista)"
public interface UserRepository extends JpaRepository<User, Long> {
    // Ao estender JpaRepository, ganhamos de graça métodos como:
    // save(), findById(), findAll(), delete(), etc.

    // Mágica do Spring Data JPA:
    // Apenas declarando este método, o Spring vai gerar o SQL automaticamente
    // para buscar um usuário pelo campo 'email'.
    // SQL gerado: SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);
}