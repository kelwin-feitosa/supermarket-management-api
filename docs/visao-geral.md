<div class="studio-doc-eyebrow"><span class="studio-doc-eyebrow__mark" aria-hidden="true"></span> 01 / VISÃO GERAL <span class="studio-doc-eyebrow__suffix">/ SUPERMARKET MANAGEMENT API</span></div>

# Visão geral

Entenda o objetivo funcional, as capacidades existentes e os limites do escopo.

> Esta documentação descreve o projeto em evolução. Funcionalidades planejadas não devem ser confundidas com comportamento implementado; o código e os testes prevalecem em caso de divergência.

## 🎯 Objetivos

O projeto possui dois objetivos complementares.

#### Objetivo funcional

Desenvolver um sistema que permita administrar as operações fundamentais de um supermercado, desde o cadastro de produtos até o registro de vendas e o acompanhamento do estoque.

#### Objetivo técnico

Aplicar e aprofundar conhecimentos em desenvolvimento de software, com atenção especial a:

- Programação Orientada a Objetos;
- Princípios SOLID;
- Separação de responsabilidades;
- Modelagem de domínio;
- Arquitetura modular;
- APIs REST;
- Spring Boot;
- Spring Data JPA;
- Hibernate;
- Transações;
- Validação de dados;
- Autenticação e autorização;
- Testes automatizados;
- Integração contínua;
- Persistência relacional;
- Tratamento de erros;
- Documentação;
- Qualidade e manutenibilidade.

O projeto também serve como ambiente de aprendizagem para identificar problemas reais de arquitetura, refatorar código existente e avaliar decisões de implementação.

---



## 🚀 Funcionalidades implementadas

Esta seção descreve as funcionalidades presentes no backend atual.

Funcionalidades planejadas para as próximas versões são apresentadas separadamente no roadmap.

### 📦 Produtos

O módulo de catálogo permite:

- Cadastrar produtos;
- Atualizar informações;
- Buscar produtos por identificador;
- Listar produtos com paginação;
- Aplicar filtros dinâmicos;
- Associar produtos a categorias;
- Definir preços;
- Registrar quantidades em estoque;
- Validar dados de entrada;
- Verificar duplicidade de nomes;
- Excluir produtos.

Os produtos possuem informações como:

- Identificador;
- Nome;
- Descrição;
- Preço;
- Quantidade em estoque;
- Categoria;
- Data de cadastro.

> **Evolução planejada:** identificação por SKU e código de barras, estoque mínimo, unidades de medida e desativação de produtos.

### 🏷️ Categorias

O sistema permite:

- Cadastrar categorias;
- Atualizar informações;
- Buscar por identificador;
- Listar com paginação;
- Aplicar filtros;
- Verificar duplicidade;
- Excluir categorias, respeitando as regras implementadas.

As categorias permitem organizar os produtos e facilitar consultas.

### 👤 Usuários

O módulo de identidade contempla:

- Cadastro;
- Atualização;
- Consulta;
- Listagem;
- Filtros;
- Validação de informações;
- Criação automática de carrinho;
- Armazenamento de senhas com BCrypt;
- Associação de perfis de acesso.

O modelo atual utiliza a entidade `Usuario` tanto para autenticação quanto para representar o usuário associado a carrinhos e vendas.

> **Evolução planejada:** distinguir funcionários de clientes e permitir vendas presenciais sem cadastro obrigatório do comprador.

### 🏭 Fornecedores

O sistema permite:

- Cadastrar fornecedores;
- Atualizar dados;
- Consultar fornecedores;
- Listar com filtros e paginação;
- Verificar duplicidade de CNPJ;
- Controlar a situação ativa ou inativa dos fornecedores.

Fornecedores são associados às compras de mercadorias.

### 🛒 Carrinhos

O módulo de vendas possui regras para:

- Adicionar produtos;
- Alterar quantidades;
- Remover itens;
- Limpar carrinhos;
- Calcular subtotais;
- Calcular valores totais;
- Verificar disponibilidade de estoque.

Atualmente, os carrinhos estão associados a usuários cadastrados.

> **Evolução planejada:** adaptar esse modelo para permitir que funcionários registrem vendas presenciais sem exigir um carrinho permanente por cliente.

### 🧾 Vendas

O sistema permite:

- Registrar vendas a partir de carrinhos;
- Associar vendas a usuários;
- Registrar itens vendidos;
- Preservar os preços utilizados nos itens;
- Calcular subtotais;
- Calcular o valor total;
- Reduzir o estoque;
- Consultar vendas;
- Listar vendas com filtros e paginação.

A realização de vendas utiliza transações para agrupar alterações relacionadas à operação.

> **Ainda não implementado:** registro de pagamentos, cancelamentos, devoluções, identificação independente do operador de caixa e controle adequado de vendas simultâneas.

### 🚚 Compras

O módulo de compras permite:

- Registrar compras de mercadorias;
- Associar compras a fornecedores;
- Registrar produtos e quantidades;
- Armazenar preços de compra;
- Calcular subtotais;
- Calcular valores totais;
- Aumentar o estoque;
- Consultar compras;
- Listar compras com filtros e paginação.

O fluxo atual considera o registro da compra como entrada de mercadorias.

A separação entre pedido de compra e recebimento físico poderá ser implementada futuramente.

### 📊 Consultas e paginação

A aplicação utiliza Spring Data JPA para realizar consultas paginadas e ordenadas.

Também utiliza Specifications para combinar critérios de pesquisa dinamicamente.

Entre os critérios disponíveis, conforme o recurso, estão:

- Nome;
- Categoria;
- Faixa de preços;
- Quantidade em estoque;
- Fornecedor;
- Usuário;
- Períodos de operações.

Exemplo de consulta de produtos:

```http
GET /api/produtos?page=0&size=10&sort=nome,asc
```

As respostas paginadas utilizam a estrutura disponibilizada pelo Spring Data.

---



## 🎯 Escopo do produto

O projeto tem como objetivo inicial demonstrar o funcionamento de um sistema para **um supermercado físico**, com operações administrativas e de frente de caixa.

O MVP planejado contempla:

- Um estabelecimento;
- Vários funcionários;
- Perfis de acesso;
- Cadastro de produtos;
- Cadastro de fornecedores;
- Compras;
- Estoque;
- Vendas presenciais;
- Pagamentos simulados;
- Cancelamentos controlados;
- Consultas gerenciais;
- Interface web.

O cadastro de clientes será opcional para vendas presenciais.

O sistema não pretende, nesta primeira versão, substituir uma solução comercial certificada ou integrada a obrigações fiscais.

Integrações fiscais e financeiras reais exigiriam análise específica de requisitos legais, operacionais e de segurança.

---

