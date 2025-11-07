package bt.com.projetai.auth_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity // 1. Diz que isso vira uma tabela no banco
@Table(name = "users") // 2. O nome da tabela será "users"
@Data // 3. Lombok: cria getters/setters automaticamente
@NoArgsConstructor // 4. Lombok: cria construtor vazio
@AllArgsConstructor // 5. Lombok: cria construtor completo
public class User implements UserDetails { // 6. Integra com a segurança do Spring

    @Id // Diz que este campo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Diz para o banco gerar o ID automático (1, 2, 3...)
    private Long id;

    @Column(unique = true) // Não permite dois usuários com o mesmo email
    private String email;

    private String password;

    // --- Métodos Obrigatórios do Spring Security (UserDetails) ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Não vamos usar perfis de acesso por enquanto
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        // Avisa ao Spring Security que nosso "usuário" é o email
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Conta nunca expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Conta nunca bloqueia
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Senha nunca expira
    }

    @Override
    public boolean isEnabled() {
        return true; // Usuário sempre ativo
    }
}