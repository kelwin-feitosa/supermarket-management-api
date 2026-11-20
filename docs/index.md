<!-- A página inicial é construída com HTML sem dependências externas; os links são relativos à raiz do portal. -->
<div class="portal-home">
  <section class="portal-hero" aria-labelledby="portal-title">
    <div class="portal-hero__content">
      <div class="portal-kicker"><span class="portal-kicker__dot" aria-hidden="true"></span> DOCUMENTAÇÃO DE ENGENHARIA <span class="portal-kicker__sep">/</span> JAVA BACKEND</div>
      <h1 id="portal-title">Backend bem explicado.<br><span>Decisões que fazem sentido.</span></h1>
      <p class="portal-hero__lead">Explore a arquitetura, as regras de negócio, os contratos e a evolução de uma API de gestão de supermercado construída com Java e Spring Boot.</p>
      <div class="portal-hero__actions">
        <a class="portal-btn portal-btn--primary" href="visao-geral/">Explorar documentação <span aria-hidden="true">↗</span></a>
        <a class="portal-btn portal-btn--ghost" href="https://github.com/kelwin-feitosa/supermarket-management-api">Ver repositório <span aria-hidden="true">↗</span></a>
      </div>
      <div class="portal-stack" aria-label="Tecnologias principais">
        <span>Java 21</span><span>Spring Boot 4</span><span>PostgreSQL</span><span>Spring Security</span>
      </div>
    </div>
    <div class="portal-hero__visual" aria-label="Visão simplificada das camadas da aplicação">
      <div class="portal-panel">
        <div class="portal-panel__top"><span class="portal-panel__lights" aria-hidden="true"><i></i><i></i><i></i></span><span>application / architecture</span><span class="portal-panel__status">MODULAR</span></div>
        <div class="portal-panel__body">
          <div class="portal-code-label">REQUEST LIFECYCLE</div>
          <div class="portal-layer portal-layer--entry"><span class="portal-layer__number">01</span><span><strong>HTTP / REST</strong><small>Controllers e validação</small></span><span class="portal-layer__tag">ENTRY</span></div>
          <div class="portal-layer__connector" aria-hidden="true">↓</div>
          <div class="portal-layer portal-layer--logic"><span class="portal-layer__number">02</span><span><strong>Regras de negócio</strong><small>Services e transações</small></span><span class="portal-layer__tag">CORE</span></div>
          <div class="portal-layer__connector" aria-hidden="true">↓</div>
          <div class="portal-layer portal-layer--data"><span class="portal-layer__number">03</span><span><strong>Persistência</strong><small>JPA / PostgreSQL</small></span><span class="portal-layer__tag">DATA</span></div>
          <div class="portal-panel__foot"><span class="portal-panel__pulse"></span> Uma aplicação · Módulos por domínio</div>
        </div>
      </div>
    </div>
  </section>

  <section class="portal-meta" aria-label="Características do projeto">
    <div><span class="portal-meta__eyebrow">ARQUITETURA</span><strong>Monólito modular</strong><small>Domínios e responsabilidades separados</small></div>
    <div><span class="portal-meta__eyebrow">DOCUMENTAÇÃO</span><strong>Guiada pelo código</strong><small>Contratos, arquitetura e decisões</small></div>
    <div><span class="portal-meta__eyebrow">MATURIDADE</span><strong>Em evolução</strong><small>Limitações e próximos passos explícitos</small></div>
  </section>

  <section class="portal-section" aria-labelledby="portal-topics-title">
    <div class="portal-section__heading"><div><span class="portal-eyebrow">ÁREAS DA DOCUMENTAÇÃO</span><h2 id="portal-topics-title">Encontre o que precisa.</h2></div><p>Documentação para quem quer entender, executar, revisar ou evoluir o projeto.</p></div>
    <div class="portal-cards">
      <a class="portal-card" href="arquitetura/"><span class="portal-card__icon">01</span><h3>Arquitetura</h3><p>Estrutura modular, camadas, responsabilidades e decisões técnicas.</p><span class="portal-card__more">Explorar área <span aria-hidden="true">↗</span></span></a>
      <a class="portal-card" href="dominio/"><span class="portal-card__icon">02</span><h3>Regras de negócio</h3><p>Fluxos de compra e venda, estoque, validações e limites atuais.</p><span class="portal-card__more">Explorar área <span aria-hidden="true">↗</span></span></a>
      <a class="portal-card" href="banco-de-dados/"><span class="portal-card__icon">03</span><h3>Dados e migrations</h3><p>Persistência, relacionamentos e evolução do esquema com Flyway.</p><span class="portal-card__more">Explorar área <span aria-hidden="true">↗</span></span></a>
      <a class="portal-card" href="seguranca/"><span class="portal-card__icon">04</span><h3>Segurança</h3><p>JWT, Spring Security, perfis de acesso e riscos ainda pendentes.</p><span class="portal-card__more">Explorar área <span aria-hidden="true">↗</span></span></a>
      <a class="portal-card" href="rotas/"><span class="portal-card__icon">05</span><h3>API e contratos</h3><p>Mapa real das rotas, Swagger e comportamento de erros.</p><span class="portal-card__more">Explorar área <span aria-hidden="true">↗</span></span></a>
      <a class="portal-card" href="qualidade/"><span class="portal-card__icon">06</span><h3>Testes e qualidade</h3><p>Testes automatizados, cobertura e pipelines de integração contínua.</p><span class="portal-card__more">Explorar área <span aria-hidden="true">↗</span></span></a>
    </div>
  </section>

  <section class="portal-bottom" aria-labelledby="portal-start-title">
    <div><span class="portal-eyebrow">PRÓXIMO PASSO</span><h2 id="portal-start-title">Quer executar a API?</h2><p>Configure o PostgreSQL, a chave JWT e rode a aplicação localmente. O guia também explica como acessar o Swagger e executar os testes.</p><a class="portal-btn portal-btn--dark" href="execucao/">Abrir guia de execução <span aria-hidden="true">↗</span></a></div>
    <div class="portal-bottom__snippet" aria-label="Comandos de inicialização em Linux ou macOS"><span class="portal-code-label">TERMINAL · LINUX / MACOS</span><pre><code><span class="portal-cli-prompt">$</span> docker compose up -d
<span class="portal-cli-prompt">$</span> export JWT_SECRET="$(openssl rand -hex 32)"
<span class="portal-cli-prompt">$</span> ./mvnw spring-boot:run</code></pre></div>
  </section>

  <p class="portal-disclaimer"><strong>Escopo:</strong> projeto educacional e de portfólio. O backend está em evolução; controle concorrente de estoque, autorização fina e operações completas de PDV exigem trabalho adicional. Consulte o <a href="roadmap/">roadmap e as limitações</a>.</p>
</div>
