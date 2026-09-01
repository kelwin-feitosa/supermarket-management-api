<div align="center">

# 🛒 Supermarket Management API

### API REST para gerenciamento de supermercado

**Java 21 · Spring Boot 4 · PostgreSQL · JPA · Docker**

<br>

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge\&logo=openjdk\&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-6DB33F?style=for-the-badge\&logo=springboot\&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-316192?style=for-the-badge\&logo=postgresql\&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge\&logo=docker\&logoColor=white)](https://www.docker.com/)

[![Tests](https://img.shields.io/badge/Tests-75%20passing-25A162?style=for-the-badge\&logo=junit5\&logoColor=white)](https://junit.org/junit5/)
[![JaCoCo](https://img.shields.io/badge/JaCoCo-Coverage-EF2D5E?style=for-the-badge)](https://www.jacoco.org/jacoco/)
[![SonarCloud](https://img.shields.io/badge/SonarCloud-Analysis-F3702A?style=for-the-badge\&logo=sonarcloud\&logoColor=white)](https://sonarcloud.io/)
[![CI](https://img.shields.io/badge/CI-GitHub%20Actions-2088FF?style=for-the-badge\&logo=githubactions\&logoColor=white)](https://github.com/features/actions)

</div>

---

## 📌 Sobre o projeto

O **Supermarket Management API** é uma API REST desenvolvida com **Java 21** e **Spring Boot 4**, criada para simular o backend de um sistema de gerenciamento de supermercado.

O projeto foi desenvolvido com foco em **boas práticas de desenvolvimento backend**, organização de código, regras de negócio, persistência relacional, testes automatizados e infraestrutura reproduzível.

A aplicação possui módulos para gerenciamento de produtos, categorias, clientes, fornecedores, compras, vendas e carrinho de compras.

---

## 🎯 Objetivo

O objetivo do projeto é colocar em prática conceitos importantes do desenvolvimento backend profissional, indo além de operações CRUD simples.

Entre os principais conceitos aplicados estão:

* Arquitetura em camadas
* Princípios de separação de responsabilidades
* Spring Data JPA
* Hibernate
* Specifications e filtros dinâmicos
* Paginação e ordenação
* DTOs
* Validação de dados
* Regras de negócio
* Tratamento global de exceções
* Testes unitários
* Testes de persistência
* Migração de banco de dados
* Containerização
* Integração contínua

---

# 🏗️ Arquitetura

A aplicação utiliza uma arquitetura em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

Organização principal:

```text
src
├── main
│   ├── java
│   │   └── com.exemplo.meu_primeiro_projeto
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── exception
│   │       ├── mapper
│   │       ├── model
│   │       ├── repository
│   │       │   └── specification
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

* Cadastro de produtos
* Atualização
* Consulta por ID
* Listagem paginada
* Filtros dinâmicos
* Controle de estoque
* Validação de preços
* Associação com categorias

### 🏷️ Categorias

* Cadastro
* Atualização
* Consulta
* Listagem paginada
* Filtros
* Validação de duplicidade

### 👤 Clientes

* Cadastro
* Atualização
* Consulta
* Listagem
* Filtros
* Validação de dados
* Criação automática do carrinho

### 🏭 Fornecedores

* Cadastro
* Atualização
* Consulta
* Listagem
* Filtros
* Controle de fornecedores ativos
* Validação de CNPJ

### 🛒 Carrinho

* Consulta do carrinho
* Adição de produtos
* Alteração de quantidade
* Remoção de itens
* Cálculo de subtotal
* Cálculo do valor total
* Validação de estoque

### 🧾 Vendas

* Criação de vendas
* Associação com clientes
* Adição de itens
* Cálculo de valores
* Baixa automática de estoque
* Histórico de vendas
* Filtros por cliente e período

### 🚚 Compras

* Registro de compras
* Associação com fornecedores
* Registro de itens
* Atualização de estoque
* Histórico de compras
* Filtros por fornecedor e período

---

# 🔎 Filtros e paginação

A API utiliza **Spring Data JPA Specifications** para permitir consultas dinâmicas.

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

* Paginação
* Ordenação
* Specifications reutilizáveis
* Criteria API
* Composição de filtros

---

# 🧠 Regras de negócio

O projeto possui regras de negócio implementadas na camada de serviço, incluindo:

* Verificação de duplicidade
* Validação de entidades relacionadas
* Validação de disponibilidade de estoque
* Controle de quantidade de produtos
* Atualização automática de estoque
* Cálculo de subtotais
* Cálculo de valores totais
* Validação de operações de compra e venda
* Criação automática de carrinho para clientes

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

* Recurso não encontrado
* Dados inválidos
* Regras de negócio violadas
* Conflitos de dados
* Erros de validação

---

# 🗄️ Banco de dados

O projeto utiliza **PostgreSQL 17** como banco de dados principal.

O schema é controlado pelo **Flyway**, permitindo versionar as alterações estruturais do banco.

```text
PostgreSQL
     │
     ▼
   Flyway
     │
     ▼
  Schema
     │
     ▼
Hibernate / JPA
```

A primeira migration cria as principais tabelas da aplicação:

```text
categoria
cliente
fornecedor
produto
carrinho
item_carrinho
compra
item_compra
venda
item_venda
```

---

# 🧪 Testes

O projeto possui **75 testes automatizados**, utilizando:

* JUnit 5
* Mockito
* Spring Boot Test
* Spring Data JPA Test
* PostgreSQL

Os testes cobrem principalmente:

* Services
* Regras de negócio
* Repositories
* Specifications
* Persistência
* Filtros
* Paginação

Status atual:

```text
Tests run: 75
Failures: 0
Errors:   0
Skipped:  0

BUILD SUCCESS
```

---

# 📊 Qualidade de código

### JaCoCo

Utilizado para gerar relatórios de cobertura dos testes automatizados.

### SonarCloud

Utilizado para análise estática do código e acompanhamento da qualidade do projeto.

```text
Código
  ↓
Testes
  ↓
JaCoCo
  ↓
SonarCloud
```

---

# 🐳 Docker

O PostgreSQL pode ser executado utilizando **Docker Compose**, evitando a necessidade de instalar e configurar o banco manualmente.

Estrutura:

```text
Docker Compose
      │
      ▼
PostgreSQL 17
      │
      ├── supermarket
      │
      └── supermarket_test
```

Porta utilizada localmente:

```text
localhost:5433
```

---

# 🔄 Integração Contínua

O projeto possui **GitHub Actions** configurado para executar automaticamente o pipeline de validação.

```text
Push / Pull Request
        ↓
   GitHub Actions
        ↓
   Java 21 + Maven
        ↓
    PostgreSQL
        ↓
      Tests
        ↓
     JaCoCo
        ↓
   SonarCloud
```

O pipeline verifica se a aplicação continua compilando, se os testes passam e se o relatório de cobertura é gerado corretamente.

---

# 📚 Documentação da API

A API utiliza **Swagger / OpenAPI** para documentação e exploração dos endpoints.

Após iniciar a aplicação:

```text
Swagger UI
http://localhost:8080/swagger-ui.html
```

Documentação OpenAPI:

```text
http://localhost:8080/v3/api-docs
```

---

# 🛠️ Tecnologias

<div align="center">

### ☕ Backend

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge\&logo=openjdk\&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-6DB33F?style=for-the-badge\&logo=springboot\&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge\&logo=spring\&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge\&logo=hibernate\&logoColor=white)
![Validation](https://img.shields.io/badge/Jakarta%20Validation-59666C?style=for-the-badge)

### 🗄️ Banco de Dados

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-316192?style=for-the-badge\&logo=postgresql\&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge\&logo=flyway\&logoColor=white)

### 🧪 Testes e Qualidade

![JUnit](https://img.shields.io/badge/JUnit%205-25A162?style=for-the-badge\&logo=junit5\&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-78A641?style=for-the-badge)
![JaCoCo](https://img.shields.io/badge/JaCoCo-Coverage-EF2D5E?style=for-the-badge)
![SonarCloud](https://img.shields.io/badge/SonarCloud-F3702A?style=for-the-badge\&logo=sonarcloud\&logoColor=white)

### 🐳 Infraestrutura e Ferramentas

![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge\&logo=docker\&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge\&logo=githubactions\&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge\&logo=apachemaven\&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge\&logo=git\&logoColor=white)
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge\&logo=linux\&logoColor=black)

</div>

---

# ⚙️ Como executar

## Pré-requisitos

* Java 21
* Docker
* Docker Compose
* Git

## 1. Clone o projeto

```bash
git clone https://github.com/kelwin-feitosa/supermarket-management-api.git

cd supermarket-management-api
```

## 2. Inicie o PostgreSQL

```bash
docker compose up -d
```

O Docker irá iniciar o PostgreSQL e disponibilizar o banco na porta:

```text
5433
```

## 3. Execute a aplicação

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

## 4. Execute os testes

```bash
./mvnw clean verify
```

---

# 📡 Principais endpoints

| Recurso         | Endpoint        | Operações      |
| --------------- | --------------- | -------------- |
| 🏷️ Categorias  | `/categorias`   | CRUD           |
| 🛍️ Produtos    | `/produtos`     | CRUD + filtros |
| 👤 Clientes     | `/clientes`     | CRUD + filtros |
| 🏭 Fornecedores | `/fornecedores` | CRUD + filtros |
| 🛒 Carrinho     | `/carrinhos`    | Gerenciamento  |
| 🚚 Compras      | `/compras`      | CRUD + filtros |
| 🧾 Vendas       | `/vendas`       | CRUD + filtros |

> A criação do carrinho acontece automaticamente durante o cadastro do cliente.

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
                                         │
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
```

---

# 🔗 Repositório

<div align="center">

[![GitHub](https://img.shields.io/badge/GitHub-Supermarket%20Management%20API-181717?style=for-the-badge\&logo=github)](https://github.com/kelwin-feitosa/supermarket-management-api)

</div>

---

# 🚧 Próximos passos

O projeto continua em evolução.

### 🔐 Segurança

* Spring Security
* JWT
* BCrypt
* Autenticação e autorização
* Controle de acesso por perfil

### 🧪 Testes

* Testes de Controllers com MockMvc
* Testes de integração
* Testcontainers

### 📈 Evolução da aplicação

* Histórico detalhado de estoque
* Logs estruturados
* Melhorias de observabilidade
* Dockerfile da aplicação
* Pipeline de deploy
* Deploy em ambiente cloud
* Integração com Inteligência Artificial
* Evolução futura para arquitetura distribuída

---

# 🎯 Objetivo profissional

Este projeto faz parte da minha evolução como **desenvolvedor backend Java**.

Meu objetivo é continuar aprofundando conhecimentos em:

```text
Java
  ↓
Spring Boot
  ↓
APIs REST
  ↓
Persistência
  ↓
Testes
  ↓
Docker
  ↓
CI/CD
  ↓
Segurança
  ↓
Arquitetura
  ↓
Cloud & IA
```

---

<div align="center">

## 👨‍💻 Desenvolvido por Kelwin Ribeiro Feitosa

🎓 Ciência da Computação
💻 Backend Java / Spring Boot

<br>

[![GitHub](https://img.shields.io/badge/GitHub-Kelwin%20Feitosa-181717?style=for-the-badge\&logo=github)](https://github.com/kelwin-feitosa)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Kelwin%20Feitosa-0A66C2?style=for-the-badge\&logo=linkedin\&logoColor=white)](https://www.linkedin.com/in/kelwinfeitosa)

<br>

### ☕ Transformando café em código.

</div>
