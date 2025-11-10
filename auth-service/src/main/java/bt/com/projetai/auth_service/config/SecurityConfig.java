package bt.com.projetai.auth_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println(">>> INICIANDO CONFIGURAÇÃO DE SEGURANÇA <<<");
        try {
        SecurityFilterChain filterChain = http
                .csrf(csrf -> csrf.disable()) // Desabilita proteção desnecessária para APIs
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // NÃO guarda sessão (usa Token)
                .authorizeHttpRequests(authorize -> authorize
                    // Libera qualquer coisa que comece com "/api/auth/"
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated() // Todas as outras portas: TRANCADAS (exigem token)
                )
                .build();

            System.out.println(">>> CONFIGURAÇÃO DE SEGURANÇA CONCLUÍDA COM SUCESSO <<<"); // ✅ Aqui também!
            return filterChain;
    
    } catch (Exception e) {
        System.out.println(">>> ERRO NA CONFIGURAÇÃO DE SEGURANÇA: " + e.getMessage()); // ✅ Útil para ver o erro
        throw e; // Relança o erro para o Spring saber que falhou
    }}

    // 2. O Gerente de Autenticação (Quem sabe fazer o login acontecer)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();
    }
    // 3. A Ferramenta de Criptografia (Para não salvar senhas em texto puro)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
