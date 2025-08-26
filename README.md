# API de Gerenciamento de Tarefas em Microsserviços

Este projeto implementa uma API RESTful para gerenciamento de tarefas utilizando uma arquitetura de microsserviços com Spring Boot e Spring Cloud.

## Arquitetura

A aplicação é dividida em dois microsserviços principais:

1.  **`tarefas-service` (Porta 8082)**: É responsável pela persistência de dados (CRUD) com o banco de dados. Ele expõe uma API interna que não deve ser acessada diretamente pelo cliente.

2.  **`tarefas-gateway` (Porta 8081)**: A porta de entrada (API Gateway) para a aplicação. Ele recebe todas as requisições do cliente e as encaminha para o `tarefas-service` de forma transparente, utilizando o **Spring Cloud OpenFeign** para a comunicação. O cliente final interage **apenas** com este serviço.

Este desacoplamento permite que os serviços sejam desenvolvidos, implantados e escalados de forma independente.

## Tecnologias Utilizadas

* **Java 17**: Linguagem de programação.
* **Spring Boot 3.2.5**: Framework para construção das aplicações.
* **Spring Cloud 2023.0.1**: Para integração entre os microsserviços.
* **Spring Cloud OpenFeign**: Para comunicação declarativa e resiliente entre o gateway e o service.
* **Spring Web**: Para construção das APIs RESTful.
* **Spring Data JPA**: Para persistência de dados.
* **H2 Database**: Banco de dados em memória para desenvolvimento.
* **Lombok**: Biblioteca para reduzir código boilerplate.
* **Maven**: Ferramenta de automação de build.

## Estrutura do Projeto

O projeto é um monorepo Maven multi-módulo, contendo os dois microsserviços:

```
api-tarefas-microservices/
├── tarefas-gateway/              # Módulo do API Gateway (porta 8081)
│   ├── src/main/java
│   │   └── com/example/gateway
│   │       ├── client/           # Interfaces Feign para comunicar com o service
│   │       │   └── TarefaClient.java
│   │       └── controller/       # Controller que expõe os endpoints para o cliente
│   │           └── GatewayController.java
│   └── pom.xml
│
├── tarefas-service/              # Módulo do serviço de negócio (porta 8082)
│   ├── src/main/java
│   │   └── com/example/tarefas
│   │       ├── controller/       # Controller com a API interna
│   │       ├── service/          # Camada de lógica de negócio
│   │       ├── repository/       # Repositórios JPA
│   │       ├── model/            # Entidades e Enums
│   │       └── mapper/           # Mapeadores DTO <-> Entidade
│   └── pom.xml
│
└── pom.xml                       # POM pai que gerencia os módulos
```

## Como Executar o Projeto

### Pré-requisitos

Certifique-se de ter o Java Development Kit (JDK) 17 ou superior instalado.

### Execução

Como temos dois serviços, eles precisam ser iniciados em uma ordem específica. Abra dois terminais na raiz do projeto (`api-tarefas-microservices/`).

1.  **Inicie o `tarefas-service` (Terminal 1):**
    Este serviço precisa estar no ar para que o gateway possa se comunicar com ele.

    ```bash
    ./mvnw spring-boot:run -pl tarefas-service
    ```
    O serviço será iniciado na porta **8082**.

2.  **Inicie o `tarefas-gateway` (Terminal 2):**
    Após o `tarefas-service` iniciar com sucesso, inicie o gateway.

    ```bash
    ./mvnw spring-boot:run -pl tarefas-gateway
    ```
    O gateway será iniciado na porta **8081**.

Agora a aplicação está pronta! Todas as requisições devem ser feitas para a porta `8081` (gateway).

## Endpoints da API

A API expõe os seguintes endpoints através do **gateway**.

### `GET /gateway/tarefas`

Lista todas as tarefas cadastradas.

**Exemplo de Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "titulo": "Comprar Leite",
    "prioridade": "ALTA",
    "status": "PENDENTE",
    "dataLimite": "2025-08-30"
  }
]
```

### `GET /gateway/tarefas/{id}`

Busca uma tarefa específica pelo seu ID.

**Exemplo de Resposta (200 OK):**
```json
{
  "id": 1,
  "titulo": "Comprar Leite",
  "descricao": "Ir ao supermercado",
  "prioridade": "ALTA",
  "status": "PENDENTE",
  "dataLimite": "2025-08-30"
}
```

### `POST /gateway/tarefas`

Cria uma nova tarefa.

**Corpo da Requisição (JSON):**
```json
{
  "titulo": "Estudar Microsserviços",
  "descricao": "Revisar conceitos de API Gateway e Feign",
  "prioridade": "ALTA",
  "status": "PENDENTE",
  "dataLimite": "2025-09-15"
}
```
**Exemplo de Resposta (201 Created):**
```json
{
  "id": 3,
  "titulo": "Estudar Microsserviços",
  "descricao": "Revisar conceitos de API Gateway e Feign",
  "prioridade": "ALTA",
  "status": "PENDENTE",
  "dataLimite": "2025-09-15"
}
```

### `PUT /gateway/tarefas/{id}`

Atualiza uma tarefa existente pelo seu ID.

**Corpo da Requisição (JSON):**
```json
{
  "titulo": "Comprar Leite Desnatado",
  "descricao": "Ir ao supermercado e comprar leite desnatado",
  "prioridade": "MEDIA",
  "status": "CONCLUIDA",
  "dataLimite": "2025-08-30"
}
```
**Exemplo de Resposta (200 OK):**
```json
{
  "id": 1,
  "titulo": "Comprar Leite Desnatado",
  "descricao": "Ir ao supermercado e comprar leite desnatado",
  "prioridade": "MEDIA",
  "status": "CONCLUIDA",
  "dataLimite": "2025-08-30"
}
```

### `DELETE /gateway/tarefas/{id}`

Deleta uma tarefa pelo seu ID.

**Exemplo de Resposta (204 No Content):**
`(Corpo vazio)`