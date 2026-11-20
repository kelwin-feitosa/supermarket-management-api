# Autenticação e autorização

**Importante:** a existência dos perfis não comprova que todos os endpoints possuem as permissões corretas. Compare [o mapa de rotas](rotas.md) com testes de autorização antes de qualquer uso real.

> Esta documentação descreve o projeto em evolução. Funcionalidades planejadas não devem ser confundidas com comportamento implementado; o código e os testes prevalecem em caso de divergência.

## 🔐 Segurança

A API utiliza **Spring Security** para autenticação e autorização.

### Autenticação JWT

A autenticação utiliza JSON Web Tokens (JWT).

O sistema trabalha com sessões de autenticação stateless, sem depender de sessões HTTP tradicionais para armazenar o estado de autenticação.

#### Fluxo de autenticação

```text
             POST /auth/login
                    │
                    ▼
              Email + Senha
                    │
                    ▼
         AuthenticationManager
                    │
                    ▼
         UsuarioDetailsService
                    │
                    ▼
          BCryptPasswordEncoder
                    │
                    ▼
           Usuário autenticado
                    │
                    ▼
                JwtService
                    │
                    ▼
                JWT Token
```

O token retornado deve ser enviado nas requisições autenticadas:

```http
Authorization: Bearer <token>
```

### Perfis de acesso

O sistema possui os perfis:

```text
SYSTEM_ADMIN
      │
      ▼
   MANAGER
    │   │
    ▼   ▼
STOCK_MANAGER  CASHIER

CUSTOMER
```

As permissões são configuradas com Spring Security e anotações `@PreAuthorize`.

A hierarquia permite que determinadas permissões sejam herdadas por perfis superiores.

> **Atenção:** as permissões atuais ainda precisam de revisão para refletir corretamente os processos de um supermercado físico. A presença dos perfis não significa que todos os fluxos de autorização estejam concluídos.

### Proteção de senhas

As senhas são transformadas em hashes utilizando BCrypt antes do armazenamento.

O sistema não deve persistir senhas em texto puro.

### Configuração do JWT

A chave utilizada para assinar tokens JWT deve ser fornecida por variável de ambiente.

```text
JWT_SECRET
```

Exemplo para Linux:

```bash
export JWT_SECRET="$(openssl rand -hex 32)"
```

A chave não deve ser incluída em commits ou arquivos versionados.

### Melhorias planejadas

- Revisão das permissões por função;
- Inicialização segura do administrador;
- Proteção contra acesso a recursos pertencentes a outros usuários;
- Testes de autorização para diferentes perfis;
- Configuração de CORS para o futuro frontend;
- Revisão do tratamento de tokens inválidos e expirados;
- Melhorias na auditoria das operações.

---



## Pontos de atenção encontrados no código

- A API é stateless e utiliza JWT no cabeçalho `Authorization`.
- A hierarquia configurada em `SecurityConfig` é `SYSTEM_ADMIN > MANAGER > STOCK_MANAGER` e `MANAGER > CASHIER`, enquanto `CUSTOMER` é independente.
- Controllers usam `@PreAuthorize` em classes e métodos; esse comportamento deve ser coberto por testes que comprovem permissões efetivas, inclusive herança de papéis.
- O valor de `JWT_SECRET` deve vir do ambiente e não pode ser enviado ao repositório.
- Regras de propriedade de recursos (por exemplo, um usuário acessar carrinho de outro) exigem revisão e testes específicos.

**Não utilize dados reais, senhas ou tokens no portal público.**
