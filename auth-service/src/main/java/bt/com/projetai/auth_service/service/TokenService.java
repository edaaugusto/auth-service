//AQUI QUEM GERA E VALIDA O TOKEN JWT
package bt.com.projetai.auth_service.service;

import bt.com.projetai.auth_service.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service // 1. Diz ao Spring que esta é uma classe de serviço (um "trabalhador")
public class TokenService {

    // 2. Pega a "chave-mestra" do application.properties
    @Value("${api.jwt.secret}")
    private String secret;

    private static final String ISSUER = "Projetei-Auth-API"; // O nome do "emissor" do crachá

    /**
     * Gera um novo token JWT para um usuário autenticado.
     * Este é o "Criador de Crachás".
     */
    public String generateToken(User user) {
        try {
            // 3. Prepara o "carimbo" com nossa chave secreta
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // 4. Cria o crachá
            String token = JWT.create()
                    .withIssuer(ISSUER) // Emissor: "Projetei"
                    .withSubject(user.getEmail()) // Dono do Crachá: "usuario@email.com"
                    .withExpiresAt(getExpirationDate()) // Data de Validade: (ex: 2 horas)
                    .sign(algorithm); // Carimba e assina com a chave secreta
            return token;

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    /**
     * Valida um token JWT e retorna o "dono" (o email) se for válido.
     * Este é o "Verificador de Crachás" (o segurança na porta).
     */
    public String validateToken(String token) {
        try {
            // 5. Prepara o "carimbo" com a *mesma* chave secreta
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // 6. Tenta verificar o crachá
            return JWT.require(algorithm)
                    .withIssuer(ISSUER) // Só aceita crachás emitidos por "Projetei"
                    .build()
                    .verify(token) // Verifica a assinatura (se é falso) e a validade (se expirou)
                    .getSubject(); // Se tudo deu certo, retorna o email do dono

        } catch (JWTVerificationException exception) {
            // Se a verificação falhar (token expirado, assinatura inválida, etc.),
            // retorna vazio, indicando que o usuário NÃO está autenticado.
            return "";
        }
    }

    // Método privado para calcular a data de expiração (2 horas a partir de agora)
    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}