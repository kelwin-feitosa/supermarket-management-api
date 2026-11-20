# Regras de negócio

As operações de compra e venda são transacionais, mas isso **não garante por si só** integridade diante de vendas concorrentes. Confira o fluxo e as limitações abaixo.

> Esta documentação descreve o projeto em evolução. Funcionalidades planejadas não devem ser confundidas com comportamento implementado; o código e os testes prevalecem em caso de divergência.

## 🧠 Regras de negócio

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

### Consistência das operações

Compras e vendas utilizam operações transacionais para preservar a consistência das alterações realizadas durante uma operação.

Por exemplo, ao registrar uma venda, o sistema precisa relacionar:

1. O carrinho utilizado;
2. Os itens vendidos;
3. As quantidades;
4. Os preços;
5. A atualização do estoque;
6. O registro da venda.

Uma falha durante a transação não deve resultar em uma operação parcialmente concluída.

#### Limitações atuais

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

