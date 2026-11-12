# PRW3 Veículos — API REST de Consertos

API REST desenvolvida com **Spring Boot 4.1.1** para gerenciamento de consertos de veículos, com autenticação via **JWT (JSON Web Token)**.

Projeto da disciplina PRW3 do IFSP.

**Autores:** Fabio e Douglas

---

## Tecnologias

- Java 25
- Spring Boot 4.1.1
- Spring Security (autenticação stateless com JWT)
- Spring Data JPA / Hibernate
- Flyway (migrations de banco de dados)
- H2 Database (banco embarcado, arquivo local)
- Lombok
- Auth0 java-jwt 4.4.0
- Maven

---

## Estrutura do Projeto

```
src/main/java/br/edu/ifsp/prw3_veiculos/
├── Prw3VeiculosApplication.java          # Classe principal
├── Controller/
│   ├── AutenticacaoController.java       # POST /login (gera token JWT)
│   └── ConsertoController.java           # CRUD de consertos
├── dto/
│   ├── DadosAutenticacao.java            # { login, senha }
│   ├── DadosTokenJWT.java                # { token }
│   ├── DadosListagemConserto.java        # Dados resumidos de conserto
│   ├── DadosListagemFull.java            # Dados completos (com paginação)
│   └── DadosAtualizacaoConserto.java     # Dados para PATCH
├── model/
│   ├── Conserto.java                     # @Entity principal
│   ├── Veiculo.java                      # @Embeddable (marca, modelo, ano, cor)
│   ├── Mecanico.java                     # @Embeddable (nome, anosDeExperiencia)
│   └── Usuario.java                      # @Entity, implementa UserDetails
├── repository/
│   ├── ConsertoRepository.java
│   └── UsuarioRepository.java
└── security/
    ├── SecurityConfigurations.java       # Configuração do Spring Security
    ├── AutenticacaoService.java          # Implementa UserDetailsService
    ├── TokenService.java                 # Gera e valida tokens JWT
    └── SecurityFilter.java              # Filtro que intercepta requests com Bearer token
```

---

## Como rodar

### Pré-requisitos

- **JDK 25** instalado (no IntelliJ: `File > Project Structure > SDK > Download JDK > versão 25`)

### Executar

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.

O banco H2 é criado automaticamente em `./data/veiculosdb` (arquivo local).

### Console H2

Acesse `http://localhost:8080/h2-console` para visualizar o banco:
- **JDBC URL:** `jdbc:h2:file:./data/veiculosdb`
- **User:** `sa`
- **Password:** *(vazio)*

---

## Autenticação JWT

A API usa autenticação **stateless** com tokens JWT. O fluxo é:

1. O cliente faz `POST /login` com login e senha
2. A API valida as credenciais e retorna um **token JWT** (válido por 2 horas)
3. O cliente envia o token em todas as requisições seguintes no header `Authorization: Bearer <token>`
4. A API valida o token e libera o acesso

### Usuário de teste

| Campo | Valor |
|-------|-------|
| Login | `admin@email.com` |
| Senha | `admin123` |

---

## Endpoints

| Método | Rota | Acesso | Descrição |
|--------|------|--------|-----------|
| `POST` | `/login` | 🔓 Público | Autenticação (retorna token JWT) |
| `GET` | `/consertos` | 🔒 Autenticado | Lista consertos ativos |
| `GET` | `/consertos/full` | 🔒 Autenticado | Lista todos os consertos (paginado) |
| `GET` | `/consertos/{id}` | 🔒 Autenticado | Busca conserto por ID |
| `POST` | `/consertos` | 🔒 Autenticado | Cadastra novo conserto |
| `PATCH` | `/consertos/{id}` | 🔒 Autenticado | Atualiza conserto (data saída, mecânico) |
| `DELETE` | `/consertos/{id}` | 🔒 Autenticado | Exclusão lógica (marca como inativo) |

---

## Testes com cURL

### 1. Login com senha correta — recebe o token

```bash
curl -s -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"login": "admin@email.com", "senha": "admin123"}'
```

**Resposta esperada (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

### 2. Login com senha errada

```bash
curl -s -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"login": "admin@email.com", "senha": "senhaerrada"}'
```

**Resposta esperada (401 Unauthorized):**
```json
{
  "status": 401,
  "erro": "Credenciais inválidas"
}
```

---

### 3. Acessar rota protegida SEM token

```bash
curl -s -X GET http://localhost:8080/consertos
```

**Resposta esperada: 403 Forbidden**

---

### 4. Acessar rota protegida com token INVÁLIDO

```bash
curl -s -X GET http://localhost:8080/consertos \
  -H "Authorization: Bearer tokeninvalidoqualquer123"
```

**Resposta esperada: 403 Forbidden**

---

### 5. Fluxo completo — Login + usar token

```bash
# Faz login e salva o token na variável TOKEN
TOKEN=$(curl -s -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"login": "admin@email.com", "senha": "admin123"}' \
  | python3 -c "import sys,json; print(json.load(sys.stdin)['token'])")

echo "Token: $TOKEN"
```

---

### 6. Listar consertos (com token válido)

```bash
curl -s -X GET http://localhost:8080/consertos \
  -H "Authorization: Bearer $TOKEN"
```

**Resposta esperada (200 OK):**
```json
[]
```
*(lista vazia se não tiver consertos cadastrados)*

---

### 7. Cadastrar um conserto

```bash
curl -s -X POST http://localhost:8080/consertos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "dataEntrada": "10/11/2026",
    "dataSaida": "15/11/2026",
    "mecanicoResponsavel": {
      "nome": "Carlos Silva",
      "anosDeExperiencia": 5
    },
    "veiculo": {
      "marca": "Toyota",
      "modelo": "Corolla",
      "ano": 2022,
      "cor": "Prata"
    },
    "ativo": true
  }'
```

**Resposta esperada (201 Created):**
```json
{
  "id": 1,
  "dataEntrada": "10/11/2026",
  "dataSaida": "15/11/2026",
  "mecanicoResponsavel": { "nome": "Carlos Silva", "anosDeExperiencia": 5 },
  "veiculo": { "marca": "Toyota", "modelo": "Corolla", "ano": 2022, "cor": "Prata" },
  "ativo": true
}
```

---

### 8. Buscar conserto por ID

```bash
curl -s -X GET http://localhost:8080/consertos/1 \
  -H "Authorization: Bearer $TOKEN"
```

**Resposta esperada (200 OK):**
```json
{
  "id": 1,
  "dataEntrada": "10/11/2026",
  "dataSaida": "15/11/2026",
  "nomeMecanico": "Carlos Silva",
  "marca": "Toyota",
  "modelo": "Corolla",
  "cor": "Prata"
}
```

---

### 9. Atualizar conserto (PATCH)

```bash
curl -s -X PATCH http://localhost:8080/consertos/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "dataSaida": "20/11/2026",
    "nomeMecanico": "João Santos",
    "anosDeExperiencia": 10
  }'
```

**Resposta esperada (200 OK):** conserto com dados atualizados.

---

### 10. Excluir conserto (exclusão lógica)

```bash
curl -s -X DELETE http://localhost:8080/consertos/1 \
  -H "Authorization: Bearer $TOKEN"
```

**Resposta esperada: 204 No Content**

Após a exclusão, o conserto não aparece mais no `GET /consertos` (só no `GET /consertos/full`).

---

### 11. Listar todos os consertos com paginação

```bash
curl -s -X GET "http://localhost:8080/consertos/full?page=0&size=10" \
  -H "Authorization: Bearer $TOKEN"
```

**Resposta esperada (200 OK):** página com todos os consertos (incluindo inativos).

---

## Variáveis de ambiente

| Variável | Descrição | Padrão |
|----------|-----------|--------|
| `JWT_SECRET` | Chave secreta para assinar tokens JWT | `minha-chave-secreta-prw3-veiculos-2024` |

Em produção, defina uma chave forte:

```bash
JWT_SECRET=uma-chave-muito-segura-e-longa ./mvnw spring-boot:run
```
