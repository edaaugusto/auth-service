//AQUI É AONDE AUTORIZA, ONDE FAZ PUXA O DADO DO BANCO
package bt.com.projetai.auth_service.service;

import bt.com.projetai.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Este é o método que o Spring Security chama automaticamente
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Como definimos que nosso "username" é o email, buscamos por email
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com este email"));
    }
}