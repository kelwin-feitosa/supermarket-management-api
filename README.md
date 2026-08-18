# 🛒 API REST para Gerenciamento de Supermercado

API REST desenvolvida com **Java 21** e **Spring Boot 3** com o objetivo de criar um sistema backend para gerenciamento de operações comerciais de um supermercado.

O projeto aplica boas práticas de desenvolvimento backend utilizando arquitetura em camadas, Spring Data JPA, Hibernate, Jakarta Validation, DTOs, documentação com Swagger/OpenAPI, tratamento global de exceções e regras de negócio.

O sistema contempla o gerenciamento de **produtos, categorias, clientes, fornecedores, carrinhos de compras, compras e vendas**, sendo desenvolvido com foco em organização, escalabilidade e práticas utilizadas no desenvolvimento de aplicações profissionais.

---

# 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- Hibernate
- Jakarta Validation
- Springdoc OpenAPI (Swagger)
- PostgreSQL
- H2 Database
- Maven
- Git e GitHub
- Postman
- Linux
- JUnit 5
- Mockito

---

# 🏗️ Arquitetura

A aplicação segue uma arquitetura em camadas:

- **Controller** → responsável pela exposição dos endpoints REST e comunicação com o cliente.
- **Service** → contém as regras de negócio e validações da aplicação.
- **Repository** → realiza a comunicação com o banco de dados utilizando Spring Data JPA.
- **Model** → entidades persistidas utilizando JPA/Hibernate.
- **DTO** → objetos utilizados para entrada e saída de dados, evitando exposição direta das entidades.
- **Exception Handler** → tratamento centralizado de erros e respostas padronizadas.

---

# 🧠 Conceitos aplicados

Durante o desenvolvimento foram aplicados conceitos importantes de desenvolvimento backend:

- Programação Orientada a Objetos.
- Princípios SOLID.
- Separação de responsabilidades.
- Injeção de dependências.
- Persistência com JPA/Hibernate.
- Modelagem de banco relacional.
- DTO Pattern.
- Tratamento global de exceções.
- Validação de dados.
- Testes unitários com JUnit 5 e Mockito.
- Paginação e ordenação com Spring Data.
- Filtros dinâmicos utilizando JPA Specifications.
- Consultas dinâmicas com Criteria API.
- Testes de Specifications.
- Paginação e ordenação com Spring Data.
- Filtros dinâmicos utilizando JPA Specifications.
- Consultas dinâmicas com Criteria API.
- Composição de Specifications.

---

# 📂 Modelagem do Sistema

O sistema possui as seguintes entidades:

- Categoria
- Produto
- Cliente
- Fornecedor
- Carrinho
- ItemCarrinho
- Compra
- ItemCompra
- Venda
- ItemVenda

## Principais relacionamentos

- Uma **Categoria** possui vários produtos.
- Um **Produto** pertence a uma categoria.
- Um **Cliente** possui um carrinho.
- Um **Carrinho** possui vários itens.
- Um **ItemCarrinho** representa um produto dentro de um carrinho.
- Uma **Venda** pertence a um cliente.
- Uma **Venda** possui vários itens vendidos.
- Uma **Compra** pertence a um fornecedor.
- Uma **Compra** possui vários itens comprados.

---

# 📋 Funcionalidades

## Implementadas

- ✅ Modelagem das entidades utilizando JPA/Hibernate
- ✅ Relacionamentos entre entidades
- ✅ DTOs de Request e Response
- ✅ Validação utilizando Jakarta Validation
- ✅ CRUD de produtos
- ✅ CRUD de categorias
- ✅ CRUD de clientes
- ✅ CRUD de fornecedores
- ✅ Registro de compras
- ✅ Gerenciamento de carrinho de compras
- ✅ Adição de produtos ao carrinho
- ✅ Alteração de quantidade dos itens
- ✅ Remoção de itens do carrinho
- ✅ Limpeza completa do carrinho
- ✅ Cálculo automático de subtotal dos itens
- ✅ Cálculo automático do valor total
- ✅ Registro de vendas
- ✅ Conversão de carrinho em venda
- ✅ Atualização automática de estoque após venda
- ✅ Validação de estoque disponível
- ✅ Tratamento global de exceções
- ✅ Documentação da API utilizando Swagger/OpenAPI
- ✅ Testes unitários dos Services utilizando JUnit 5 e Mockito
- ✅ Testes das Specifications utilizando JUnit 5
- ✅ 64 testes automatizados
- ✅ Validação das regras de negócio
- ✅ Paginação das listagens utilizando Spring Data Pageable
- ✅ Ordenação dos resultados
- ✅ Filtros dinâmicos utilizando JPA Specifications
- ✅ Filtros por múltiplos critérios
- ✅ Integração entre Specifications e paginação

---

# 🔎 Filtros e Paginação

As listagens da API utilizam `Pageable` e **JPA Specifications**, permitindo combinar filtros, paginação e ordenação de forma dinâmica.

Exemplo de requisição:

```text
GET /produtos?nome=arroz&categoriaId=1&page=0&size=10&sort=nome,asc
```

Os filtros são representados por DTOs específicos:

- `ProdutoFiltro`
- `CategoriaFiltro`
- `ClienteFiltro`
- `FornecedorFiltro`
- `CompraFiltro`
- `VendaFiltro`

A implementação utiliza `JpaSpecificationExecutor` e Specifications baseadas na Criteria API.

---

# 🛡️ Validação

Os dados recebidos pela API são validados utilizando **Jakarta Validation**.

Validações implementadas:

- Campos obrigatórios
- Valores positivos
- E-mail válido
- CNPJ com formato correto
- Estoque não negativo
- Limitação de tamanho de campos
- Validação de quantidade de produtos
- Validação de regras comerciais

---

# 📦 Regras de Negócio

Algumas regras implementadas no sistema:

## Carrinho

- Um cliente pode possuir um carrinho de compras.
- Produtos adicionados ao carrinho possuem quantidade e preço registrado no momento da inclusão.
- Caso o produto já exista no carrinho, sua quantidade é atualizada.
- Não é permitido adicionar quantidade superior ao estoque disponível.
- O valor total do carrinho é calculado automaticamente com base nos itens adicionados.

---

## Venda

- Não é possível finalizar uma venda com carrinho vazio.
- Ao realizar uma venda:

  - Os itens do carrinho são transformados em itens da venda.
  - O estoque dos produtos é atualizado automaticamente.
  - O valor total da venda é calculado.
  - O carrinho é limpo após a conclusão.

---

## Compra

- Uma compra deve possuir um fornecedor válido.
- Uma compra deve possuir pelo menos um item.
- Os produtos comprados possuem quantidade e preço de compra registrados.
- O valor total da compra é calculado automaticamente através dos itens.

---

## Estoque

- O sistema verifica a disponibilidade antes de adicionar produtos ao carrinho.
- A quantidade em estoque não pode ser negativa.
- A baixa do estoque ocorre automaticamente após uma venda.
- Compras podem ser utilizadas para reposição de estoque.

---

# ❌ Tratamento de Erros

A API possui tratamento global de exceções utilizando `@ControllerAdvice`.

Todas as respostas de erro seguem um padrão:

```json
{
  "mensagem": "Dados enviados não passam nas regras de validação.",
  "detalhes": "O preço deve ser maior que zero.",
  "timestamp": "2026-07-09T20:05:32"
}
```

Exceções tratadas:

- Produto não encontrado
- Categoria não encontrada
- Cliente não encontrado
- Fornecedor não encontrado
- Carrinho não encontrado
- Item do carrinho não encontrado
- Venda não encontrada
- Compra não encontrada
- Produto já existente
- Categoria já existente
- Cliente já cadastrado
- CNPJ já cadastrado
- Estoque insuficiente
- Carrinho vazio
- Erros de validação
- JSON inválido

---

# 🌐 Endpoints

## Categorias

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/categorias` | Lista categorias com filtros, paginação e ordenação |
| GET | `/categorias/{id}` | Busca categoria por ID |
| POST | `/categorias` | Cadastra uma categoria |
| PUT | `/categorias/{id}` | Atualiza uma categoria |
| DELETE | `/categorias/{id}` | Remove uma categoria |

---

## Produtos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/produtos` | Lista produtos com filtros, paginação e ordenação |
| GET | `/produtos/{id}` | Busca produto por ID |
| POST | `/produtos` | Cadastra um produto |
| PUT | `/produtos/{id}` | Atualiza um produto |
| DELETE | `/produtos/{id}` | Remove um produto |

---

## Clientes

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/clientes` | Lista clientes com filtros, paginação e ordenação |
| GET | `/clientes/{id}` | Busca cliente por ID |
| POST | `/clientes` | Cadastra um cliente |
| PUT | `/clientes/{id}` | Atualiza um cliente |
| DELETE | `/clientes/{id}` | Remove um cliente |

---

## Fornecedores

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/fornecedores` | Lista fornecedores com filtros, paginação e ordenação |
| GET | `/fornecedores/{id}` | Busca fornecedor por ID |
| POST | `/fornecedores` | Cadastra fornecedor |
| PUT | `/fornecedores/{id}` | Atualiza fornecedor |
| DELETE | `/fornecedores/{id}` | Desativa ou remove fornecedor |

---

## Carrinhos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/carrinhos/{idCliente}` | Cria um carrinho para um cliente |
| POST | `/carrinhos/itens` | Adiciona um produto ao carrinho |
| PUT | `/carrinhos/itens` | Atualiza a quantidade de um item |
| DELETE | `/carrinhos/itens` | Remove um item do carrinho |
| DELETE | `/carrinhos/{idCarrinho}` | Remove todos os itens do carrinho |

---

## Compras

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/compras` | Registra uma nova compra |
| GET | `/compras/{id}` | Busca uma compra por ID |
| GET | `/compras` | Lista compras com filtros, paginação e ordenação |

---

## Vendas

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/vendas/{idCarrinho}` | Finaliza uma venda utilizando o carrinho |
| GET | `/vendas/{idVenda}` | Busca uma venda por ID |
| GET | `/vendas` | Lista vendas com filtros, paginação e ordenação |

---

# 📥 Exemplos de Requisição

## Cadastro de Produto

```json
{
  "nome": "Arroz 5kg",
  "preco": 29.90,
  "descricao": "Arroz branco tipo 1",
  "quantidadeEstoque": 50,
  "categoriaId": 1
}
```

---

## Cadastro de Cliente

```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "telefone": "61999999999"
}
```

---

## Adicionar Produto ao Carrinho

```json
{
  "carrinhoId": 1,
  "produtoId": 1,
  "quantidade": 2
}
```

---

## Registrar Compra

```json
{
  "fornecedorId": 1,
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 100,
      "precoCompra": 20.00
    }
  ]
}
```

---

# 📤 Exemplos de Respostas

## Produto

```json
{
  "id": 1,
  "nome": "Arroz 5kg",
  "preco": 29.90,
  "descricao": "Arroz branco tipo 1",
  "quantidadeEstoque": 50,
  "categoriaId": 1,
  "dataCadastro": "2026-07-15T14:30:52"
}
```

---

## Carrinho

```json
{
  "id": 1,
  "valorTotal": 59.80,
  "itens": [
    {
      "id": 1,
      "produtoId": 1,
      "nomeProduto": "Arroz 5kg",
      "quantidade": 2,
      "precoUnitario": 29.90,
      "subtotal": 59.80
    }
  ]
}
```

---

## Venda

```json
{
  "id": 1,
  "clienteId": 1,
  "dataVenda": "2026-07-23T15:30:00",
  "valorTotal": 59.80,
  "itens": [
    {
      "produtoId": 1,
      "nomeProduto": "Arroz 5kg",
      "quantidade": 2,
      "precoUnitario": 29.90,
      "subtotal": 59.80
    }
  ]
}
```

---

# ▶️ Como Executar o Projeto

## Pré-requisitos

Antes de executar a aplicação, é necessário possuir instalado:

- Java 21
- Maven
- Git

---

## Clonar o repositório

```bash
git clone https://github.com/kelwin-feitosa/api-gerenciamento-supermercado.git
```

Acesse a pasta do projeto:

```bash
cd api-gerenciamento-supermercado
```

---

## Executar a aplicação

Utilizando Maven:

```bash
./mvnw spring-boot:run
```

Ou pelo Maven instalado:

```bash
mvn spring-boot:run
```

Após iniciar, a API estará disponível em:

```
http://localhost:8080
```

---

# 📚 Documentação da API

A API utiliza **Springdoc OpenAPI** e **Swagger UI** para gerar documentação interativa dos endpoints.

Após iniciar a aplicação, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

No Swagger é possível:

- Visualizar todos os endpoints disponíveis.
- Consultar parâmetros e validações.
- Visualizar os DTOs de requisição e resposta.
- Testar requisições diretamente pelo navegador.
- Consultar códigos de resposta HTTP.
- Visualizar exemplos de requisições e respostas.

Tecnologias utilizadas:

- Springdoc OpenAPI
- Swagger UI

---

# 🗂️ Estrutura do Projeto

```
src/main/java/com/exemplo/meu_primeiro_projeto

├── config
│   └── OpenApiConfig.java
│
├── controller
│   ├── ProdutoController.java
│   ├── CategoriaController.java
│   ├── ClienteController.java
│   ├── FornecedorController.java
│   ├── CarrinhoController.java
│   ├── CompraController.java
│   └── VendaController.java
│
├── dto
│   ├── filter
│   │   ├── ProdutoFiltro.java
│   │   ├── CategoriaFiltro.java
│   │   ├── ClienteFiltro.java
│   │   ├── FornecedorFiltro.java
│   │   ├── CompraFiltro.java
│   │   └── VendaFiltro.java
│   │
│   ├── request
│   └── response
│
├── exception
│   ├── GlobalExceptionHandler.java
│   └── Exceções personalizadas
│
├── mapper
│   └── Mappers das entidades
│
├── model
│   └── Entidades JPA
│
├── repository
│   ├── specification
│   │   ├── ProdutoSpecification.java
│   │   ├── CategoriaSpecification.java
│   │   ├── ClienteSpecification.java
│   │   ├── FornecedorSpecification.java
│   │   ├── CompraSpecification.java
│   │   └── VendaSpecification.java
│   │
│   └── Repositories Spring Data JPA
│
├── service
│   └── Regras de negócio da aplicação
│
└── MeuPrimeiroProjetoApplication.java
```

---

# 🧪 Testes

O projeto possui testes automatizados utilizando **JUnit 5** e **Mockito**, com foco nas regras de negócio, paginação e consultas dinâmicas.

Implementados:

- Testes unitários dos Services.
- Testes das regras de negócio.
- Testes de exceções personalizadas.
- Testes de duplicidade e validações.
- Testes de paginação dos Services.
- Testes de filtros utilizando Specifications.
- Testes das Specifications.
- Utilização de Mockito para isolamento das dependências.

## Resultado

Todos os testes automatizados estão passando atualmente.

- 64 testes executados
- 0 falhas
- 0 erros
- 0 testes ignorados

Próximas implementações:

- Testes dos Controllers utilizando MockMvc.
- Testes de integração dos principais fluxos da aplicação.

---

# 🔐 Segurança

Próximas implementações:

- Implementação de Spring Security.
- Autenticação utilizando JWT.
- Controle de acesso baseado em perfis de usuário.
- Gerenciamento de usuários e permissões.
- Proteção dos endpoints autenticados.

---

# 🐳 Infraestrutura e Deploy

Melhorias planejadas:

- Migrar banco H2 para PostgreSQL.
- Configurar ambiente utilizando Docker.
- Criar Dockerfile da aplicação.
- Utilizar Docker Compose para subir aplicação e banco.
- Preparar deploy em ambiente cloud.

---

# 🚀 Próximos Passos

- Implementar autenticação e autorização utilizando Spring Security e JWT.
- Adicionar gerenciamento de usuários.
- Implementar testes dos Controllers utilizando MockMvc.
- Implementar testes de integração.
- Melhorar documentação da autenticação no Swagger.
- Implementar histórico de movimentações de estoque.
- Melhorar logs da aplicação.
- Expandir CI/CD utilizando GitHub Actions.
- Preparar deploy da API.

---

# 🎯 Objetivo Profissional

Este projeto foi desenvolvido com o objetivo de aplicar conhecimentos utilizados no desenvolvimento backend profissional, incluindo:

- Construção de APIs REST.
- Arquitetura em camadas.
- Desenvolvimento com Spring Boot.
- Persistência utilizando JPA/Hibernate.
- Modelagem de banco de dados relacional.
- Aplicação de regras de negócio.
- Tratamento de exceções.
- Boas práticas de organização de código.
- Desenvolvimento orientado a testes unitários.

---

# 👨‍💻 Autor

**Kelwin Ribeiro Feitosa**

Estudante de Ciência da Computação com foco em desenvolvimento backend utilizando Java e Spring Boot.

Projeto desenvolvido para estudo, prática profissional e construção de portfólio.