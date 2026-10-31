# Arquitetura — refatoração estrutural (fase 1)

## Decisões aprovadas de produto
- Portfólio com possibilidade de adaptação comercial futura.
- Gestão de **um supermercado físico** com PDV; sem e-commerce no MVP.
- Cliente identificado é opcional; venda pode ocorrer sem cadastro do cliente.
- Venda iniciada pelo operador de caixa, não por carrinho persistente do cliente.
- Perfis: administrador, gerente, estoque e caixa.
- Mercadorias por unidade e peso; movimentações de estoque rastreáveis.
- Recebimento de compras imediato no MVP.
- Registrar a forma de pagamento sem processar pagamento real.
- Cancelamento autorizado e rastreado; descontos autorizados.

**Essas decisões exigirão alterações de domínio e banco em etapas posteriores.**
**Nenhuma delas foi implementada por este script.**

## Estrutura desta fase
O sistema continua um monólito Spring Boot, agora organizado por funcionalidades:

```text
com.exemplo.meu_primeiro_projeto/
├── catalog/       # Produto e Categoria
├── procurement/   # Compra e Fornecedor
├── sales/         # Carrinho e Venda (modelo atual, temporário)
├── identity/      # Usuario e Role (modelo atual, temporário)
├── inventory/     # EstoqueService
├── security/      # JWT, autenticação e filtros
├── config/        # Configurações Spring
└── shared/        # Exceções, cálculo de preços e utilitários
```

Dentro de cada módulo permanecem `controller`, `dto`, `model`, `service`,
`repository` e `mapper` conforme necessário. **Não são microserviços**.

## Política para erros
- `ResourceNotFoundException` → HTTP 404.
- `ResourceConflictException` → HTTP 409.
- `BusinessRuleException` → HTTP 400.
- `MethodArgumentNotValidException` e JSON inválido → HTTP 400.
- Exceções imprevistas → HTTP 500, sem detalhes internos.

O contrato JSON de `RespostaErro` mantém os três campos existentes. **A string
`mensagem` será genérica**; `detalhes` preserva as mensagens específicas que os
serviços já forneciam. Consumidores que dependam do texto exato devem ajustar-se.

## Limites desta mudança
- Não modifica schema, DTOs, endpoints, autenticação, autorização ou lógica de vendas.
- Não resolve bugs anteriores (CI/JWT, autorização, concorrência de estoque,
  inicialização de administrador, ordenação de compras).
- Não adiciona transações nem corrige validação de cliente/carrinho.

## Validação necessária após executar
1. Inicialize PostgreSQL (`docker compose up -d`).
2. Configure a variável `JWT_SECRET` com chave válida de 32+ bytes.
3. Rode `./mvnw clean verify` e revise os resultados.
4. Execute casos reais de cadastro, compras, venda e autorização.
5. Revise o diff e só depois faça commit.

## Fases seguintes (sem executar nesta refatoração)
1. Garantir CI/ambiente de testes determinístico e corrigir inconsistências de segurança.
2. Separar `Cliente` de `Funcionario`/`Usuario`, ajustar venda anônima e caixa.
3. Movimentações de estoque, concorrência e rastreabilidade.
4. Pagamento simulado, cancelamento e descontos com regras explícitas.
5. Expor contratos para o frontend e desenvolver telas com Stitch + React.
