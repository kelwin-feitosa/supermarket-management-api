<div class="studio-home" id="studio-home">
  <div class="studio-grid-background" aria-hidden="true"></div>

  <section class="studio-hero" aria-labelledby="studio-title">
    <div class="studio-hero__glow" aria-hidden="true"></div>
    <div class="studio-hero__copy">
      <div class="studio-eyebrow studio-hero__eyebrow"><span class="studio-eyebrow__line"></span> ENGINEERING DOCUMENTATION <span class="studio-eyebrow__faint">/ SUPERMARKET API</span></div>
      <div class="studio-hero__edition"><span class="studio-status-dot" aria-hidden="true"></span> JAVA · SPRING BOOT · POSTGRESQL <span aria-hidden="true">/</span> EM EVOLUÇÃO</div>
      <h1 id="studio-title">Além dos <span class="studio-hero__highlight">endpoints.</span><br>Dentro da <span class="studio-hero__outline">engenharia.</span></h1>
      <p class="studio-hero__description">Explore o raciocínio por trás de uma API de gestão de supermercado: arquitetura modular, regras de negócio, segurança, persistência e qualidade — com decisões e limitações documentadas.</p>
      <div class="studio-hero__actions">
        <a class="studio-button studio-button--solid" href="visao-geral/">Explorar documentação <span aria-hidden="true">↗</span></a>
        <a class="studio-button studio-button--outline" href="https://github.com/kelwin-feitosa/supermarket-management-api">Código no GitHub <span aria-hidden="true">↗</span></a>
      </div>
      <div class="studio-hero__footnote"><span class="studio-hero__footline" aria-hidden="true"></span> UMA APLICAÇÃO <span aria-hidden="true">·</span> DOMÍNIOS SEPARADOS <span aria-hidden="true">·</span> EVOLUÇÃO CONTÍNUA</div>
    </div>

    <div class="studio-hero__art">
      <div class="studio-orbit studio-orbit--one" aria-hidden="true"></div>
      <div class="studio-orbit studio-orbit--two" aria-hidden="true"></div>
      <span class="studio-floating-label studio-floating-label--top" aria-hidden="true">[ SYSTEM / JAVA 21 ]</span>
      <div class="studio-system-window" aria-label="Representação conceitual do ciclo de uma requisição HTTP no backend">
        <div class="studio-system-window__header">
          <span class="studio-window-dots" aria-hidden="true"><i></i><i></i><i></i></span>
          <span>request.flow</span>
          <span class="studio-window-chip">MONOLITH / MODULAR</span>
        </div>
        <div class="studio-system-window__body">
          <div class="studio-window-comment"># API REQUEST LIFECYCLE</div>
          <div class="studio-request"><span class="studio-request__verb">POST</span><span>/compras</span><span class="studio-request__arrow" aria-hidden="true">↗</span></div>
          <div class="studio-path" aria-hidden="true"><span></span></div>
          <div class="studio-flow-row"><div class="studio-flow-row__symbol">01</div><div class="studio-flow-row__description"><strong>CONTROLLER</strong><small>HTTP · validação · DTOs</small></div><span class="studio-flow-row__label">ENTRY</span></div>
          <div class="studio-flow-connector" aria-hidden="true"><span></span></div>
          <div class="studio-flow-row studio-flow-row--active"><div class="studio-flow-row__symbol">02</div><div class="studio-flow-row__description"><strong>SERVICE</strong><small>regras · transações</small></div><span class="studio-flow-row__label">CORE</span></div>
          <div class="studio-flow-connector" aria-hidden="true"><span></span></div>
          <div class="studio-flow-row"><div class="studio-flow-row__symbol">03</div><div class="studio-flow-row__description"><strong>REPOSITORY</strong><small>JPA · PostgreSQL</small></div><span class="studio-flow-row__label">DATA</span></div>
          <div class="studio-system-window__footer"><span class="studio-system-window__pulse" aria-hidden="true"></span> Estrutura ilustrativa, baseada nos módulos do código <span aria-hidden="true">⌁</span></div>
        </div>
      </div>
      <span class="studio-floating-label studio-floating-label--bottom" aria-hidden="true">[ 07 CAPACIDADES / 01 APLICAÇÃO ]</span>
    </div>
  </section>

  <div class="studio-facts" aria-label="Características do backend">
    <div class="studio-fact"><span class="studio-fact__number">01</span><span class="studio-fact__text"><strong>Aplicação Spring Boot</strong><small>Uma implantação, múltiplas responsabilidades</small></span></div>
    <div class="studio-fact"><span class="studio-fact__number">07</span><span class="studio-fact__text"><strong>Módulos principais</strong><small>Organizados por capacidades de negócio</small></span></div>
    <div class="studio-fact"><span class="studio-fact__number">CI</span><span class="studio-fact__text"><strong>Validação automatizada</strong><small>Testes e revisão de mudanças no GitHub</small></span></div>
  </div>

  <section class="studio-section studio-section--topics" id="explorar" aria-labelledby="studio-topics-title">
    <div class="studio-section__heading studio-reveal">
      <div><span class="studio-section__eyebrow">01 / LEIA O SISTEMA</span><h2 id="studio-topics-title">Tudo se conecta.<br><em>Comece por onde importa.</em></h2></div>
      <p>Uma documentação estruturada para entender o código, justificar escolhas, executar a API e identificar o que ainda precisa evoluir.</p>
    </div>
    <div class="studio-topic-grid">
      <a class="studio-topic-card studio-reveal" href="arquitetura/">
  <span class="studio-topic-card__top"><span class="studio-topic-card__icon"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.7" aria-hidden="true"><rect x="3" y="3" width="7" height="7" rx="1.5"/><rect x="14" y="3" width="7" height="7" rx="1.5"/><rect x="8.5" y="14" width="7" height="7" rx="1.5"/><path d="M6.5 10v2h11v-2M12 12v2"/></svg></span><span class="studio-topic-card__index">01 / 06</span></span>
  <strong>Arquitetura</strong><span class="studio-topic-card__description">Como os módulos se relacionam e por que o backend permanece um monólito modular.</span>
  <span class="studio-topic-card__link">Explorar tópico <span aria-hidden="true">↗</span></span>
</a>
<a class="studio-topic-card studio-reveal" href="dominio/">
  <span class="studio-topic-card__top"><span class="studio-topic-card__icon"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.7" aria-hidden="true"><path d="M4 5h16v14H4zM4 10h16M10 10v9"/><path d="M13.5 14h3.5M13.5 17h2"/></svg></span><span class="studio-topic-card__index">02 / 06</span></span>
  <strong>Domínio e regras</strong><span class="studio-topic-card__description">Compras, vendas, estoque e invariantes que sustentam os fluxos de negócio.</span>
  <span class="studio-topic-card__link">Explorar tópico <span aria-hidden="true">↗</span></span>
</a>
<a class="studio-topic-card studio-reveal" href="banco-de-dados/">
  <span class="studio-topic-card__top"><span class="studio-topic-card__icon"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.7" aria-hidden="true"><ellipse cx="12" cy="5" rx="8" ry="3"/><path d="M4 5v14c0 1.7 3.6 3 8 3s8-1.3 8-3V5M4 12c0 1.7 3.6 3 8 3s8-1.3 8-3"/></svg></span><span class="studio-topic-card__index">03 / 06</span></span>
  <strong>Persistência</strong><span class="studio-topic-card__description">Modelo relacional, integridade e migrations versionadas com Flyway.</span>
  <span class="studio-topic-card__link">Explorar tópico <span aria-hidden="true">↗</span></span>
</a>
<a class="studio-topic-card studio-reveal" href="seguranca/">
  <span class="studio-topic-card__top"><span class="studio-topic-card__icon"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.7" aria-hidden="true"><path d="m12 2 8 4v6c0 5.2-3.6 8.7-8 10-4.4-1.3-8-4.8-8-10V6z"/><path d="m9 12 2 2 4-4"/></svg></span><span class="studio-topic-card__index">04 / 06</span></span>
  <strong>Segurança</strong><span class="studio-topic-card__description">JWT, Spring Security, perfis de acesso e limitações conhecidas.</span>
  <span class="studio-topic-card__link">Explorar tópico <span aria-hidden="true">↗</span></span>
</a>
<a class="studio-topic-card studio-reveal" href="rotas/">
  <span class="studio-topic-card__top"><span class="studio-topic-card__icon"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.7" aria-hidden="true"><path d="m8 4-5 8 5 8M16 4l5 8-5 8M14 3l-4 18"/></svg></span><span class="studio-topic-card__index">05 / 06</span></span>
  <strong>API e contratos</strong><span class="studio-topic-card__description">Rotas efetivas, respostas, tratamento de erros e integração com OpenAPI.</span>
  <span class="studio-topic-card__link">Explorar tópico <span aria-hidden="true">↗</span></span>
</a>
<a class="studio-topic-card studio-reveal" href="qualidade/">
  <span class="studio-topic-card__top"><span class="studio-topic-card__icon"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.7" aria-hidden="true"><rect x="3" y="5" width="18" height="14" rx="2"/><path d="m7 12 3 3 6-7M3 9h4"/></svg></span><span class="studio-topic-card__index">06 / 06</span></span>
  <strong>Qualidade</strong><span class="studio-topic-card__description">Testes, pipeline de CI, cobertura e próximos passos de validação.</span>
  <span class="studio-topic-card__link">Explorar tópico <span aria-hidden="true">↗</span></span>
</a>
    </div>
  </section>

  <section class="studio-explorer studio-reveal" aria-labelledby="studio-explorer-title">
    <div class="studio-explorer__heading">
      <div><span class="studio-section__eyebrow">02 / EXPLORE O BACKEND</span><h2 id="studio-explorer-title">Um sistema. <em>Vários domínios.</em></h2></div>
      <p>Selecione um módulo para conhecer sua responsabilidade na arquitetura atual.</p>
    </div>
    <div class="studio-explorer__workspace">
      <div class="studio-module-menu" aria-label="Módulos do backend">
        <button class="studio-module-button is-active" type="button" data-module="catalog" aria-pressed="true"><span class="studio-module-button__marker" aria-hidden="true"></span><span class="studio-module-button__name">Catálogo</span><span class="studio-module-button__suffix">Produtos e categorias</span><span class="studio-module-button__arrow" aria-hidden="true">↗</span></button>
<button class="studio-module-button" type="button" data-module="procurement" aria-pressed="false"><span class="studio-module-button__marker" aria-hidden="true"></span><span class="studio-module-button__name">Compras</span><span class="studio-module-button__suffix">Fornecedores e aquisição</span><span class="studio-module-button__arrow" aria-hidden="true">↗</span></button>
<button class="studio-module-button" type="button" data-module="sales" aria-pressed="false"><span class="studio-module-button__marker" aria-hidden="true"></span><span class="studio-module-button__name">Vendas</span><span class="studio-module-button__suffix">Carrinho e registro de vendas</span><span class="studio-module-button__arrow" aria-hidden="true">↗</span></button>
<button class="studio-module-button" type="button" data-module="inventory" aria-pressed="false"><span class="studio-module-button__marker" aria-hidden="true"></span><span class="studio-module-button__name">Estoque</span><span class="studio-module-button__suffix">Disponibilidade e quantidades</span><span class="studio-module-button__arrow" aria-hidden="true">↗</span></button>
<button class="studio-module-button" type="button" data-module="identity" aria-pressed="false"><span class="studio-module-button__marker" aria-hidden="true"></span><span class="studio-module-button__name">Identidade</span><span class="studio-module-button__suffix">Usuários e perfis</span><span class="studio-module-button__arrow" aria-hidden="true">↗</span></button>
<button class="studio-module-button" type="button" data-module="security" aria-pressed="false"><span class="studio-module-button__marker" aria-hidden="true"></span><span class="studio-module-button__name">Segurança</span><span class="studio-module-button__suffix">Autenticação com JWT</span><span class="studio-module-button__arrow" aria-hidden="true">↗</span></button>
<button class="studio-module-button" type="button" data-module="shared" aria-pressed="false"><span class="studio-module-button__marker" aria-hidden="true"></span><span class="studio-module-button__name">Compartilhado</span><span class="studio-module-button__suffix">Erros e cálculos</span><span class="studio-module-button__arrow" aria-hidden="true">↗</span></button>
      </div>
      <div class="studio-module-details" aria-live="polite" aria-atomic="true">
        <div class="studio-module-details__top"><span><span class="studio-module-details__signal" aria-hidden="true"></span> MODULE EXPLORER</span><span>01 — 07</span></div>
        <span class="studio-module-details__eyebrow" id="studio-module-tag">DOMAIN / CATALOG</span>
        <h3 id="studio-module-title">Catálogo</h3>
        <p id="studio-module-summary">Entidades, filtros e operações de cadastro de produtos e categorias.</p>
        <div class="studio-module-details__terminal"><span>src/main/java /</span><strong id="studio-module-path">catalog/</strong><div id="studio-module-flow">ProductController → Service → Repository</div></div>
        <a id="studio-module-link" class="studio-module-details__link" href="arquitetura/">Entender a arquitetura <span aria-hidden="true">↗</span></a>
      </div>
    </div>
  </section>

  <section class="studio-section studio-section--process" aria-labelledby="studio-process-title">
    <div class="studio-section__heading studio-reveal">
      <div><span class="studio-section__eyebrow">03 / DA TEORIA À EXECUÇÃO</span><h2 id="studio-process-title">Documentação que<br><em>serve para construir.</em></h2></div>
      <p>O objetivo não é decorar o repositório, mas tornar cada decisão reproduzível, verificável e compreensível.</p>
    </div>
    <div class="studio-process-grid">
      <a class="studio-process-card studio-reveal" href="visao-geral/"><span class="studio-process-card__step">ETAPA 01</span><span class="studio-process-card__glyph">◇</span><h3>Entenda</h3><p>Contexto, requisitos, módulos e principais fluxos de negócio.</p><span>Visão geral <span aria-hidden="true">↗</span></span></a>
      <a class="studio-process-card studio-reveal" href="execucao/"><span class="studio-process-card__step">ETAPA 02</span><span class="studio-process-card__glyph">⌘</span><h3>Execute</h3><p>Configure o PostgreSQL e rode a aplicação e os testes localmente.</p><span>Guia de execução <span aria-hidden="true">↗</span></span></a>
      <a class="studio-process-card studio-reveal" href="roadmap/"><span class="studio-process-card__step">ETAPA 03</span><span class="studio-process-card__glyph">↗</span><h3>Evolua</h3><p>Conheça as limitações atuais, decisões técnicas e próximos incrementos.</p><span>Roadmap <span aria-hidden="true">↗</span></span></a>
    </div>
  </section>

  <section class="studio-cta studio-reveal" aria-labelledby="studio-cta-title">
    <div class="studio-cta__copy"><span class="studio-section__eyebrow">PRONTO PARA EXPLORAR?</span><h2 id="studio-cta-title">Veja o código.<br><em>Entenda o porquê.</em></h2><p>Comece pela arquitetura, reproduza o ambiente e acompanhe a evolução de um backend real em construção.</p><div class="studio-cta__buttons"><a class="studio-button studio-button--solid" href="arquitetura/">Conhecer a arquitetura <span aria-hidden="true">↗</span></a><a class="studio-button studio-button--outline" href="execucao/">Executar localmente <span aria-hidden="true">↗</span></a></div></div>
    <div class="studio-cta__terminal"><div class="studio-cta__terminal-header"><span class="studio-window-dots" aria-hidden="true"><i></i><i></i><i></i></span><span>quickstart.sh</span><button type="button" class="studio-copy" aria-label="Copiar comandos do terminal">Copiar</button></div><pre><code id="studio-quickstart"><span class="studio-code-comment"># Iniciar banco de dados</span>
<span class="studio-code-prefix">$</span> docker compose up -d
<span class="studio-code-comment"># Configure sua chave JWT no Linux/macOS</span>
<span class="studio-code-prefix">$</span> export JWT_SECRET="$(openssl rand -hex 32)"
<span class="studio-code-prefix">$</span> ./mvnw spring-boot:run</code></pre><div class="studio-cta__terminal-foot"><span>JAVA 21</span><span>POSTGRESQL 17</span><span>SPRING BOOT</span></div></div>
  </section>
  <div class="studio-home-disclaimer"><span class="studio-status-dot" aria-hidden="true"></span><p><strong>Sobre o projeto:</strong> esta API é um sistema educacional em evolução, não um produto pronto para uso comercial. Concorrência de estoque, autorização fina e operações completas de PDV ainda exigem trabalho. <a href="roadmap/">Veja as limitações e prioridades.</a></p></div>
</div>