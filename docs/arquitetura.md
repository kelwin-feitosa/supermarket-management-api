# Arquitetura

O sistema é uma única aplicação Spring Boot organizada por funcionalidades, não uma coleção de microserviços. Veja também [ADR-0001](decisoes/0001-monolito-modular.md).

> Esta documentação descreve o projeto em evolução. Funcionalidades planejadas não devem ser confundidas com comportamento implementado; o código e os testes prevalecem em caso de divergência.

## 🏗️ Arquitetura

### Monólito modular

A aplicação utiliza uma arquitetura de **monólito modular**, organizada por funcionalidades de negócio.

Essa abordagem mantém a simplicidade operacional de uma única aplicação Spring Boot, mas melhora a organização e a identificação das responsabilidades de cada módulo.

A separação por funcionalidades substitui a organização anterior, em que controllers, services, repositories e entidades de diferentes áreas ficavam agrupados em diretórios globais.

#### Organização dos módulos

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

### Responsabilidades dos módulos

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

#### Fluxo de processamento

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

#### Decisão arquitetural

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



## 🧩 Decisões de arquitetura

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

### Por que um monólito modular?

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

### Por que consolidar as exceções?

As exceções anteriores repetiam estruturas semelhantes para recursos diferentes.

A utilização de exceções compartilhadas simplifica a modelagem dos erros e reduz código repetitivo.

As mensagens específicas continuam identificando a causa do problema.

### Por que manter o PostgreSQL?

O PostgreSQL oferece recursos adequados às necessidades atuais do projeto, incluindo:

- Transações;
- Integridade referencial;
- Restrições;
- Consultas relacionais;
- Índices;
- Mecanismos de controle de concorrência.

Esses recursos serão importantes para a evolução das operações de estoque e vendas.

---

