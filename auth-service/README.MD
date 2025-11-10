# 🚀 Serviço de Autenticação (`auth-service`) - Ecossistema Projetaí

Este microserviço é a autoridade central de identidade para o ecossistema **Projetaí**. Ele é responsável por gerenciar o ciclo de vida de autenticação (registro, login) e autorização via tokens JWT (JSON Web Token).

O serviço foi projetado para ser **stateless** (sem sessão no servidor) e facilmente integrável com diferentes front-ends (Web, Mobile) e outros microserviços.

---

## 🛠️ Tecnologias Principais

* **Java 17** (LTS)
* **Spring Boot 3.x**
* **Spring Security 6** (Autenticação e Criptografia BCrypt)
* **JWT (Auth0 java-jwt)** (Geração e Validação de Tokens)
* **Spring Data JPA & Hibernate** (Persistência)
* **MySQL Driver**

---

## ⚡ Guia de Início Rápido (Desenvolvimento Local)

Siga estes passos para rodar o projeto na sua máquina pela primeira vez.

### 1. Pré-requisitos
* Java 17 instalado.
* MySQL rodando localmente.
* Maven (opcional, se usar o wrapper `./mvnw`).

### 2. Configuração do Banco Local
Crie um banco de dados vazio para desenvolvimento:
```sql
CREATE DATABASE projeteidb;
3. Configuração de Credenciais
O projeto não possui senhas comitadas. Crie ou edite o arquivo src/main/resources/application.properties com suas credenciais locais:

Properties

# --- Configuração Local (DEV) ---
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/projeteidb
spring.datasource.username=SEU_USER_LOCAL  # ex: root
spring.datasource.password=SUA_SENHA_LOCAL # ex: 123456

# Em DEV, 'update' cria as tabelas automaticamente. NUNCA usar em produção.
spring.jpa.hibernate.ddl-auto=update

# Chave para assinar tokens (use qualquer string longa para testes locais)
api.jwt.secret=CHAVE_SECRETA_DESENVOLVIMENTO_123
4. Executar
Rode a classe principal AuthServiceApplication.java via IDE ou terminal:

Bash

./mvnw spring-boot:run

🔌 Guia de Integração (Produção / Projeto Raiz)
ATENÇÃO EQUIPE: Ao integrar este serviço ao ambiente oficial do Projetaí, as seguintes configurações são OBRIGATÓRIAS.

1. Conexão com Banco de Dados Existente (Legado)
O projeto raiz já possui banco de dados. Para evitar perda de dados ou alterações indesejadas no esquema oficial:

REGRA DE OURO: No application.properties de produção, a configuração DEVE ser:

Properties

# 'validate' apenas checa se a tabela existe, mas NÃO altera nada.
spring.jpa.hibernate.ddl-auto=validate
Mapeamento de Tabela: Se a tabela de usuários no banco raiz tiver outro nome (ex: TB_USUARIO_SISTEMA em vez de users), NÃO altere o código Java. Use anotações na classe model/User.java:

Java

@Entity
@Table(name = "TB_USUARIO_SISTEMA") // Nome real no banco legado
public class User implements UserDetails {
    @Column(name = "DS_EMAIL_LOGIN") // Nome real da coluna
    private String email;
    // ...
}


2. Integração com Front-end (CORS)
Para que o Front-end (React, Angular, Vue, etc.) consiga fazer requisições para este serviço, é necessário liberar a origem dele no CORS.

Vá até a classe config/SecurityConfig.java.

Localize o método corsConfigurationSource().

Adicione a URL de produção do front-end à lista de origens permitidas:

Java

configuration.setAllowedOrigins(List.of(
    "http://localhost:3000",      // Front em Dev local
    "http://localhost:5500",      // Front em Dev local (alternativo)
    "[https://www.projetai.com.br](https://www.projetai.com.br)" // URL OFICIAL DE PRODUÇÃO DO FRONT (Adicionar)
));
3. Variáveis de Ambiente (Segurança)
Em produção, NÃO coloque senhas reais no application.properties. Utilize variáveis de ambiente do servidor ou do container (Docker):

DB_HOST: URL do banco de produção.

DB_USER: Usuário do banco.

DB_PASS: Senha do banco.

JWT_SECRET: A chave mestra real (deve ser longa, complexa e mantida em segredo absoluto).

🔑 Documentação da API (Endpoints)
1. Registrar Novo Usuário
Cria um novo login no sistema.

URL: POST /api/auth/register

Body (JSON):

JSON

{
  "email": "novo.usuario@projetai.com",
  "password": "senhaSegura123"
}
Respostas:

201 Created: Sucesso.

400 Bad Request: Email já existente ou dados inválidos.

2. Login (Autenticação)
Verifica credenciais e retorna o token de acesso.

URL: POST /api/auth/login

Body (JSON):

JSON

{
  "email": "usuario@projetai.com",
  "password": "senhaSegura123"
}
Resposta de Sucesso (200 OK):

JSON

{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 3600
}
Uso do Token: Nas requisições para outros serviços, envie o header: Authorization: Bearer <seu_token_aqui>