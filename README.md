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
- Testes automatizados.

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
- ✅ Testes unitários dos Services
- ✅ Validação das regras de negócio

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

# 📚 Documentação da API

A API utiliza Swagger/OpenAPI para documentação dos endpoints.

A documentação permite visualizar:

- Endpoints disponíveis
- Parâmetros necessários
- Estrutura dos DTOs
- Códigos de resposta HTTP
- Exemplos de requisições e respostas

Tecnologia utilizada:

- Springdoc OpenAPI
- Swagger UI

---

# 🌐 Endpoints

## Categorias

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/categorias` | Lista todas as categorias |
| GET | `/categorias/{id}` | Busca categoria por ID |
| POST | `/categorias` | Cadastra uma categoria |
| PUT | `/categorias/{id}` | Atualiza uma categoria |
| DELETE | `/categorias/{id}` | Remove uma categoria |

---

## Produtos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/produtos` | Lista todos os produtos |
| GET | `/produtos/{id}` | Busca produto por ID |
| POST | `/produtos` | Cadastra um produto |
| PUT | `/produtos/{id}` | Atualiza um produto |
| DELETE | `/produtos/{id}` | Remove um produto |

---

## Clientes

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/clientes` | Lista todos os clientes |
| GET | `/clientes/{id}` | Busca cliente por ID |
| POST | `/clientes` | Cadastra um cliente |
| PUT | `/clientes/{id}` | Atualiza um cliente |
| DELETE | `/clientes/{id}` | Remove um cliente |

---

## Fornecedores

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/fornecedores` | Lista fornecedores |
| GET | `/fornecedores/ativos` | Lista fornecedores ativos |
| GET | `/fornecedores/{id}` | Busca fornecedor por ID |
| POST | `/fornecedores` | Cadastra fornecedor |
| PUT | `/fornecedores/{id}` | Atualiza fornecedor |
| DELETE | `/fornecedores/{id}` | Desativa fornecedor |

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
| GET | `/compras` | Lista todas as compras |

---

## Vendas

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/vendas/{idCarrinho}` | Finaliza uma venda utilizando o carrinho |
| GET | `/vendas/{idVenda}` | Busca uma venda por ID |
| GET | `/vendas` | Lista todas as vendas |

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

A API possui documentação interativa utilizando Swagger/OpenAPI.

Após executar o projeto, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

Através do Swagger é possível:

- Visualizar todos os endpoints disponíveis.
- Consultar parâmetros necessários.
- Testar requisições diretamente pelo navegador.
- Visualizar respostas de sucesso e erro.

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
│   ├── Requests
│   └── Responses
│
├── exception
│   ├── GlobalExceptionHandler.java
│   └── Exceptions personalizadas
│
├── model
│   └── Entidades JPA
│
├── repository
│   └── Interfaces Spring Data JPA
│
├── service
│   └── Regras de negócio da aplicação
│
└── MeuPrimeiroProjetoApplication.java
```

---

# 🧪 Testes

O projeto possui testes unitários utilizando **JUnit 5** e **Mockito**, com foco na validação das regras de negócio implementadas nos Services.

Implementados:

- Testes unitários dos Services.
- Validação de criação, atualização, busca e remoção de entidades.
- Testes de exceções personalizadas.
- Testes das regras de duplicidade e validações de negócio.

Próximas implementações:

- Testes de integração dos Controllers utilizando MockMvc.
- Testes dos principais fluxos completos da aplicação.

---

# 🔐 Segurança

Melhorias planejadas:

- Implementação de autenticação utilizando Spring Security.
- Controle de acesso baseado em perfis de usuário.
- Proteção dos endpoints através de JWT.
- Gerenciamento de usuários e permissões.

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

- Finalizar documentação completa com Swagger/OpenAPI.
- Criar testes automatizados.
- Implementar autenticação e autorização.
- Adicionar gerenciamento de usuários.
- Implementar histórico de movimentações de estoque.
- Melhorar logs da aplicação.
- Configurar CI/CD utilizando GitHub Actions.
- Realizar deploy da API.

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

---

# 👨‍💻 Autor

**Kelwin Ribeiro Feitosa**

Estudante de Ciência da Computação com foco em desenvolvimento backend utilizando Java e Spring Boot.

Projeto desenvolvido para estudo, prática profissional e construção de portfólio.