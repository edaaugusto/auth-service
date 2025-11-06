# 🚀 Serviço de Autenticação (`auth-service`)

Este é o microserviço responsável pela autenticação e autorização de usuários (registro, login e geração de token JWT) para o ecossistema Projetaí.

## 🛠️ Tecnologias Utilizadas

* Java 17
* Spring Boot (3.5.7+)
* Spring Security
* Spring Data JPA
* MySQL
* JSON Web Token (JWT) - via `com.auth0:java-jwt`

## ⚙️ Configuração Obrigatória (Importante!)

Este projeto não contém senhas de banco de dados ou chaves secretas no código. Para executar o serviço localmente, você **precisa** configurar suas próprias credenciais.

1.  **Crie um Banco MySQL:** Primeiro, crie um banco de dados vazio no seu MySQL local. (ex: `CREATE DATABASE projeteidb;`)

2.  **Configure o `application.properties`:** Abra o arquivo localizado em `src/main/resources/application.properties`.

3.  **Atualize as seguintes linhas** com as suas credenciais locais:

    ```properties
    # Altere para o seu usuário (ex: root) e sua senha local do MySQL
    spring.datasource.username=SEU_USUARIO_AQUI
    spring.datasource.password=SUA_SENHA_AQUI

    # Garanta que o nome do banco de dados (ex: projeteidb) está correto na URL
    spring.datasource.url=jdbc:mysql://localhost:3306/projeteidb

    # Altere esta chave para uma frase secreta longa e aleatória
    # Ela é usada para assinar os tokens JWT
    api.jwt.secret=SUA_PROPRIA_CHAVE_SECRETA_LONGA_E_ALEATORIA_AQUI
    ```

## 🏃 Como Executar

1.  Clone este repositório.
2.  Configure seu `application.properties` (conforme o passo acima).
3.  Execute a classe principal `AuthServiceApplication.java` através da sua IDE.
4.  O serviço estará rodando em `http://localhost:8080`.



## 🔑 Endpoints da API

Use uma ferramenta como o **Postman** ou **Insomnia** para testar os endpoints:

* **Registrar (POST):** `http://localhost:8080/api/auth/register`
* **Login (POST):** `http://localhost:8080/api/auth/login`

opa