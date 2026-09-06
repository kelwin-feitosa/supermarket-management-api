<div align="center">

# 🛒 Supermarket Management API

### API REST para gerenciamento de supermercado

**Java 21 · Spring Boot 4 · PostgreSQL · JPA · Docker**

<br>

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

[![Tests](https://img.shields.io/badge/Tests-75%20passing-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/junit5/)
[![JaCoCo](https://img.shields.io/badge/JaCoCo-Coverage-EF2D5E?style=for-the-badge)](https://www.jacoco.org/jacoco/)
[![SonarCloud](https://img.shields.io/badge/SonarCloud-Analysis-F3702A?style=for-the-badge&logo=sonarcloud&logoColor=white)](https://sonarcloud.io/)
[![CI](https://img.shields.io/badge/CI-GitHub%20Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)](https://github.com/features/actions)

</div>

---

## 📌 Sobre o projeto

O **Supermarket Management API** é uma API REST desenvolvida com **Java 21** e **Spring Boot 4**, criada para simular o backend de um sistema de gerenciamento de supermercado.

O projeto foi desenvolvido com foco em práticas utilizadas no desenvolvimento backend, incluindo **arquitetura em camadas, separação de responsabilidades, regras de negócio, persistência relacional, segurança, testes automatizados, documentação de API e integração contínua**.

A aplicação possui módulos para gerenciamento de produtos, categorias, usuários, fornecedores, compras, vendas e carrinho de compras.

---

## 🎯 Objetivo

O objetivo do projeto é aplicar conceitos de desenvolvimento backend que vão além de operações CRUD simples.

Entre os principais conceitos praticados estão:

- Arquitetura em camadas
- Separação de responsabilidades
- Programação Orientada a Objetos
- Spring Data JPA
- Hibernate
- Specifications e filtros dinâmicos
- Paginação e ordenação
- DTOs
- Mappers
- Validação de dados
- Regras de negócio
- Tratamento global de exceções
- Testes unitários
- Testes de persistência
- Migrações de banco de dados
- Autenticação com JWT
- Criptografia de senhas com BCrypt
- Autorização baseada em roles
- Docker
- GitHub Actions
- JaCoCo
- SonarCloud
- OpenAPI / Swagger

---

# 🏗️ Arquitetura

A aplicação utiliza uma arquitetura em camadas, mantendo responsabilidades separadas:

```text
                    HTTP Request
                         │
                         ▼
                 ┌──────────────┐
                 │  Controller  │
                 └──────┬───────┘
                        │
                        ▼
                 ┌──────────────┐
                 │   Service    │
                 └──────┬───────┘
                        │
                        ▼
                 ┌──────────────┐
                 │  Repository  │
                 └──────┬───────┘
                        │
                        ▼
                  ┌───────────┐
                  │ PostgreSQL│
                  └───────────┘
```

Além dessas camadas, o projeto utiliza componentes específicos para responsabilidades complementares:

```text
Controller
    │
    ├── DTOs
    │
    ├── Validation
    │
    └── Swagger / OpenAPI
         │
         ▼
      Service
         │
         ├── Business Rules
         ├── Mappers
         └── Specifications
              │
              ▼
          Repository
              │
              ▼
          PostgreSQL
```

### Organização principal

```text
src
├── main
│   ├── java
│   │   └── com.exemplo.meu_primeiro_projeto
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       │   ├── filter
│   │       │   ├── request
│   │       │   └── response
│   │       ├── exception
│   │       ├── mapper
│   │       ├── model
│   │       ├── repository
│   │       │   └── specification
│   │       ├── security
│   │       │   ├── filter
│   │       │   └── service
│   │       ├── service
│   │       └── util
│   │
│   └── resources
│       ├── db
│       │   └── migration
│       └── application.properties
│
└── test
    ├── java
    └── resources
        └── application-test.properties
```

---

# 🚀 Funcionalidades

### 📦 Produtos

- Cadastro de produtos
- Atualização
- Consulta por ID
- Listagem paginada
- Filtros dinâmicos
- Controle de estoque
- Validação de preços
- Associação com categorias

### 🏷️ Categorias

- Cadastro
- Atualização
- Consulta
- Listagem paginada
- Filtros
- Validação de duplicidade

### 👤 Usuários

- Cadastro
- Atualização
- Consulta
- Listagem
- Filtros
- Validação de dados
- Criação automática do carrinho
- Armazenamento seguro de senha com BCrypt
- Controle de perfil de acesso

### 🏭 Fornecedores

- Cadastro
- Atualização
- Consulta
- Listagem
- Filtros
- Controle de fornecedores ativos
- Validação de CNPJ

### 🛒 Carrinho

- Consulta do carrinho
- Adição de produtos
- Alteração de quantidade
- Remoção de itens
- Cálculo de subtotal
- Cálculo do valor total
- Validação de estoque

### 🧾 Vendas

- Realização de vendas
- Associação com usuários
- Associação com carrinhos
- Registro de itens
- Cálculo de valores
- Baixa automática de estoque
- Histórico de vendas
- Filtros por usuário e período

### 🚚 Compras

- Registro de compras
- Associação com fornecedores
- Registro de itens
- Atualização de estoque
- Histórico de compras
- Filtros por fornecedor e período

---

# 🔎 Filtros e paginação

A API utiliza **Spring Data JPA Specifications** para construir consultas dinâmicas.

Exemplo conceitual:

```text
GET /produtos?
    nome=arroz
    &categoriaId=1
    &precoMin=5
    &precoMax=30
    &estoqueMin=10
```

Os filtros são combinados dinamicamente, evitando a criação de diversos métodos específicos no Repository.

Também são utilizadas:

- Paginação
- Ordenação
- Specifications reutilizáveis
- Criteria API
- Composição de filtros

---

# 🧠 Regras de negócio

As regras de negócio ficam concentradas na camada de serviço.

Entre os principais comportamentos implementados estão:

- Verificação de duplicidade
- Validação de entidades relacionadas
- Validação de disponibilidade de estoque
- Controle de quantidade de produtos
- Atualização automática de estoque
- Cálculo de subtotais
- Cálculo de valores totais
- Validação de operações de compra e venda
- Criação automática de carrinho para usuários
- Validação de operações envolvendo categorias
- Controle de fornecedores ativos

---

# 🔐 Segurança

A API utiliza **Spring Security** para autenticação e autorização.

A autenticação é baseada em **JWT (JSON Web Token)** e a aplicação utiliza uma arquitetura **stateless**, sem armazenamento de sessão para autenticação.

### Fluxo de autenticação

```text
              POST /auth/login
                     │
                     ▼
              Email + Senha
                     │
                     ▼
          AuthenticationManager
                     │
                     ▼
          UsuarioDetailsService
                     │
                     ▼
             BCryptPasswordEncoder
                     │
                     ▼
             Usuário autenticado
                     │
                     ▼
                  JwtService
                     │
                     ▼
                JWT Token
```

Nas requisições protegidas, o token deve ser enviado através do header:

```http
Authorization: Bearer <token>
```

### Perfis de acesso

A aplicação possui os seguintes perfis:

```text
SYSTEM_ADMIN
      │
      ▼
   MANAGER
    │   │
    ▼   ▼
STOCK_MANAGER  CASHIER

CUSTOMER
```

A hierarquia de roles permite que perfis superiores herdem as permissões dos perfis inferiores.

O controle de acesso é aplicado através do **Spring Method Security** com `@PreAuthorize`.

### Senhas

As senhas dos usuários não são armazenadas em texto puro.

A aplicação utiliza **BCrypt** para realizar o hash das senhas antes da persistência.

### JWT Secret

A chave utilizada para assinar os tokens JWT não fica armazenada diretamente no código-fonte.

Ela é fornecida através da variável de ambiente:

```text
JWT_SECRET
```

Exemplo:

```bash
export JWT_SECRET='sua-chave-secreta-com-pelo-menos-32-bytes'
```

A configuração utiliza `@ConfigurationProperties` para carregar a propriedade de forma segura.

---

# ⚠️ Tratamento de exceções

A API possui tratamento global de exceções para manter respostas padronizadas.

Exemplo:

```json
{
  "mensagem": "Já existe uma categoria com esse nome.",
  "detalhes": "Já existe uma categoria com esse nome.",
  "timestamp": "2026-08-31T..."
}
```

Entre os cenários tratados estão:

- Recurso não encontrado
- Dados inválidos
- Regras de negócio violadas
- Conflitos de dados
- Erros de validação
- Falhas relacionadas à autenticação e autorização

---

# 🗄️ Banco de dados

O projeto utiliza **PostgreSQL 17** como banco de dados relacional principal.

O schema é controlado pelo **Flyway**, permitindo versionar alterações estruturais do banco.

```text
Migration
    │
    ▼
  Flyway
    │
    ▼
PostgreSQL
    │
    ▼
Hibernate / JPA
```

As migrations controlam a evolução do schema da aplicação.

Entre as principais entidades persistidas estão:

```text
usuario
categoria
fornecedor
produto
carrinho
item_carrinho
compra
item_compra
venda
item_venda
```

A evolução do domínio também é refletida nas migrations. Por exemplo, o modelo anteriormente baseado em `cliente` foi migrado para `usuario`, adicionando informações necessárias para autenticação e controle de acesso.

---

# 🧪 Testes

O projeto possui testes automatizados utilizando:

- JUnit 5
- Mockito
- Spring Boot Test
- Spring Data JPA Test
- PostgreSQL

Os testes cobrem principalmente:

- Services
- Regras de negócio
- Repositories
- Specifications
- Persistência
- Filtros
- Paginação

Status atual:

```text
Tests run: 75
Failures: 0
Errors:   0
Skipped:  0

BUILD SUCCESS
```

A suíte de testes é executada durante o processo de build através do Maven.

---

# 📊 Qualidade de código

### JaCoCo

O **JaCoCo** é utilizado para gerar relatórios de cobertura dos testes automatizados.

### SonarCloud

O **SonarCloud** é utilizado para análise estática do código e acompanhamento de:

- Bugs
- Vulnerabilidades
- Security Hotspots
- Code Smells
- Manutenibilidade
- Qualidade dos testes

O projeto utiliza o SonarCloud como parte do processo de melhoria contínua da qualidade do código.

---

# 🐳 Docker

O PostgreSQL pode ser executado utilizando **Docker Compose**, permitindo reproduzir a infraestrutura de banco de dados sem depender de uma instalação local específica.

```text
Docker Compose
      │
      ▼
PostgreSQL 17
      │
      ▼
Supermarket API
```

Na configuração local utilizada durante o desenvolvimento, o PostgreSQL é disponibilizado na porta:

```text
localhost:5433
```

A porta `5433` é utilizada para evitar conflito com uma instalação local do PostgreSQL na porta padrão `5432`.

---

# 🔄 Integração Contínua

O projeto possui **GitHub Actions** configurado para executar automaticamente o pipeline de validação.

```text
Push / Pull Request
        ↓
   GitHub Actions
        ↓
      Java 21
        ↓
       Maven
        ↓
    PostgreSQL
        ↓
      Tests
        ↓
     JaCoCo
        ↓
   SonarCloud
```

O pipeline verifica se a aplicação continua compilando, se os testes passam e se os relatórios necessários são gerados.

A chave secreta utilizada pelo JWT é fornecida ao ambiente de CI através dos **GitHub Actions Secrets**, evitando armazená-la no repositório.

---

# 📚 Documentação da API

A API utiliza **Swagger / OpenAPI** para documentação e exploração dos endpoints.

Após iniciar a aplicação:

### Swagger UI

```text
http://localhost:8080/swagger-ui.html
```

### OpenAPI

```text
http://localhost:8080/v3/api-docs
```

Os controllers possuem documentação dos endpoints, parâmetros e principais respostas HTTP.

O endpoint de autenticação também é documentado no Swagger:

```text
POST /auth/login
```

---

# 📡 Principais endpoints

| Recurso | Endpoint | Operações |
|---|---|---|
| 🔐 Autenticação | `/auth` | Login |
| 🏷️ Categorias | `/categorias` | CRUD + filtros |
| 🛍️ Produtos | `/produtos` | CRUD + filtros |
| 👤 Usuários | `/usuarios` | CRUD + filtros |
| 🏭 Fornecedores | `/fornecedores` | CRUD + filtros |
| 🛒 Carrinho | `/carrinhos` | Gerenciamento |
| 🚚 Compras | `/compras` | CRUD + filtros |
| 🧾 Vendas | `/vendas` | Operações + filtros |

> O acesso aos endpoints é controlado de acordo com o perfil do usuário autenticado.

---

# 🛠️ Tecnologias

<div align="center">

### ☕ Backend

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![Jakarta Validation](https://img.shields.io/badge/Jakarta%20Validation-59666C?style=for-the-badge)

### 🔐 Segurança

![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![BCrypt](https://img.shields.io/badge/BCrypt-Password%20Hashing-59666C?style=for-the-badge)

### 🗄️ Banco de Dados

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)

### 🧪 Testes e Qualidade

![JUnit](https://img.shields.io/badge/JUnit%205-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-78A641?style=for-the-badge)
![JaCoCo](https://img.shields.io/badge/JaCoCo-Coverage-EF2D5E?style=for-the-badge)
![SonarCloud](https://img.shields.io/badge/SonarCloud-F3702A?style=for-the-badge&logo=sonarcloud&logoColor=white)

### 📖 Documentação

![OpenAPI](https://img.shields.io/badge/OpenAPI-6BA539?style=for-the-badge&logo=openapiinitiative&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

### 🐳 Infraestrutura e Ferramentas

![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)

</div>

---

# ⚙️ Como executar

## Pré-requisitos

- Java 21
- Docker
- Docker Compose
- Git

## 1. Clone o projeto

```bash
git clone https://github.com/kelwin-feitosa/supermarket-management-api.git

cd supermarket-management-api
```

## 2. Configure o JWT Secret

A aplicação necessita da variável de ambiente `JWT_SECRET`.

Linux/macOS:

```bash
export JWT_SECRET='sua-chave-secreta-com-pelo-menos-32-bytes'
```

> Não coloque a chave JWT diretamente no código-fonte ou em arquivos versionados.

## 3. Inicie o PostgreSQL

```bash
docker compose up -d
```

O PostgreSQL será disponibilizado localmente na porta:

```text
5433
```

## 4. Execute a aplicação

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

A aplicação estará disponível em:

```text
http://localhost:8080
```

As migrations do Flyway serão executadas automaticamente durante a inicialização.

## 5. Acesse o Swagger

```text
http://localhost:8080/swagger-ui.html
```

## 6. Execute os testes

```bash
./mvnw clean verify
```

---

# 📁 Estrutura de domínio

```text
                    ┌─────────────┐
                    │  Categoria  │
                    └──────┬──────┘
                           │
                           ▼
                    ┌─────────────┐
                    │   Produto   │
                    └──────┬──────┘
                           │
              ┌────────────┴────────────┐
              ▼                         ▼
       ┌─────────────┐           ┌─────────────┐
       │  Carrinho   │           │    Venda    │
       └──────┬──────┘           └──────┬──────┘
              │                         │
              ▼                         ▼
       ItemCarrinho                ItemVenda
              │                         │
              └──────────┬──────────────┘
                         ▼
                      Produto


                    ┌─────────────┐
                    │ Fornecedor  │
                    └──────┬──────┘
                           │
                           ▼
                       ┌────────┐
                       │ Compra │
                       └────┬───┘
                            │
                            ▼
                        ItemCompra


                    ┌─────────────┐
                    │   Usuario   │
                    └──────┬──────┘
                           │
                    ┌──────┴──────┐
                    ▼             ▼
               ┌─────────┐    ┌─────────┐
               │ Carrinho│    │  Venda  │
               └─────────┘    └─────────┘
```

---

# 🚧 Próximos passos

O projeto continua em evolução.

### 🧪 Testes

- Testes dos Controllers com MockMvc
- Testes de integração mais abrangentes
- Testcontainers

### 📈 Qualidade e observabilidade

- Histórico detalhado de estoque
- Logs estruturados
- Melhorias de observabilidade
- Monitoramento da aplicação

### 🐳 Infraestrutura

- Dockerfile da aplicação
- Docker Compose completo para aplicação + banco
- Melhorias no pipeline de CI/CD
- Deploy em ambiente cloud

### 🏗️ Arquitetura

- Evolução das decisões arquiteturais
- Processamento assíncrono quando necessário
- Avaliação de modularização
- Evolução futura para arquitetura distribuída

### 🤖 Inteligência Artificial

- Exploração de funcionalidades envolvendo IA
- Integração com agentes de IA
- Avaliação de casos de uso relacionados ao domínio

---

# 🎯 Objetivo profissional

Este projeto faz parte da minha evolução como **desenvolvedor backend Java**.

O objetivo é utilizar o projeto para aprofundar conhecimentos em desenvolvimento de APIs, arquitetura, persistência, segurança, testes e infraestrutura.

```text
Java
  ↓
Spring Boot
  ↓
APIs REST
  ↓
Persistência
  ↓
Regras de negócio
  ↓
Testes
  ↓
Segurança
  ↓
Docker
  ↓
CI/CD
  ↓
Qualidade de código
  ↓
Arquitetura
  ↓
Cloud & IA
```

---

# 🔗 Repositório

<div align="center">

[![GitHub](https://img.shields.io/badge/GitHub-Supermarket%20Management%20API-181717?style=for-the-badge&logo=github)](https://github.com/kelwin-feitosa/supermarket-management-api)

</div>

---

<div align="center">

## 👨‍💻 Desenvolvido por Kelwin Ribeiro Feitosa

🎓 Ciência da Computação  
💻 Backend Java / Spring Boot

<br>

[![GitHub](https://img.shields.io/badge/GitHub-Kelwin%20Feitosa-181717?style=for-the-badge&logo=github)](https://github.com/kelwin-feitosa)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Kelwin%20Feitosa-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/kelwinfeitosa)

<br>

### ☕ Transformando café em código.

</div>