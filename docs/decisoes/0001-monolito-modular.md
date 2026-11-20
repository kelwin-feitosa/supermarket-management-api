# ADR-0001 — Adotar monólito modular

- **Status:** adotada na arquitetura atual;
- **Contexto:** projeto acadêmico/portfólio com um backend Spring Boot, regras de negócio de catálogo, compras e vendas e objetivo de aprendizado;
- **Decisão:** manter um único processo/artefato Spring Boot, organizado em pacotes de funcionalidades (`catalog`, `procurement`, `sales`, `inventory`, `identity`, `security`, `shared`).
- **Motivação:** separar responsabilidades sem introduzir custos de distribuição, redes, observabilidade e implantação de múltiplos serviços.
- **Consequências positivas:** desenvolvimento e operação local mais simples, transações na mesma aplicação e estrutura mais fácil de navegar.
- **Limitações:** pacotes não garantem independência dos módulos; dependências cruzadas e fronteiras transacionais precisam de atenção.
- **Alternativas:** organização global por camadas e microserviços. A primeira foi substituída por dificultar navegação por domínio; os segundos não apresentam benefício demonstrado para o escopo atual.

**Quando reconsiderar:** somente se houver requisitos comprovados de escalonamento, isolamento ou implantação independente que justifiquem o custo operacional.

Veja também [arquitetura](../arquitetura.md) e o [registro histórico da fase 1](../ARCHITECTURE.md).
