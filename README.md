# 🛒 Supermarket Management API

API REST de gerenciamento de supermercado, desenvolvida com **Java 21, Spring Boot 4, PostgreSQL e Spring Security**. Projeto de estudo e portfólio, estruturado como **monólito modular**.

[![CI](https://github.com/kelwin-feitosa/supermarket-management-api/actions/workflows/ci.yml/badge.svg)](https://github.com/kelwin-feitosa/supermarket-management-api/actions/workflows/ci.yml)
[![Documentação](https://github.com/kelwin-feitosa/supermarket-management-api/actions/workflows/docs.yml/badge.svg)](https://github.com/kelwin-feitosa/supermarket-management-api/actions/workflows/docs.yml)

**[📖 Documentação técnica](https://kelwin-feitosa.github.io/supermarket-management-api/)** · **[Código-fonte](https://github.com/kelwin-feitosa/supermarket-management-api)**

## O que o backend faz

- Catálogo de produtos e categorias, fornecedores e compras;
- Carrinhos, registro de vendas e movimentação básica de estoque;
- Usuários, autenticação JWT e perfis de acesso;
- Consultas paginadas e filtradas, validações e tratamento centralizado de erros.

> **Estado atual:** o backend está em evolução. PDV completo, pagamentos, cancelamento, concorrência segura do estoque e frontend administrativo fazem parte do trabalho pendente. Este projeto **não é um sistema comercial pronto para produção**.

## Arquitetura e tecnologias

- **Backend:** Java 21, Spring Boot 4, Spring Data JPA/Hibernate;
- **Persistência:** PostgreSQL, Flyway e transações;
- **Segurança:** Spring Security, JWT e BCrypt;
- **Qualidade:** JUnit, JaCoCo, SonarCloud e GitHub Actions;
- **Documentação de endpoints:** springdoc OpenAPI/Swagger UI.

O código é dividido por capacidades de negócio: `catalog`, `procurement`, `sales`, `inventory`, `identity`, `security` e `shared`. Veja [a arquitetura](https://kelwin-feitosa.github.io/supermarket-management-api/arquitetura/) e [as limitações conhecidas](https://kelwin-feitosa.github.io/supermarket-management-api/roadmap/).

## Executar localmente

Pré-requisitos: **Java 21** e **Docker com Compose**.

```bash
docker compose up -d
export JWT_SECRET="$(openssl rand -hex 32)"
./mvnw spring-boot:run
```

No Windows, use `mvnw.cmd` e configure `JWT_SECRET` no ambiente. Os exemplos acima consideram Linux/macOS.

- Swagger UI (com a aplicação em execução): http://localhost:8080/swagger-ui.html
- OpenAPI: http://localhost:8080/v3/api-docs
- Testes: `./mvnw clean verify`

Mais instruções: [guia de execução](https://kelwin-feitosa.github.io/supermarket-management-api/execucao/).

## Documentação e contribuição

A documentação técnica fica em `docs/`, é compilada com **Zensical** e publicada no **GitHub Pages** após ativar a fonte GitHub Actions nas configurações do repositório. O fluxo de CI valida a construção também nos pull requests.

Consulte o [mapa de rotas](https://kelwin-feitosa.github.io/supermarket-management-api/rotas/), [segurança](https://kelwin-feitosa.github.io/supermarket-management-api/seguranca/), [banco de dados](https://kelwin-feitosa.github.io/supermarket-management-api/banco-de-dados/) e [orientações de contribuição](https://kelwin-feitosa.github.io/supermarket-management-api/contribuindo/).

---

Desenvolvido por [Kelwin Ribeiro Feitosa](https://github.com/kelwin-feitosa).
