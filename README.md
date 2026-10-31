<div align="center">

# 🛒 Supermarket Management API

### Sistema de gerenciamento de supermercado

**Java 21 · Spring Boot 4 · PostgreSQL · Spring Security · Docker**

<br>

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-6DB33F?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

<br>

[![Tests](https://img.shields.io/badge/Tests-76%20passing-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/junit5/)
[![JaCoCo](https://img.shields.io/badge/JaCoCo-Coverage-EF2D5E?style=for-the-badge)](https://www.jacoco.org/jacoco/)
[![SonarCloud](https://img.shields.io/badge/SonarCloud-Analysis-F3702A?style=for-the-badge&logo=sonarcloud)](https://sonarcloud.io/)
[![CI](https://img.shields.io/badge/CI-GitHub%20Actions-2088FF?style=for-the-badge&logo=githubactions)](https://github.com/features/actions)

<br>

**Backend REST · Arquitetura Modular · Segurança · Testes Automatizados**

</div>

---

## 📌 Sobre o projeto

O **Supermarket Management API** é um projeto de engenharia de software desenvolvido com **Java 21, Spring Boot 4 e PostgreSQL**, cujo objetivo é construir a base de um sistema de gerenciamento de supermercado.

A aplicação contempla operações de:

- Gerenciamento de produtos e categorias;
- Cadastro e gerenciamento de fornecedores;
- Controle de estoque;
- Registro de compras de mercadorias;
- Gerenciamento de carrinhos;
- Registro de vendas;
- Cadastro e gerenciamento de usuários;
- Autenticação e autorização;
- Consultas, filtros e paginação.

O projeto é desenvolvido com foco em práticas de engenharia de software, incluindo **modelagem de domínio, arquitetura modular, regras de negócio, persistência relacional, segurança, testes automatizados e integração contínua**.

A proposta é evoluir gradualmente de uma API REST para um **sistema completo de gestão de um supermercado físico**, incluindo uma interface administrativa e um módulo de frente de caixa (PDV).

> **Status:** Backend em evolução. A primeira reorganização para uma arquitetura modular foi realizada e validada localmente com 76 testes automatizados.
>
> O frontend e algumas funcionalidades planejadas para o sistema completo ainda não foram implementados.

---

# 🎯 Objetivos

O projeto possui dois objetivos complementares.

### Objetivo funcional

Desenvolver um sistema que permita administrar as operações fundamentais de um supermercado, desde o cadastro de produtos até o registro de vendas e o acompanhamento do estoque.

### Objetivo técnico

Aplicar e aprofundar conhecimentos em desenvolvimento de software, com atenção especial a:

- Programação Orientada a Objetos;
- Princípios SOLID;
- Separação de responsabilidades;
- Modelagem de domínio;
- Arquitetura modular;
- APIs REST;
- Spring Boot;
- Spring Data JPA;
- Hibernate;
- Transações;
- Validação de dados;
- Autenticação e autorização;
- Testes automatizados;
- Integração contínua;
- Persistência relacional;
- Tratamento de erros;
- Documentação;
- Qualidade e manutenibilidade.

O projeto também serve como ambiente de aprendizagem para identificar problemas reais de arquitetura, refatorar código existente e avaliar decisões de implementação.

---

# 🏗️ Arquitetura

## Monólito modular

A aplicação utiliza uma arquitetura de **monólito modular**, organizada por funcionalidades de negócio.

Essa abordagem mantém a simplicidade operacional de uma única aplicação Spring Boot, mas melhora a organização e a identificação das responsabilidades de cada módulo.

A separação por funcionalidades substitui a organização anterior, em que controllers, services, repositories e entidades de diferentes áreas ficavam agrupados em diretórios globais.

### Organização dos módulos

```text
com.exemplo.meu_primeiro_projeto/
│
├── catalog/
│   ├── controller/
│   ├── dto/
│   ├── mapper/
│   ├── model/
│   ├── repository/
│   └── service/
│
├── procurement/
│   ├── controller/
│   ├── dto/
│   ├── mapper/
│   ├── model/
│   ├── repository/
│   └── service/
│
├── sales/
│   ├── controller/
│   ├── dto/
│   ├── mapper/
│   ├── model/
│   ├── repository/
│   └── service/
│
├── inventory/
│   └── service/
│
├── identity/
│   ├── controller/
│   ├── dto/
│   ├── enums/
│   ├── mapper/
│   ├── model/
│   ├── repository/
│   └── service/
│
├── security/
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── filter/
│   └── service/
│
├── shared/
│   ├── exception/
│   │   └── handler/
│   ├── service/
│   └── util/
│
├── config/
│
└── MeuPrimeiroProjetoApplication.java
```

A estrutura representa a divisão lógica dos módulos. As subpastas refletem as responsabilidades existentes e podem evoluir conforme novas funcionalidades forem implementadas.

## Responsabilidades dos módulos

| Módulo | Responsabilidade |
|---|---|
| `catalog` | Gerenciamento de produtos e categorias |
| `procurement` | Compras e fornecedores |
| `sales` | Carrinhos, vendas e respectivos itens |
| `inventory` | Regras de controle de estoque |
| `identity` | Usuários e perfis de acesso |
| `security` | Autenticação, JWT e componentes de segurança |
| `shared` | Componentes compartilhados, exceções e utilitários |
| `config` | Configurações gerais da aplicação |

### Fluxo de processamento

A organização modular mantém a separação entre as responsabilidades de apresentação, aplicação e persistência.

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
               ┌──────────────┐
               │  PostgreSQL  │
               └──────────────┘
```

Os controllers recebem requisições HTTP e coordenam a entrada e a saída de dados.

Os services concentram as regras de negócio e a execução das operações.

Os repositories oferecem acesso à persistência por meio do Spring Data JPA.

Os DTOs delimitam os dados utilizados nas requisições e respostas.

Os mappers realizam conversões entre objetos de domínio e DTOs.

As Specifications permitem construir consultas com filtros dinâmicos.

### Decisão arquitetural

O projeto permanece como uma única aplicação Spring Boot.

Não há utilização de microserviços.

A modularização foi adotada para melhorar:

- Organização do código;
- Compreensão das responsabilidades;
- Manutenção;
- Evolução das funcionalidades;
- Localização dos componentes;
- Testabilidade;
- Redução de dependências desnecessárias.

Uma arquitetura distribuída não faz parte do escopo atual.

---

# 🚀 Funcionalidades implementadas

Esta seção descreve as funcionalidades presentes no backend atual.

Funcionalidades planejadas para as próximas versões são apresentadas separadamente no roadmap.

## 📦 Produtos

O módulo de catálogo permite:

- Cadastrar produtos;
- Atualizar informações;
- Buscar produtos por identificador;
- Listar produtos com paginação;
- Aplicar filtros dinâmicos;
- Associar produtos a categorias;
- Definir preços;
- Registrar quantidades em estoque;
- Validar dados de entrada;
- Verificar duplicidade de nomes;
- Excluir produtos.

Os produtos possuem informações como:

- Identificador;
- Nome;
- Descrição;
- Preço;
- Quantidade em estoque;
- Categoria;
- Data de cadastro.

> **Evolução planejada:** identificação por SKU e código de barras, estoque mínimo, unidades de medida e desativação de produtos.

## 🏷️ Categorias

O sistema permite:

- Cadastrar categorias;
- Atualizar informações;
- Buscar por identificador;
- Listar com paginação;
- Aplicar filtros;
- Verificar duplicidade;
- Excluir categorias, respeitando as regras implementadas.

As categorias permitem organizar os produtos e facilitar consultas.

## 👤 Usuários

O módulo de identidade contempla:

- Cadastro;
- Atualização;
- Consulta;
- Listagem;
- Filtros;
- Validação de informações;
- Criação automática de carrinho;
- Armazenamento de senhas com BCrypt;
- Associação de perfis de acesso.

O modelo atual utiliza a entidade `Usuario` tanto para autenticação quanto para representar o usuário associado a carrinhos e vendas.

> **Evolução planejada:** distinguir funcionários de clientes e permitir vendas presenciais sem cadastro obrigatório do comprador.

## 🏭 Fornecedores

O sistema permite:

- Cadastrar fornecedores;
- Atualizar dados;
- Consultar fornecedores;
- Listar com filtros e paginação;
- Verificar duplicidade de CNPJ;
- Controlar a situação ativa ou inativa dos fornecedores.

Fornecedores são associados às compras de mercadorias.

## 🛒 Carrinhos

O módulo de vendas possui regras para:

- Adicionar produtos;
- Alterar quantidades;
- Remover itens;
- Limpar carrinhos;
- Calcular subtotais;
- Calcular valores totais;
- Verificar disponibilidade de estoque.

Atualmente, os carrinhos estão associados a usuários cadastrados.

> **Evolução planejada:** adaptar esse modelo para permitir que funcionários registrem vendas presenciais sem exigir um carrinho permanente por cliente.

## 🧾 Vendas

O sistema permite:

- Registrar vendas a partir de carrinhos;
- Associar vendas a usuários;
- Registrar itens vendidos;
- Preservar os preços utilizados nos itens;
- Calcular subtotais;
- Calcular o valor total;
- Reduzir o estoque;
- Consultar vendas;
- Listar vendas com filtros e paginação.

A realização de vendas utiliza transações para agrupar alterações relacionadas à operação.

> **Ainda não implementado:** registro de pagamentos, cancelamentos, devoluções, identificação independente do operador de caixa e controle adequado de vendas simultâneas.

## 🚚 Compras

O módulo de compras permite:

- Registrar compras de mercadorias;
- Associar compras a fornecedores;
- Registrar produtos e quantidades;
- Armazenar preços de compra;
- Calcular subtotais;
- Calcular valores totais;
- Aumentar o estoque;
- Consultar compras;
- Listar compras com filtros e paginação.

O fluxo atual considera o registro da compra como entrada de mercadorias.

A separação entre pedido de compra e recebimento físico poderá ser implementada futuramente.

## 📊 Consultas e paginação

A aplicação utiliza Spring Data JPA para realizar consultas paginadas e ordenadas.

Também utiliza Specifications para combinar critérios de pesquisa dinamicamente.

Entre os critérios disponíveis, conforme o recurso, estão:

- Nome;
- Categoria;
- Faixa de preços;
- Quantidade em estoque;
- Fornecedor;
- Usuário;
- Períodos de operações.

Exemplo de consulta de produtos:

```http
GET /api/produtos?page=0&size=10&sort=nome,asc
```

As respostas paginadas utilizam a estrutura disponibilizada pelo Spring Data.

---

# 🧠 Regras de negócio

As regras de negócio ficam concentradas principalmente nos services de cada módulo.

Entre os comportamentos implementados estão:

- Validação de duplicidade;
- Validação de entidades relacionadas;
- Verificação de existência de recursos;
- Validação de disponibilidade de estoque;
- Atualização das quantidades em operações de compra e venda;
- Cálculo de subtotais;
- Cálculo de valores totais;
- Validação de compras;
- Validação de vendas;
- Criação automática de carrinhos para usuários;
- Restrições relacionadas a categorias;
- Controle de fornecedores.

## Consistência das operações

Compras e vendas utilizam operações transacionais para preservar a consistência das alterações realizadas durante uma operação.

Por exemplo, ao registrar uma venda, o sistema precisa relacionar:

1. O carrinho utilizado;
2. Os itens vendidos;
3. As quantidades;
4. Os preços;
5. A atualização do estoque;
6. O registro da venda.

Uma falha durante a transação não deve resultar em uma operação parcialmente concluída.

### Limitações atuais

A implementação ainda precisa evoluir em alguns pontos:

- Controle de concorrência entre vendas simultâneas;
- Histórico individual de movimentações de estoque;
- Cancelamento de vendas;
- Devolução de mercadorias;
- Ajustes de inventário;
- Registro de pagamentos;
- Rastreabilidade das operações.

Essas funcionalidades serão desenvolvidas gradualmente.

---

# 🔐 Segurança

A API utiliza **Spring Security** para autenticação e autorização.

## Autenticação JWT

A autenticação utiliza JSON Web Tokens (JWT).

O sistema trabalha com sessões de autenticação stateless, sem depender de sessões HTTP tradicionais para armazenar o estado de autenticação.

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

O token retornado deve ser enviado nas requisições autenticadas:

```http
Authorization: Bearer <token>
```

## Perfis de acesso

O sistema possui os perfis:

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

As permissões são configuradas com Spring Security e anotações `@PreAuthorize`.

A hierarquia permite que determinadas permissões sejam herdadas por perfis superiores.

> **Atenção:** as permissões atuais ainda precisam de revisão para refletir corretamente os processos de um supermercado físico. A presença dos perfis não significa que todos os fluxos de autorização estejam concluídos.

## Proteção de senhas

As senhas são transformadas em hashes utilizando BCrypt antes do armazenamento.

O sistema não deve persistir senhas em texto puro.

## Configuração do JWT

A chave utilizada para assinar tokens JWT deve ser fornecida por variável de ambiente.

```text
JWT_SECRET
```

Exemplo para Linux:

```bash
export JWT_SECRET="$(openssl rand -hex 32)"
```

A chave não deve ser incluída em commits ou arquivos versionados.

## Melhorias planejadas

- Revisão das permissões por função;
- Inicialização segura do administrador;
- Proteção contra acesso a recursos pertencentes a outros usuários;
- Testes de autorização para diferentes perfis;
- Configuração de CORS para o futuro frontend;
- Revisão do tratamento de tokens inválidos e expirados;
- Melhorias na auditoria das operações.

---

# ⚠️ Tratamento de exceções

A aplicação utiliza tratamento global de exceções para padronizar respostas HTTP.

Inicialmente, o projeto possuía diversas exceções específicas para situações semelhantes.

A arquitetura foi refatorada para reduzir a duplicação e utilizar exceções compartilhadas.

## Exceções compartilhadas

| Exceção | HTTP | Finalidade |
|---|---|---|
| `ResourceNotFoundException` | 404 | Recurso não encontrado |
| `ResourceConflictException` | 409 | Conflito ou duplicidade |
| `BusinessRuleException` | 400 | Violação de regra de negócio |

### Exemplo: recurso não encontrado

```java
throw new ResourceNotFoundException(
    "Produto não encontrado."
);
```

### Exemplo: conflito

```java
throw new ResourceConflictException(
    "Já existe uma categoria com esse nome."
);
```

### Exemplo: regra de negócio

```java
throw new BusinessRuleException(
    "Estoque insuficiente para realizar a operação."
);
```

## Respostas padronizadas

O tratamento global é centralizado em:

```text
shared/exception/handler/GlobalExceptionHandler.java
```

Exemplo de resposta:

```json
{
  "mensagem": "Recurso não encontrado.",
  "detalhes": "Produto não encontrado.",
  "timestamp": "2026-10-31T16:30:00-03:00"
}
```

Os títulos das respostas são genéricos, enquanto os detalhes preservam informações sobre o erro encontrado.

A aplicação também trata situações como:

- Dados inválidos;
- JSON malformado;
- Violações de validação;
- Falhas inesperadas.

Autenticação e autorização são responsabilidades integradas ao Spring Security.

### Benefícios da refatoração

A consolidação de exceções busca:

- Reduzir classes repetitivas;
- Padronizar respostas;
- Simplificar manutenção;
- Evitar duplicação de código;
- Facilitar a evolução da API.

---

# 🗄️ Persistência e banco de dados

A aplicação utiliza PostgreSQL 17 como banco de dados relacional.

A persistência é implementada com Spring Data JPA e Hibernate.

## Principais entidades

O modelo atual contempla:

```text
Usuario
Categoria
Produto
Fornecedor
Carrinho
ItemCarrinho
Compra
ItemCompra
Venda
ItemVenda
```

As entidades são relacionadas de acordo com as regras de cada operação.

### Relacionamentos principais

```text
Categoria
    │
    └── Produto

Fornecedor
    │
    └── Compra
          │
          └── ItemCompra
                │
                └── Produto

Usuario
    │
    ├── Carrinho
    │     │
    │     └── ItemCarrinho
    │           │
    │           └── Produto
    │
    └── Venda
          │
          └── ItemVenda
                │
                └── Produto
```

Esse é o modelo atual. As relações entre funcionários, clientes e vendas serão revisadas nas próximas etapas.

## Flyway

O Flyway é utilizado para versionar alterações no esquema do banco de dados.

As migrations existentes são:

```text
db/migration/
├── V1__criacao_inicial.sql
└── V2__renomear_cliente_para_usuario_e_adicionar_autenticacao.sql
```

A migration V1 introduz a estrutura inicial de persistência.

A migration V2 adapta o modelo de clientes para usuários e adiciona os campos relacionados à autenticação.

As migrations são executadas automaticamente durante a inicialização da aplicação.

## Validação do esquema

O projeto utiliza:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

Dessa maneira, o Hibernate verifica a compatibilidade entre o mapeamento das entidades e o esquema existente.

Mudanças estruturais devem ser realizadas por novas migrations do Flyway.

---

# 🧪 Testes automatizados

A aplicação possui testes automatizados utilizando:

- JUnit 5;
- Mockito;
- Spring Boot Test;
- Spring Security Test;
- Spring Data JPA;
- PostgreSQL;
- MockMvc.

## Áreas testadas

A suíte atual contempla testes relacionados a:

- Produtos;
- Categorias;
- Fornecedores;
- Compras;
- Vendas;
- Usuários;
- Estoque;
- Cálculos de preços;
- Specifications;
- Inicialização da aplicação;
- Configurações de segurança.

## Resultado da validação local

Após a refatoração da arquitetura, a aplicação foi compilada e validada localmente.

Resultado registrado em **31 de outubro de 2026**:

```text
Tests run: 76
Failures: 0
Errors:   0
Skipped:  0

BUILD SUCCESS
```

A validação também confirmou:

- Compilação das classes;
- Inicialização do contexto Spring;
- Conexão com PostgreSQL;
- Validação das migrations;
- Execução dos testes;
- Geração do artefato JAR;
- Geração do relatório JaCoCo.

> O resultado acima corresponde à execução local. Não representa garantia de que todos os cenários de negócio estejam cobertos ou de que o pipeline remoto esteja passando.

## Executando os testes

Com o PostgreSQL disponível e o ambiente configurado:

```bash
./mvnw clean verify
```

O comando executa as etapas de compilação, testes e verificações configuradas no ciclo Maven.

## Melhorias planejadas

- Ampliar testes de integração;
- Adicionar testes de controllers;
- Cobrir cenários de autorização;
- Implementar testes de concorrência;
- Validar transações;
- Introduzir Testcontainers;
- Aprimorar isolamento entre os bancos de desenvolvimento e teste.

---

# 📊 Qualidade de código

## JaCoCo

O JaCoCo é utilizado para medir a cobertura dos testes.

O relatório HTML pode ser consultado após a execução do Maven:

```text
target/site/jacoco/index.html
```

A cobertura deverá ser acompanhada principalmente nas regras de negócio.

Uma porcentagem elevada de cobertura não substitui testes relevantes.

## SonarCloud

O projeto possui configuração para utilização do SonarCloud.

A ferramenta pode auxiliar na identificação de:

- Bugs;
- Vulnerabilidades;
- Security Hotspots;
- Code Smells;
- Problemas de manutenibilidade;
- Pontos de melhoria na cobertura de testes.

Os resultados devem ser avaliados considerando o contexto do projeto.

---

# 🐳 Docker

O projeto utiliza Docker Compose para disponibilizar o PostgreSQL em ambiente local.

## Infraestrutura atual

```text
Docker Compose
      │
      ▼
PostgreSQL 17
      │
      ▼
Spring Boot API
```

Atualmente, o Docker Compose disponibiliza principalmente o banco de dados.

A aplicação Spring Boot pode ser executada separadamente pelo Maven.

### Inicializando o banco

```bash
docker compose up -d
```

O PostgreSQL fica disponível em:

```text
localhost:5433
```

A porta 5433 evita conflito com instalações locais que utilizam a porta padrão 5432.

### Encerrando o serviço

```bash
docker compose down
```

O volume persistente não é removido por esse comando.

> Evite utilizar `docker compose down -v` sem compreender as consequências, pois isso remove os volumes do projeto e pode causar perda de dados locais.

## Evolução planejada

- Dockerfile da aplicação;
- Healthchecks;
- Execução da aplicação e banco pelo Docker Compose;
- Configuração por variáveis de ambiente;
- Separação adequada de ambientes;
- Preparação para deploy.

---

# 🔄 Integração contínua

A aplicação possui um workflow do GitHub Actions responsável pela validação automática.

## Pipeline

```text
Push / Pull Request
        │
        ▼
      Checkout
        │
        ▼
      Java 21
        │
        ▼
     PostgreSQL
        │
        ▼
      Maven
        │
        ▼
      Testes
        │
        ▼
      JaCoCo
        │
        ▼
     SonarCloud
```

O pipeline possui etapas para:

- Preparar o ambiente;
- Iniciar PostgreSQL;
- Criar o banco de testes;
- Executar Maven;
- Validar o relatório de cobertura;
- Executar análise estática quando configurada para o evento correspondente.

## Estado atual

A branch principal teve uma execução de CI bem-sucedida antes da refatoração.

Entretanto, algumas atualizações automáticas de dependências apresentaram falhas nos testes de integração.

Foi identificado um problema relacionado à disponibilidade da chave JWT nos ambientes de pull requests automatizados.

### Melhorias pendentes

- Configurar chave JWT específica para testes;
- Reduzir dependência de segredos em PRs automatizados;
- Garantir isolamento do banco de testes;
- Validar o pipeline após a refatoração;
- Revisar atualizações de dependências.

O sucesso da execução local não substitui a validação do CI.

---

# 📚 Documentação da API

A API utiliza Swagger e OpenAPI para documentar os endpoints.

## Swagger UI

Após iniciar a aplicação:

```text
http://localhost:8080/swagger-ui.html
```

## OpenAPI

```text
http://localhost:8080/v3/api-docs
```

Os controllers possuem documentação das operações, parâmetros e respostas HTTP.

A interface Swagger pode ser utilizada para explorar os contratos disponibilizados pela API.

Algumas operações exigem autenticação.

---

# 📡 Principais endpoints

| Recurso | Endpoint | Operações |
|---|---|---|
| 🔐 Autenticação | `/auth` | Login |
| 🏷️ Categorias | `/categorias` | Cadastro, consulta, atualização e exclusão |
| 🛍️ Produtos | `/api/produtos` | Cadastro, consulta, atualização e exclusão |
| 👤 Usuários | `/usuarios` | Cadastro, consulta, atualização e exclusão |
| 🏭 Fornecedores | `/fornecedores` | Cadastro, consulta, atualização e desativação |
| 🛒 Carrinhos | `/carrinhos` | Gerenciamento de itens |
| 🚚 Compras | `/compras` | Registro e consultas |
| 🧾 Vendas | `/vendas` | Registro e consultas |

As operações disponíveis dependem das permissões do usuário autenticado.

### Autenticação

```http
POST /auth/login
Content-Type: application/json
```

Exemplo:

```json
{
  "email": "usuario@exemplo.com",
  "senha": "Senha@123"
}
```

Em caso de sucesso, a API retorna um token JWT.

### Exemplo de consulta paginada

```http
GET /api/produtos?page=0&size=10&sort=nome,asc
Authorization: Bearer <token>
```

> O projeto ainda precisa definir um procedimento seguro de criação do primeiro administrador. Atualmente, o login pressupõe a existência de um usuário válido no banco de dados.

---

# 🛠️ Tecnologias

<div align="center">

### ☕ Backend

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-BC4521?style=for-the-badge)

### 🔐 Segurança

![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![BCrypt](https://img.shields.io/badge/BCrypt-Password%20Hashing-59666C?style=for-the-badge)

### 🗄️ Banco de dados

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)

### 🧪 Testes e qualidade

![JUnit](https://img.shields.io/badge/JUnit%205-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-78A641?style=for-the-badge)
![JaCoCo](https://img.shields.io/badge/JaCoCo-Coverage-EF2D5E?style=for-the-badge)
![SonarCloud](https://img.shields.io/badge/SonarCloud-F3702A?style=for-the-badge&logo=sonarcloud&logoColor=white)

### 📖 Documentação

![OpenAPI](https://img.shields.io/badge/OpenAPI-6BA539?style=for-the-badge&logo=openapiinitiative&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

### 🐳 Infraestrutura e ferramentas

![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)

</div>

---

# ⚙️ Como executar

## Pré-requisitos

- JDK 21;
- Docker;
- Docker Compose;
- Git;
- Maven Wrapper incluído no repositório.

O projeto utiliza Java 21 como versão-alvo de compilação.

## 1. Clonar o projeto

```bash
git clone https://github.com/kelwin-feitosa/supermarket-management-api.git

cd supermarket-management-api
```

## 2. Configurar a chave JWT

A aplicação necessita da variável de ambiente:

```text
JWT_SECRET
```

Linux/macOS:

```bash
export JWT_SECRET="$(openssl rand -hex 32)"
```

O comando gera uma chave aleatória apropriada para o ambiente local.

Não armazene segredos reais no repositório.

## 3. Iniciar o PostgreSQL

```bash
docker compose up -d
```

A configuração atual disponibiliza o PostgreSQL na porta:

```text
5433
```

## 4. Executar a aplicação

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```bat
mvnw.cmd spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

O Flyway executará as migrations necessárias durante a inicialização.

## 5. Acessar a documentação

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI:

```text
http://localhost:8080/v3/api-docs
```

## 6. Executar os testes

```bash
./mvnw clean verify
```

Os testes de integração exigem que o banco PostgreSQL de testes esteja disponível.

O Docker Compose inclui um script de inicialização para criação desse banco em volumes novos.

Se o volume já existir e o banco `supermarket_test` não tiver sido criado, será necessário criá-lo antes de executar os testes.

Exemplo:

```bash
docker compose exec postgres \
  psql -U postgres -d postgres \
  -c "CREATE DATABASE supermarket_test;"
```

Execute esse comando somente se o banco ainda não existir.

## 7. Consultar o relatório de cobertura

Após executar os testes:

```text
target/site/jacoco/index.html
```

## 8. Gerar o JAR

```bash
./mvnw clean package
```

O artefato será gerado no diretório:

```text
target/
```

---

# 🧩 Decisões de arquitetura

As decisões atuais priorizam simplicidade, clareza e evolução incremental.

| Tema | Decisão |
|---|---|
| Estilo arquitetural | Monólito modular |
| Organização | Por funcionalidades de negócio |
| Comunicação | API REST |
| Persistência | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Migrações | Flyway |
| Autenticação | JWT |
| Autorização | Spring Security |
| Tratamento de erros | Exceções compartilhadas |
| Testes | JUnit, Mockito e Spring Test |
| Build | Maven |
| Infraestrutura local | Docker Compose |
| Frontend planejado | React e TypeScript |

## Por que um monólito modular?

O sistema ainda não apresenta necessidade comprovada de uma arquitetura distribuída.

Adotar microserviços neste momento aumentaria a complexidade de:

- Implantação;
- Comunicação entre componentes;
- Observabilidade;
- Segurança;
- Testes;
- Consistência de dados;
- Manutenção.

A arquitetura modular permite evoluir o sistema sem introduzir essas dificuldades antecipadamente.

## Por que consolidar as exceções?

As exceções anteriores repetiam estruturas semelhantes para recursos diferentes.

A utilização de exceções compartilhadas simplifica a modelagem dos erros e reduz código repetitivo.

As mensagens específicas continuam identificando a causa do problema.

## Por que manter o PostgreSQL?

O PostgreSQL oferece recursos adequados às necessidades atuais do projeto, incluindo:

- Transações;
- Integridade referencial;
- Restrições;
- Consultas relacionais;
- Índices;
- Mecanismos de controle de concorrência.

Esses recursos serão importantes para a evolução das operações de estoque e vendas.

---

# 🗺️ Roadmap

O desenvolvimento está organizado em etapas incrementais.

A ordem considera dependências entre funcionalidades e a necessidade de manter uma aplicação funcional durante sua evolução.

## Fase 1 — Reorganização arquitetural

- [x] Reorganizar o código por módulos de negócio;
- [x] Preservar a separação entre controllers, services e repositories;
- [x] Consolidar exceções específicas;
- [x] Atualizar referências e imports;
- [x] Validar compilação;
- [x] Executar testes automatizados localmente;
- [ ] Confirmar o funcionamento da refatoração no CI;
- [ ] Revisar dependências entre módulos;
- [ ] Validar contratos HTTP e compatibilidade das respostas.

## Fase 2 — Segurança e identidade

- [ ] Corrigir a configuração de testes no CI;
- [ ] Revisar a hierarquia de permissões;
- [ ] Criar procedimento seguro de inicialização do administrador;
- [ ] Separar funcionários e clientes no modelo de domínio;
- [ ] Permitir clientes não identificados nas vendas presenciais;
- [ ] Proteger recursos contra acesso indevido;
- [ ] Ampliar testes de autenticação e autorização.

## Fase 3 — Catálogo e estoque

- [ ] Introduzir identificação de produtos por SKU;
- [ ] Adicionar código de barras;
- [ ] Suportar unidades e quantidades fracionárias;
- [ ] Implementar estoque mínimo;
- [ ] Registrar movimentações de estoque;
- [ ] Implementar ajustes de inventário;
- [ ] Impedir alterações de estoque sem rastreabilidade;
- [ ] Implementar controle de concorrência;
- [ ] Introduzir inativação de produtos.

## Fase 4 — Compras e vendas

- [ ] Adaptar carrinhos para vendas presenciais;
- [ ] Registrar o funcionário responsável pela operação;
- [ ] Registrar formas de pagamento simuladas;
- [ ] Implementar cancelamento de vendas;
- [ ] Registrar motivos de cancelamento;
- [ ] Implementar devolução controlada de mercadorias ao estoque;
- [ ] Suportar descontos autorizados;
- [ ] Ampliar testes de transações e integridade.

## Fase 5 — Gestão administrativa

- [ ] Criar indicadores gerenciais;
- [ ] Exibir produtos com estoque baixo;
- [ ] Apresentar histórico de movimentações;
- [ ] Consultar vendas por período;
- [ ] Implementar relatórios básicos;
- [ ] Melhorar a rastreabilidade das operações.

## Fase 6 — Frontend

Tecnologias previstas:

- React;
- TypeScript;
- Vite;
- Tailwind CSS;
- React Router.

O Google Stitch poderá auxiliar na prototipagem das interfaces.

### Telas planejadas

- [ ] Login;
- [ ] Dashboard;
- [ ] Produtos;
- [ ] Categorias;
- [ ] Fornecedores;
- [ ] Compras;
- [ ] Estoque;
- [ ] Vendas / PDV;
- [ ] Funcionários;
- [ ] Relatórios básicos.

## Fase 7 — Entrega

- [ ] Ampliar testes automatizados;
- [ ] Configurar ambiente completo com Docker;
- [ ] Revisar segurança;
- [ ] Validar todos os fluxos principais;
- [ ] Preparar dados fictícios para demonstração;
- [ ] Documentar decisões arquiteturais;
- [ ] Criar demonstração reproduzível;
- [ ] Preparar o projeto para apresentação no portfólio.

---

# 🔮 Possíveis evoluções futuras

As funcionalidades abaixo não fazem parte do escopo inicial.

Sua implementação dependerá de necessidade demonstrada e avaliação de complexidade.

### Gestão de mercadorias

- Controle de lotes;
- Data de validade;
- Produtos perecíveis;
- Rastreabilidade;
- Controle de perdas;
- Reposição automatizada;
- Gestão de múltiplos estoques.

### Operação comercial

- Promoções;
- Programas de fidelidade;
- Devoluções parciais;
- Trocas;
- Integração com leitores de código de barras;
- Integração com balanças;
- Integração com impressoras;
- Pagamentos reais.

### Integrações

- Emissão de documentos fiscais;
- Sistemas de pagamento;
- Integrações contábeis;
- Serviços externos;
- Comércio eletrônico.

### Inteligência Artificial

Possibilidades futuras incluem:

- Previsão de demanda;
- Sugestão de reposição;
- Análise de padrões de vendas;
- Identificação de anomalias;
- Apoio à tomada de decisões.

Essas funcionalidades serão avaliadas somente após a consolidação do sistema principal.

---

# 🎯 Escopo do produto

O projeto tem como objetivo inicial demonstrar o funcionamento de um sistema para **um supermercado físico**, com operações administrativas e de frente de caixa.

O MVP planejado contempla:

- Um estabelecimento;
- Vários funcionários;
- Perfis de acesso;
- Cadastro de produtos;
- Cadastro de fornecedores;
- Compras;
- Estoque;
- Vendas presenciais;
- Pagamentos simulados;
- Cancelamentos controlados;
- Consultas gerenciais;
- Interface web.

O cadastro de clientes será opcional para vendas presenciais.

O sistema não pretende, nesta primeira versão, substituir uma solução comercial certificada ou integrada a obrigações fiscais.

Integrações fiscais e financeiras reais exigiriam análise específica de requisitos legais, operacionais e de segurança.

---

# 👨‍💻 Objetivo profissional

Este projeto faz parte da minha formação e evolução como desenvolvedor de software, com foco em **Java, Spring Boot e engenharia de software**.

Seu desenvolvimento busca ir além da implementação de funcionalidades isoladas.

O objetivo é compreender como diferentes componentes se relacionam e como decisões arquiteturais influenciam a qualidade de uma aplicação.

Entre as competências desenvolvidas estão:

- Modelagem de domínio;
- Desenvolvimento de APIs REST;
- Persistência relacional;
- Arquitetura modular;
- Refatoração;
- Segurança;
- Testes automatizados;
- Depuração;
- Integração contínua;
- Documentação técnica;
- Evolução de sistemas existentes.

O processo de desenvolvimento também incorpora ferramentas de inteligência artificial como apoio à análise, implementação, revisão e automação.

As decisões técnicas, validações e verificações de comportamento fazem parte do aprendizado e da responsabilidade do desenvolvimento.

---

# 🔗 Repositório

<div align="center">

[![GitHub](https://img.shields.io/badge/GitHub-Supermarket%20Management%20API-181717?style=for-the-badge&logo=github)](https://github.com/kelwin-feitosa/supermarket-management-api)

</div>

---

<div align="center">

## 👨‍💻 Desenvolvido por Kelwin Ribeiro Feitosa

🎓 Ciência da Computação  
💻 Java · Spring Boot · Engenharia de Software

<br>

[![GitHub](https://img.shields.io/badge/GitHub-Kelwin%20Feitosa-181717?style=for-the-badge&logo=github)](https://github.com/kelwin-feitosa)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Kelwin%20Feitosa-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/kelwinfeitosa/)

<br>

### ☕ Transformando café em código.

</div>