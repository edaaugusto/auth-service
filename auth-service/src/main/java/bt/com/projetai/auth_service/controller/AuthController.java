package bt.com.projetai.auth_service.controller; // <- Ajuste se seu pacote for diferente!

import bt.com.projetai.auth_service.dto.LoginDTO;
import bt.com.projetai.auth_service.dto.RegisterDTO;
import bt.com.projetai.auth_service.dto.TokenDTO;
import bt.com.projetai.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 1. Diz ao Spring: "Esta classe controla URLs da API"
@RequestMapping("/api/auth") // 2. Define o endereço base: http://localhost:8080/api/auth
public class AuthController {

    @Autowired
    private AuthService authService; // Injeta nosso Gerente

    // --- PORTA DE LOGIN ---
    // Endereço final: POST http://localhost:8080/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        // Chama o gerente para tentar logar
        TokenDTO token = authService.login(loginDTO);
        // Retorna 200 OK com o token no corpo da resposta
        return ResponseEntity.ok(token);
    }

    // --- PORTA DE REGISTRO ---
    // Endereço final: POST http://localhost:8080/api/auth/register
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterDTO registerDTO) {
        // Chama o gerente para registrar
        authService.register(registerDTO);
        // Retorna 201 CREATED (sem corpo)
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}