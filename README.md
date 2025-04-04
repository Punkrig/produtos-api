# API CRUD de Produtos com Spring Boot, PostgreSQL e JWT

Esta aplicação é uma API RESTful para gestão de produtos, implementando operações CRUD (Create, Read, Update, Delete) com autenticação JWT (JSON Web Token), desenvolvida com Spring Boot e PostgreSQL.

## Tecnologias Utilizadas

- **Spring Boot 3.2.0**: Framework Java para desenvolvimento de aplicações
- **Spring Security**: Módulo para autenticação e autorização
- **Spring Data JPA**: Simplificação do acesso a dados com JPA
- **JWT (JSON Web Token)**: Para autenticação stateless
- **PostgreSQL**: Banco de dados relacional
- **Maven**: Gerenciamento de dependências e build
- **Lombok**: Redução de código boilerplate

## Funcionalidades

### Autenticação e Usuários
- Registro de novos usuários
- Login com geração de token JWT
- Proteção de rotas com autenticação

### Produtos
- Listar todos os produtos
- Buscar produto por ID
- Buscar produtos por categoria
- Buscar produtos por termo no nome
- Criar novo produto
- Atualizar produto existente
- Excluir produto

## Estrutura do Projeto

```
src
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── produtosapi
│   │               ├── ProdutosApiApplication.java
│   │               ├── config
│   │               │   └── SecurityConfig.java
│   │               ├── controller
│   │               │   ├── AuthController.java
│   │               │   └── ProdutoController.java
│   │               ├── dto
│   │               │   ├── AuthRequest.java
│   │               │   ├── AuthResponse.java
│   │               │   └── ProdutoDTO.java
│   │               ├── exception
│   │               │   └── ResourceNotFoundException.java
│   │               ├── model
│   │               │   ├── Produto.java
│   │               │   └── Usuario.java
│   │               ├── repository
│   │               │   ├── ProdutoRepository.java
│   │               │   └── UsuarioRepository.java
│   │               ├── security
│   │               │   ├── JwtAuthenticationFilter.java
│   │               │   └── JwtTokenProvider.java
│   │               └── service
│   │                   ├── ProdutoService.java
│   │                   └── UsuarioService.java
│   └── resources
│       └── application.properties
```

## Configuração e Execução

### Pré-requisitos
- JDK 17+
- Maven
- PostgreSQL

### Configuração do Banco de Dados
1. Crie um banco de dados PostgreSQL:
```sql
CREATE DATABASE produtos_db;
```

2. Configure as credenciais no arquivo `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/produtos_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

A aplicação estará disponível em `http://localhost:8080`

## Endpoints da API

### Autenticação

#### Registrar Usuário
```
POST /api/auth/register
Content-Type: application/json

{
  "username": "usuario",
  "password": "senha",
  "nome": "Nome Completo",
  "role": "USER"
}
```

#### Login
```
POST /api/auth/login
Content-Type: application/json

{
  "username": "usuario",
  "password": "senha"
}
```
Resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "username": "usuario",
  "nome": "Nome Completo"
}
```

### Produtos

> Todas as requisições abaixo requerem o header de autenticação:
> `Authorization: Bearer seu_token_jwt`

#### Listar Todos os Produtos
```
GET /api/produtos
```

#### Buscar Produto por ID
```
GET /api/produtos/{id}
```

#### Buscar Produtos por Categoria
```
GET /api/produtos/categoria/{categoria}
```

#### Buscar Produtos por Nome
```
GET /api/produtos/buscar?nome=termo
```

#### Criar Produto
```
POST /api/produtos
Content-Type: application/json

{
  "nome": "Produto XYZ",
  "descricao": "Descrição detalhada do produto",
  "preco": 99.90,
  "quantidade": 50,
  "categoria": "Categoria"
}
```

#### Atualizar Produto
```
PUT /api/produtos/{id}
Content-Type: application/json

{
  "nome": "Produto XYZ Atualizado",
  "descricao": "Nova descrição",
  "preco": 109.90,
  "quantidade": 45,
  "categoria": "Categoria"
}
```

#### Excluir Produto
```
DELETE /api/produtos/{id}
```

## Segurança

- Senhas são armazenadas com criptografia BCrypt
- Autenticação stateless com JWT
- Token JWT expira após 24 horas (configurável)
- Endpoints de autenticação são públicos, demais endpoints requerem autenticação

### Usuário Padrão

Na primeira execução, um usuário administrador padrão é criado:
- Username: `admin`
- Senha: `admin123`


## Licença

[MIT](LICENSE)
