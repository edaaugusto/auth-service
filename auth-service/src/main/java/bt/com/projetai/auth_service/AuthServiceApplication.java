package bt.com.projetai.auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        // 1. Ligamos a aplicação e guardamos a referência dela na variável 'app'
        var app = SpringApplication.run(AuthServiceApplication.class, args);

        // 2. Pegamos o "Environment" (o ambiente onde ela está rodando)
        Environment env = app.getEnvironment();

        // 3. Descobrimos qual porta ela usou (lendo do application.properties)
        String port = env.getProperty("server.port");

        // 4. Imprimimos a mensagem bonita no console
        System.out.println("\n----------------------------------------------------------");
        System.out.println("✅ Aplicação 'auth-service' rodando! Acesso: http://localhost:" + port);
        System.out.println("📝 Swagger UI (se tiver): http://localhost:" + port + "/swagger-ui.html");
        System.out.println("----------------------------------------------------------\n");
    }
}