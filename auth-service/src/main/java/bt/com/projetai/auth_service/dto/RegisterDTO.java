package bt.com.projetai.auth_service.dto;

// Podemos adicionar mais campos no futuro (ex: nome, telefone)
public record RegisterDTO(String email, String password) {
}