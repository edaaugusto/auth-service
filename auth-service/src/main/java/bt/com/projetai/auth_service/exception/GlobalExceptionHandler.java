package bt.com.projetai.auth_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Diz ao Spring: "Eu cuido dos erros de todos os Controllers"
public class GlobalExceptionHandler {

    // Captura qualquer RuntimeException (como a do "email já em uso")
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        
        // 1. Imprime uma mensagem bonita no console (sem aquele textão gigante)
        System.out.println("***************** ERRO DE NEGÓCIO: " + ex.getMessage()+ "*****************");

        // 2. Retorna um erro 400 (Bad Request) para o cliente (Postman/Front) com a mensagem simples
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}