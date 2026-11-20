/* Supermarket API documentation: progressive enhancements.
   No external dependencies, analytics, or network requests. */
(function () {
  "use strict";

  var modules = {
    catalog: { label: "CATALOG", title: "Catálogo", summary: "Entidades, filtros e operações de cadastro de produtos e categorias.", path: "catalog/", flow: "ProdutoController → ProdutoService → ProdutoRepository", index: "01 — 07", href: "arquitetura/" },
    procurement: { label: "PROCUREMENT", title: "Compras", summary: "Registro de compras e fornecedores, com entrada de mercadorias no estoque.", path: "procurement/", flow: "CompraController → CompraService → EstoqueService", index: "02 — 07", href: "dominio/" },
    sales: { label: "SALES", title: "Vendas", summary: "Carrinhos e realização de vendas no modelo atual, ainda em evolução para um PDV físico.", path: "sales/", flow: "VendaController → VendaService → EstoqueService", index: "03 — 07", href: "dominio/" },
    inventory: { label: "INVENTORY", title: "Estoque", summary: "Regras de disponibilidade, entrada e baixa de quantidades. Concorrência ainda precisa ser tratada.", path: "inventory/", flow: "EstoqueService → Produto", index: "04 — 07", href: "dominio/" },
    identity: { label: "IDENTITY", title: "Identidade", summary: "Cadastro de usuários, perfis de acesso e dados usados na autenticação.", path: "identity/", flow: "UsuarioController → UsuarioService", index: "05 — 07", href: "seguranca/" },
    security: { label: "SECURITY", title: "Segurança", summary: "JWT, Spring Security, configuração de autenticação e filtros de requisição.", path: "security/", flow: "AuthController → JwtService", index: "06 — 07", href: "seguranca/" },
    shared: { label: "SHARED", title: "Compartilhado", summary: "Exceções padronizadas, cálculo de valores e utilitários compartilhados.", path: "shared/", flow: "GlobalExceptionHandler / CalculoPrecoService", index: "07 — 07", href: "api/" }
  };

  function init() {
    var home = document.querySelector(".studio-home");
    var reduceMotion = window.matchMedia("(prefers-reduced-motion: reduce)").matches;

    // Reading indicator for documentation pages.
    if (!document.querySelector(".studio-reading-indicator")) {
      var indicator = document.createElement("div");
      indicator.className = "studio-reading-indicator";
      indicator.setAttribute("aria-hidden", "true");
      document.body.appendChild(indicator);
      var scheduled = false;
      function updateIndicator() {
        var max = Math.max(1, document.documentElement.scrollHeight - window.innerHeight);
        var amount = Math.min(100, Math.max(0, window.scrollY / max * 100));
        indicator.style.width = amount + "%";
        scheduled = false;
      }
      function requestUpdate() {
        if (scheduled) return;
        scheduled = true;
        window.requestAnimationFrame(updateIndicator);
      }
      window.addEventListener("scroll", requestUpdate, { passive: true });
      window.addEventListener("resize", requestUpdate);
      requestUpdate();
    }

    if (!home || home.dataset.studioEnhanced === "1") return;
    home.dataset.studioEnhanced = "1";

    // Interactive module explorer, fully usable with keyboard by native buttons.
    var controls = home.querySelectorAll("[data-module]");
    var fields = {
      tag: home.querySelector("#studio-module-tag"),
      title: home.querySelector("#studio-module-title"),
      summary: home.querySelector("#studio-module-summary"),
      path: home.querySelector("#studio-module-path"),
      flow: home.querySelector("#studio-module-flow"),
      link: home.querySelector("#studio-module-link"),
      count: home.querySelector(".studio-module-details__top > span:last-child")
    };
    Array.prototype.forEach.call(controls, function (button) {
      button.addEventListener("click", function () {
        var id = button.getAttribute("data-module");
        var data = modules[id];
        if (!data) return;
        Array.prototype.forEach.call(controls, function (other) {
          var active = other === button;
          other.classList.toggle("is-active", active);
          other.setAttribute("aria-pressed", active ? "true" : "false");
        });
        fields.tag.textContent = "DOMAIN / " + data.label;
        fields.title.textContent = data.title;
        fields.summary.textContent = data.summary;
        fields.path.textContent = data.path;
        fields.flow.textContent = data.flow;
        fields.link.setAttribute("href", data.href);
        fields.count.textContent = data.index;
      });
    });

    // Cosmetic pointer-follow spotlight on hover-capable desktop devices.
    if (!reduceMotion && window.matchMedia("(hover: hover) and (pointer: fine)").matches) {
      Array.prototype.forEach.call(home.querySelectorAll(".studio-hero, .studio-topic-card"), function (element) {
        element.addEventListener("pointermove", function (event) {
          var box = element.getBoundingClientRect();
          element.style.setProperty("--pointer-x", Math.round(event.clientX - box.left) + "px");
          element.style.setProperty("--pointer-y", Math.round(event.clientY - box.top) + "px");
        }, { passive: true });
      });
    }

    // Scroll entry motion. Everything stays visible when JavaScript is unavailable.
    if (!reduceMotion && "IntersectionObserver" in window) {
      var nodes = home.querySelectorAll(".studio-reveal");
      var observer = new IntersectionObserver(function (entries) {
        entries.forEach(function (entry) {
          if (entry.isIntersecting) {
            entry.target.classList.add("is-visible");
            observer.unobserve(entry.target);
          }
        });
      }, { threshold: 0.08, rootMargin: "0px 0px 24px 0px" });
      nodes.forEach(function (node) { observer.observe(node); });
      home.classList.add("studio-motion");
    }

    var copy = home.querySelector(".studio-copy");
    if (copy) {
      copy.addEventListener("click", function () {
        var code = 'docker compose up -d\nexport JWT_SECRET="$(openssl rand -hex 32)"\n./mvnw spring-boot:run';
        if (!navigator.clipboard || !navigator.clipboard.writeText) {
          copy.textContent = "Indisponível";
          return;
        }
        navigator.clipboard.writeText(code).then(function () {
          copy.textContent = "Copiado ✓";
          copy.setAttribute("aria-label", "Comandos copiados");
        }).catch(function () {
          copy.textContent = "Falhou";
        });
      });
    }
  }

  if (document.readyState === "loading") document.addEventListener("DOMContentLoaded", init);
  else init();
})();
