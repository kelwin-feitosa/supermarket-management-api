<div class="studio-doc-eyebrow"><span class="studio-doc-eyebrow__mark" aria-hidden="true"></span> 10 / EVOLUÇÃO <span class="studio-doc-eyebrow__suffix">/ SUPERMARKET MANAGEMENT API</span></div>

# Roadmap e limitações

Itens deste documento são **planejados**, salvo marcação expressa de implementação. A priorização deve acompanhar necessidades demonstradas, testes e capacidade de manutenção.

> Esta documentação descreve o projeto em evolução. Funcionalidades planejadas não devem ser confundidas com comportamento implementado; o código e os testes prevalecem em caso de divergência.

## 🗺️ Roadmap

O desenvolvimento está organizado em etapas incrementais.

A ordem considera dependências entre funcionalidades e a necessidade de manter uma aplicação funcional durante sua evolução.

### Fase 1 — Reorganização arquitetural

- [x] Reorganizar o código por módulos de negócio;
- [x] Preservar a separação entre controllers, services e repositories;
- [x] Consolidar exceções específicas;
- [x] Atualizar referências e imports;
- [x] Validar compilação;
- [x] Executar testes automatizados localmente;
- [ ] Confirmar o funcionamento da refatoração no CI;
- [ ] Revisar dependências entre módulos;
- [ ] Validar contratos HTTP e compatibilidade das respostas.

### Fase 2 — Segurança e identidade

- [ ] Corrigir a configuração de testes no CI;
- [ ] Revisar a hierarquia de permissões;
- [ ] Criar procedimento seguro de inicialização do administrador;
- [ ] Separar funcionários e clientes no modelo de domínio;
- [ ] Permitir clientes não identificados nas vendas presenciais;
- [ ] Proteger recursos contra acesso indevido;
- [ ] Ampliar testes de autenticação e autorização.

### Fase 3 — Catálogo e estoque

- [ ] Introduzir identificação de produtos por SKU;
- [ ] Adicionar código de barras;
- [ ] Suportar unidades e quantidades fracionárias;
- [ ] Implementar estoque mínimo;
- [ ] Registrar movimentações de estoque;
- [ ] Implementar ajustes de inventário;
- [ ] Impedir alterações de estoque sem rastreabilidade;
- [ ] Implementar controle de concorrência;
- [ ] Introduzir inativação de produtos.

### Fase 4 — Compras e vendas

- [ ] Adaptar carrinhos para vendas presenciais;
- [ ] Registrar o funcionário responsável pela operação;
- [ ] Registrar formas de pagamento simuladas;
- [ ] Implementar cancelamento de vendas;
- [ ] Registrar motivos de cancelamento;
- [ ] Implementar devolução controlada de mercadorias ao estoque;
- [ ] Suportar descontos autorizados;
- [ ] Ampliar testes de transações e integridade.

### Fase 5 — Gestão administrativa

- [ ] Criar indicadores gerenciais;
- [ ] Exibir produtos com estoque baixo;
- [ ] Apresentar histórico de movimentações;
- [ ] Consultar vendas por período;
- [ ] Implementar relatórios básicos;
- [ ] Melhorar a rastreabilidade das operações.

### Fase 6 — Frontend

Tecnologias previstas:

- React;
- TypeScript;
- Vite;
- Tailwind CSS;
- React Router.

O Google Stitch poderá auxiliar na prototipagem das interfaces.

#### Telas planejadas

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

### Fase 7 — Entrega

- [ ] Ampliar testes automatizados;
- [ ] Configurar ambiente completo com Docker;
- [ ] Revisar segurança;
- [ ] Validar todos os fluxos principais;
- [ ] Preparar dados fictícios para demonstração;
- [ ] Documentar decisões arquiteturais;
- [ ] Criar demonstração reproduzível;
- [ ] Preparar o projeto para apresentação no portfólio.

---



## 🔮 Possíveis evoluções futuras

As funcionalidades abaixo não fazem parte do escopo inicial.

Sua implementação dependerá de necessidade demonstrada e avaliação de complexidade.

#### Gestão de mercadorias

- Controle de lotes;
- Data de validade;
- Produtos perecíveis;
- Rastreabilidade;
- Controle de perdas;
- Reposição automatizada;
- Gestão de múltiplos estoques.

#### Operação comercial

- Promoções;
- Programas de fidelidade;
- Devoluções parciais;
- Trocas;
- Integração com leitores de código de barras;
- Integração com balanças;
- Integração com impressoras;
- Pagamentos reais.

#### Integrações

- Emissão de documentos fiscais;
- Sistemas de pagamento;
- Integrações contábeis;
- Serviços externos;
- Comércio eletrônico.

#### Inteligência Artificial

Possibilidades futuras incluem:

- Previsão de demanda;
- Sugestão de reposição;
- Análise de padrões de vendas;
- Identificação de anomalias;
- Apoio à tomada de decisões.

Essas funcionalidades serão avaliadas somente após a consolidação do sistema principal.

---

