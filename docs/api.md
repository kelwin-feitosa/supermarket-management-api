<div class="studio-doc-eyebrow"><span class="studio-doc-eyebrow__mark" aria-hidden="true"></span> 06 / CONTRATOS <span class="studio-doc-eyebrow__suffix">/ SUPERMARKET MANAGEMENT API</span></div>

# API, Swagger e erros

O contrato OpenAPI é gerado pela aplicação em execução. Para verbos e caminhos observados no código, veja o [mapa de rotas](rotas.md).

> Esta documentação descreve o projeto em evolução. Funcionalidades planejadas não devem ser confundidas com comportamento implementado; o código e os testes prevalecem em caso de divergência.

## ⚠️ Tratamento de exceções

A aplicação utiliza tratamento global de exceções para padronizar respostas HTTP.

Inicialmente, o projeto possuía diversas exceções específicas para situações semelhantes.

A arquitetura foi refatorada para reduzir a duplicação e utilizar exceções compartilhadas.

### Exceções compartilhadas

| Exceção | HTTP | Finalidade |
|---|---|---|
| `ResourceNotFoundException` | 404 | Recurso não encontrado |
| `ResourceConflictException` | 409 | Conflito ou duplicidade |
| `BusinessRuleException` | 400 | Violação de regra de negócio |

#### Exemplo: recurso não encontrado

```java
throw new ResourceNotFoundException(
    "Produto não encontrado."
);
```

#### Exemplo: conflito

```java
throw new ResourceConflictException(
    "Já existe uma categoria com esse nome."
);
```

#### Exemplo: regra de negócio

```java
throw new BusinessRuleException(
    "Estoque insuficiente para realizar a operação."
);
```

### Respostas padronizadas

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

#### Benefícios da refatoração

A consolidação de exceções busca:

- Reduzir classes repetitivas;
- Padronizar respostas;
- Simplificar manutenção;
- Evitar duplicação de código;
- Facilitar a evolução da API.

---



## 📚 Documentação da API

A API utiliza Swagger e OpenAPI para documentar os endpoints.

### Swagger UI

Após iniciar a aplicação:

```text
http://localhost:8080/swagger-ui.html
```

### OpenAPI

```text
http://localhost:8080/v3/api-docs
```

Os controllers possuem documentação das operações, parâmetros e respostas HTTP.

A interface Swagger pode ser utilizada para explorar os contratos disponibilizados pela API.

Algumas operações exigem autenticação.

---



## 📡 Principais endpoints

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

#### Autenticação

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

#### Exemplo de consulta paginada

```http
GET /api/produtos?page=0&size=10&sort=nome,asc
Authorization: Bearer <token>
```

> O projeto ainda precisa definir um procedimento seguro de criação do primeiro administrador. Atualmente, o login pressupõe a existência de um usuário válido no banco de dados.

---



## Observação sobre o contrato

A URL do Swagger UI (`/swagger-ui.html`) funciona apenas quando o backend está iniciado. A página do GitHub Pages apresenta documentação **estática** e não executa a API. Os caminhos de recursos não seguem todos o prefixo `/api`, conforme o código atual.
