package bt.com.projetai.auth_service.service;

import bt.com.projetai.auth_service.dto.LoginDTO;
import bt.com.projetai.auth_service.dto.RegisterDTO;
import bt.com.projetai.auth_service.dto.TokenDTO;
import bt.com.projetai.auth_service.model.User;
import bt.com.projetai.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    // --- MÉTODO DE REGISTRO ---
    public void register(RegisterDTO dto) {
        // 1. Verifica se o email já existe
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Este email já está em uso!");
        }

        // 2. Criptografa a senha (TRANSFORMA "123456" EM "$2a$10$EixZaY...")
        String encryptedPassword = passwordEncoder.encode(dto.password());

        // 3. Cria o novo usuário com a senha criptografada
        User newUser = new User();
        newUser.setEmail(dto.email());
        newUser.setPassword(encryptedPassword);

        // 4. Salva no banco
        userRepository.save(newUser);
    }

    // --- MÉTODO DE LOGIN ---
    public TokenDTO login(LoginDTO dto) {
        // 1. Cria um "pedido de login" com o email e senha digitados
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());

        // 2. Pede para o AuthenticationManager verificar (ele usa o AuthorizationService e o PasswordEncoder por baixo dos panos)
        var auth = authenticationManager.authenticate(usernamePassword);

        // 3. Se chegou aqui, o login foi um sucesso! Pegamos o usuário autenticado.
        var user = (User) auth.getPrincipal();

        // 4. Geramos o crachá (Token) para ele
        String token = tokenService.generateToken(user);

        // 5. Retornamos o crachá
        return new TokenDTO(token);
    }
}