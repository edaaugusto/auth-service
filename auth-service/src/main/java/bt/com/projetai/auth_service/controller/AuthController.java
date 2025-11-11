package bt.com.projetai.auth_service.controller;

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

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        TokenDTO token = authService.login(loginDTO);

        // --- LOG DE SUCESSO NO LOGIN ---
        System.out.println("***************** LOGIN REALIZADO: " + loginDTO.email()
        + "*****************");

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterDTO registerDTO) {
        authService.register(registerDTO);

        // --- LOG DE SUCESSO NO REGISTRO ---
        System.out.println("***************** NOVO USUÁRIO REGISTRADO: " + registerDTO.email() + "*****************");

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}