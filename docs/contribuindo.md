# Como contribuir com código e documentação

## Antes de abrir um pull request

1. Identifique o problema e descreva o comportamento esperado.
2. Evite introduzir mudanças de código junto de refatorações de documentação sem necessidade.
3. Execute os testes relevantes (`./mvnw clean verify`) para alterações de backend.
4. Atualize o portal em `docs/` quando modificar contratos, regras, módulos ou decisões técnicas.
5. Execute `zensical build --clean` para garantir que o portal compila.
6. Explique no PR o que mudou, como verificou e o que ainda não foi testado.

## Documentação

- `README.md`: apresentação e primeiros passos, sem duplicar o portal.
- `docs/`: fonte canônica das explicações detalhadas.
- `docs/decisoes/`: decisões arquiteturais com contexto, alternativas e consequências.
- `zensical.toml`: navegação e aparência do site.
- `.github/workflows/docs.yml`: validação em PR e publicação após integração.

**Política editorial:** documentar funcionalidades com base no código e em testes, separar claramente o implementado do planejado, não publicar segredos ou dados sensíveis. Para contratos exatos da API, atualizar exemplos a partir do OpenAPI gerado em execução.
