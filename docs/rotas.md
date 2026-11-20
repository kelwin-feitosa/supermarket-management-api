# Mapa de rotas do backend

Resumo conferido nas anotações dos controllers da árvore `main` consultada em 20/11/2026. **Não substitui o OpenAPI executável nem testes de autorização**. As restrições listadas são as declaradas com `@PreAuthorize`, antes da aplicação completa de hierarquia e regras de segurança.

| Recurso | Caminho base | Operações anotadas |
| --- | --- | --- |
| Login | `/auth` | `POST /login` |
| Produtos | `/api/produtos` | `GET`, `GET /{id}`, `POST`, `PUT /{id}`, `DELETE /{id}` |
| Categorias | `/categorias` | `GET`, `GET /{id}`, `POST`, `PUT /{id}`, `DELETE /{id}` |
| Fornecedores | `/fornecedores` | `GET`, `GET /{id}`, `POST`, `PUT /{id}`, `DELETE /{id}` |
| Compras | `/compras` | `POST`, `GET`, `GET /{id}` |
| Carrinhos | `/carrinhos` | `POST /itens`, `PUT /itens`, `DELETE /itens`, `DELETE /{idCarrinho}` |
| Vendas | `/vendas` | `POST /{idCarrinho}`, `GET /{idVenda}`, `GET` |
| Usuários | `/usuarios` | `GET`, `GET /{id}`, `POST`, `PUT /{id}`, `DELETE /{id}` |

## Autorização declarada nos controllers

| Área | Anotações atuais |
| --- | --- |
| Produtos e categorias: leitura | `CUSTOMER` ou `CASHIER` |
| Produtos e categorias: criar/atualizar | `STOCK_MANAGER` |
| Produtos e categorias: excluir | `MANAGER` |
| Fornecedores: leitura/criação/atualização | `STOCK_MANAGER` |
| Fornecedores: desativar | `MANAGER` |
| Compras | `STOCK_MANAGER` no controller |
| Carrinhos | `CUSTOMER` no controller |
| Usuários | `MANAGER` no controller |
| Vendas | `MANAGER` no controller; métodos também declaram `CASHIER` (criar) e `STOCK_MANAGER` (consultar) |

**Atenção:** as anotações do controller de vendas em nível de classe e método devem ser revisadas com testes de integração. Não conclua que a autorização real é correta apenas pela tabela.

## Verificação prática

1. Inicie a aplicação: `./mvnw spring-boot:run`.
2. Consulte `http://localhost:8080/v3/api-docs` para o esquema gerado.
3. Consulte `http://localhost:8080/swagger-ui.html` para explorar DTOs, parâmetros e respostas.
4. Automatize testes de autorização para cada papel e para recursos pertencentes a usuários diferentes.

O prefixo `/api` aparece somente em parte das rotas. Essa inconsistência é documentada, **não alterada** nesta etapa.
