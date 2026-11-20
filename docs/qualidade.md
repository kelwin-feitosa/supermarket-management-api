<div class="studio-doc-eyebrow"><span class="studio-doc-eyebrow__mark" aria-hidden="true"></span> 08 / QUALIDADE <span class="studio-doc-eyebrow__suffix">/ SUPERMARKET MANAGEMENT API</span></div>

# Testes e integração contínua

O pipeline existente de backend permanece separado do pipeline de documentação. Resultados e números históricos devem ser confirmados nas execuções recentes do CI.

> Esta documentação descreve o projeto em evolução. Funcionalidades planejadas não devem ser confundidas com comportamento implementado; o código e os testes prevalecem em caso de divergência.

## 🧪 Testes automatizados

A aplicação possui testes automatizados utilizando:

- JUnit 5;
- Mockito;
- Spring Boot Test;
- Spring Security Test;
- Spring Data JPA;
- PostgreSQL;
- MockMvc.

### Áreas testadas

A suíte atual contempla testes relacionados a:

- Produtos;
- Categorias;
- Fornecedores;
- Compras;
- Vendas;
- Usuários;
- Estoque;
- Cálculos de preços;
- Specifications;
- Inicialização da aplicação;
- Configurações de segurança.

### Resultado da validação local

Após a refatoração da arquitetura, a aplicação foi compilada e validada localmente.

Resultado registrado em **31 de outubro de 2026**:

```text
Tests run: 76
Failures: 0
Errors:   0
Skipped:  0

BUILD SUCCESS
```

A validação também confirmou:

- Compilação das classes;
- Inicialização do contexto Spring;
- Conexão com PostgreSQL;
- Validação das migrations;
- Execução dos testes;
- Geração do artefato JAR;
- Geração do relatório JaCoCo.

> O resultado acima corresponde à execução local. Não representa garantia de que todos os cenários de negócio estejam cobertos ou de que o pipeline remoto esteja passando.

### Executando os testes

Com o PostgreSQL disponível e o ambiente configurado:

```bash
./mvnw clean verify
```

O comando executa as etapas de compilação, testes e verificações configuradas no ciclo Maven.

### Melhorias planejadas

- Ampliar testes de integração;
- Adicionar testes de controllers;
- Cobrir cenários de autorização;
- Implementar testes de concorrência;
- Validar transações;
- Introduzir Testcontainers;
- Aprimorar isolamento entre os bancos de desenvolvimento e teste.

---



## 📊 Qualidade de código

### JaCoCo

O JaCoCo é utilizado para medir a cobertura dos testes.

O relatório HTML pode ser consultado após a execução do Maven:

```text
target/site/jacoco/index.html
```

A cobertura deverá ser acompanhada principalmente nas regras de negócio.

Uma porcentagem elevada de cobertura não substitui testes relevantes.

### SonarCloud

O projeto possui configuração para utilização do SonarCloud.

A ferramenta pode auxiliar na identificação de:

- Bugs;
- Vulnerabilidades;
- Security Hotspots;
- Code Smells;
- Problemas de manutenibilidade;
- Pontos de melhoria na cobertura de testes.

Os resultados devem ser avaliados considerando o contexto do projeto.

---



## 🔄 Integração contínua

A aplicação possui um workflow do GitHub Actions responsável pela validação automática.

### Pipeline

```text
Push / Pull Request
        │
        ▼
      Checkout
        │
        ▼
      Java 21
        │
        ▼
     PostgreSQL
        │
        ▼
      Maven
        │
        ▼
      Testes
        │
        ▼
      JaCoCo
        │
        ▼
     SonarCloud
```

O pipeline possui etapas para:

- Preparar o ambiente;
- Iniciar PostgreSQL;
- Criar o banco de testes;
- Executar Maven;
- Validar o relatório de cobertura;
- Executar análise estática quando configurada para o evento correspondente.

### Estado verificado do CI

Em **20 de novembro de 2026**, após a integração do PR #19 na `main`, os workflows de [CI do backend](https://github.com/kelwin-feitosa/supermarket-management-api/actions/runs/44714491596) e [publicação da documentação](https://github.com/kelwin-feitosa/supermarket-management-api/actions/runs/44714491633) concluíram com sucesso.

Essa informação é um **registro datado**, não um indicador em tempo real. Para verificar a execução mais recente, consulte a [página de Actions](https://github.com/kelwin-feitosa/supermarket-management-api/actions). O resultado não garante cobertura integral, ausência de vulnerabilidades ou execução bem-sucedida de todos os eventos futuros.

#### Histórico e melhorias pendentes

No histórico do projeto, atualizações automáticas de dependências apresentaram falhas associadas à disponibilidade de `JWT_SECRET` em alguns pull requests. O êxito da `main` **não demonstra** que esses cenários tenham sido resolvidos, pois PRs de bots podem ter acesso diferente a segredos.

- Criar uma chave JWT apropriada para testes, sem depender de segredos usados por ambientes reais;
- Garantir que pull requests automatizados possam executar os testes com segurança;
- Reforçar o isolamento e a reprodutibilidade do banco de testes;
- Cobrir autorização, transações e concorrência;
- Revisar atualizações de dependências antes de integrá-las.

O êxito do CI deve continuar sendo um critério de revisão a cada alteração.

---



## Validação da documentação

O workflow `.github/workflows/docs.yml` compila o portal em pull requests e, após integração na `main`, publica no GitHub Pages **se o Pages estiver habilitado com fonte GitHub Actions**. Uma compilação bem-sucedida da documentação não valida o comportamento do backend.
