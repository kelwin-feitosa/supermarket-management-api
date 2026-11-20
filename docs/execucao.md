# Executar o ambiente

O banco PostgreSQL é disponibilizado pelo `docker-compose.yml` na porta local `5433`. Use apenas credenciais de desenvolvimento ao reproduzir o exemplo.

> Esta documentação descreve o projeto em evolução. Funcionalidades planejadas não devem ser confundidas com comportamento implementado; o código e os testes prevalecem em caso de divergência.

## 🐳 Docker

O projeto utiliza Docker Compose para disponibilizar o PostgreSQL em ambiente local.

### Infraestrutura atual

```text
Docker Compose
      │
      ▼
PostgreSQL 17
      │
      ▼
Spring Boot API
```

Atualmente, o Docker Compose disponibiliza principalmente o banco de dados.

A aplicação Spring Boot pode ser executada separadamente pelo Maven.

#### Inicializando o banco

```bash
docker compose up -d
```

O PostgreSQL fica disponível em:

```text
localhost:5433
```

A porta 5433 evita conflito com instalações locais que utilizam a porta padrão 5432.

#### Encerrando o serviço

```bash
docker compose down
```

O volume persistente não é removido por esse comando.

> Evite utilizar `docker compose down -v` sem compreender as consequências, pois isso remove os volumes do projeto e pode causar perda de dados locais.

### Evolução planejada

- Dockerfile da aplicação;
- Healthchecks;
- Execução da aplicação e banco pelo Docker Compose;
- Configuração por variáveis de ambiente;
- Separação adequada de ambientes;
- Preparação para deploy.

---



## 🛠️ Tecnologias

<div align="center">

#### ☕ Backend

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-BC4521?style=for-the-badge)

#### 🔐 Segurança

![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![BCrypt](https://img.shields.io/badge/BCrypt-Password%20Hashing-59666C?style=for-the-badge)

#### 🗄️ Banco de dados

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)

#### 🧪 Testes e qualidade

![JUnit](https://img.shields.io/badge/JUnit%205-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-78A641?style=for-the-badge)
![JaCoCo](https://img.shields.io/badge/JaCoCo-Coverage-EF2D5E?style=for-the-badge)
![SonarCloud](https://img.shields.io/badge/SonarCloud-F3702A?style=for-the-badge&logo=sonarcloud&logoColor=white)

#### 📖 Documentação

![OpenAPI](https://img.shields.io/badge/OpenAPI-6BA539?style=for-the-badge&logo=openapiinitiative&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

#### 🐳 Infraestrutura e ferramentas

![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)

</div>

---



## ⚙️ Como executar

### Pré-requisitos

- JDK 21;
- Docker;
- Docker Compose;
- Git;
- Maven Wrapper incluído no repositório.

O projeto utiliza Java 21 como versão-alvo de compilação.

### 1. Clonar o projeto

```bash
git clone https://github.com/kelwin-feitosa/supermarket-management-api.git

cd supermarket-management-api
```

### 2. Configurar a chave JWT

A aplicação necessita da variável de ambiente:

```text
JWT_SECRET
```

Linux/macOS:

```bash
export JWT_SECRET="$(openssl rand -hex 32)"
```

O comando gera uma chave aleatória apropriada para o ambiente local.

Não armazene segredos reais no repositório.

### 3. Iniciar o PostgreSQL

```bash
docker compose up -d
```

A configuração atual disponibiliza o PostgreSQL na porta:

```text
5433
```

### 4. Executar a aplicação

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```bat
mvnw.cmd spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

O Flyway executará as migrations necessárias durante a inicialização.

### 5. Acessar a documentação

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI:

```text
http://localhost:8080/v3/api-docs
```

### 6. Executar os testes

```bash
./mvnw clean verify
```

Os testes de integração exigem que o banco PostgreSQL de testes esteja disponível.

O Docker Compose inclui um script de inicialização para criação desse banco em volumes novos.

Se o volume já existir e o banco `supermarket_test` não tiver sido criado, será necessário criá-lo antes de executar os testes.

Exemplo:

```bash
docker compose exec postgres \
  psql -U postgres -d postgres \
  -c "CREATE DATABASE supermarket_test;"
```

Execute esse comando somente se o banco ainda não existir.

### 7. Consultar o relatório de cobertura

Após executar os testes:

```text
target/site/jacoco/index.html
```

### 8. Gerar o JAR

```bash
./mvnw clean package
```

O artefato será gerado no diretório:

```text
target/
```

---



## Publicação e prévia do portal

```bash
python -m venv .venv-docs
source .venv-docs/bin/activate
pip install -r requirements-docs.txt
zensical serve
```

No Windows, ative o ambiente com `.venv-docs\Scripts\activate`. O site de documentação abre em `http://localhost:8000`, sem iniciar Spring Boot ou PostgreSQL.
