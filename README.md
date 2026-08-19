# 🍔 Lanchonete Design Patterns API

API REST desenvolvida em **Spring Boot** como entrega final do desafio de **Padrões de Projeto
(Design Patterns)**. Simula o sistema de pedidos de uma lanchonete, aplicando **8 padrões do
catálogo GoF (Gang of Four)** de forma natural dentro do fluxo real da aplicação — criação de
produtos, montagem de pedido, validações, controle de estoque, pagamento e notificação de status.

---

## 🎯 Objetivo

Demonstrar, na prática, como os Design Patterns se encaixam organicamente em uma aplicação Spring
Boot real — muitos deles, inclusive, aproveitando recursos nativos do próprio framework (injeção
de dependência, escopo singleton de beans, etc.) em vez de reimplementados "na unha".

---

## 🧩 Padrões aplicados

| Categoria       | Padrão                     | Onde está                                  | Problema que resolve |
|-----------------|------------------------------|----------------------------------------------|------------------------|
| Criacional       | **Factory Method**          | `factory/`                                    | Centraliza a criação de `Lanche`, `Bebida` e `Sobremesa`, uma fábrica por tipo de produto, resolvidas dinamicamente via `ProdutoFactoryProvider`. |
| Criacional       | **Builder**                 | `builder/PedidoBuilder.java`                  | Monta um `Pedido` passo a passo (cliente, itens, forma de pagamento) com API fluente. |
| Criacional       | **Singleton**                | `singleton/Estoque.java`                      | Aproveita o escopo singleton nativo do Spring (`@Service`) para garantir uma única instância de estoque compartilhada por toda a aplicação. |
| Estrutural        | **Decorator**                | `decorator/`                                  | Adiciona extras ao lanche (bacon, queijo extra, molho especial) dinamicamente, sem explosão de subclasses. |
| Estrutural        | **Adapter**                  | `adapter/`                                    | Adapta um gateway de pagamento "legado" (`GatewayPagamentoExterno`, com assinatura incompatível) para a interface `ProcessadorPagamento` usada internamente. |
| Comportamental    | **Strategy**                  | `strategy/`                                   | Permite trocar a forma de pagamento (PIX, Cartão, Dinheiro) em tempo de execução. |
| Comportamental    | **Observer**                  | `observer/`                                   | Notifica Cliente e Cozinha automaticamente a cada mudança de status do pedido, usando injeção de lista de beans do Spring. |
| Comportamental    | **Chain of Responsibility**   | `chain/`                                      | Encadeia validações (cliente bloqueado → estoque → valor mínimo), com a ordem configurada em `ValidadorPedidoChainConfig`. |

---


---

## ▶️ Como executar

Requisito: **JDK 17+** e **Maven** (ou usar o wrapper `./mvnw`, se o projeto tiver sido gerado
pelo Spring Initializr).

```bash
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`.

Para gerar um `.jar` executável:
```bash
mvn clean package
java -jar target/lanchonete-design-patterns-api-1.0.0.jar
```

---

## 📡 Endpoints

| Método | Rota                              | Descrição |
|--------|-------------------------------------|-------------|
| `POST` | `/api/pedidos`                       | Cria um novo pedido |
| `GET`  | `/api/pedidos`                       | Lista todos os pedidos |
| `GET`  | `/api/pedidos/{id}`                  | Busca um pedido pelo id |
| `PATCH`| `/api/pedidos/{id}/status`           | Atualiza o status do pedido (dispara Observer) |
| `POST` | `/api/pedidos/{id}/pagamento`        | Processa o pagamento do pedido |
| `GET`  | `/api/estoque`                       | Lista o estoque atual |

### Exemplo — criar pedido

```bash
curl -X POST http://localhost:8080/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{
    "cliente": "Maria Silva",
    "itens": [
      { "tipo": "LANCHE", "nome": "X-Burguer", "preco": 18.00, "adicionais": ["BACON", "QUEIJO_EXTRA"] },
      { "tipo": "BEBIDA", "nome": "Refrigerante", "preco": 6.00 },
      { "tipo": "SOBREMESA", "nome": "Sorvete", "preco": 9.00 }
    ],
    "formaPagamento": { "tipo": "PIX" }
  }'
```

### Exemplo — atualizar status (Observer)

```bash
curl -X PATCH http://localhost:8080/api/pedidos/{id}/status \
  -H "Content-Type: application/json" \
  -d '{ "status": "EM_PREPARO" }'
```

### Exemplo — pedido rejeitado pela Chain of Responsibility

```bash
curl -X POST http://localhost:8080/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{
    "cliente": "cliente_inadimplente",
    "itens": [{ "tipo": "BEBIDA", "nome": "Refrigerante", "preco": 6.00 }],
    "formaPagamento": { "tipo": "PIX" }
  }'
```
→ retorna `400 Bad Request` com `"mensagem": "Cliente bloqueado: cliente_inadimplente"`.

---

## 💡 Decisões de design

- **Factory Method em vez de `new` direto**: novos tipos de produto exigem apenas uma nova
  `@Component` implementando `ProdutoFactory` — nada mais muda.
- **Builder para `Pedido`**: evita um construtor com muitos parâmetros, já que um pedido tem
  itens variáveis e forma de pagamento opcional.
- **Decorator para adicionais**: evita subclasses como `LancheComBaconEQueijo`; os extras se
  combinam livremente em tempo de execução via enum `TipoAdicional`.
- **Strategy para pagamento**: novas formas de pagamento entram como nova implementação de
  `FormaPagamento`, sem tocar no restante do sistema (Open/Closed Principle).
- **Observer para notificações**: o Spring injeta automaticamente todos os beans que implementam
  `StatusPedidoObserver` em `PedidoSubject`, então adicionar um novo canal de notificação
  (ex.: e-mail) é só criar mais um `@Component`.
- **Chain of Responsibility para validações**: cada regra de negócio é isolada em sua própria
  classe; a ordem da corrente é montada explicitamente em `ValidadorPedidoChainConfig`.
- **Singleton via Spring**: em vez de reimplementar `getInstance()` na mão, o próprio escopo
  singleton do container do Spring garante a instância única de `Estoque`.
- **Adapter para o gateway de pagamento**: simula integração com uma lib de terceiros com
  assinatura incompatível, sem vazar essa dependência para o resto do sistema.

---

## 🚀 Possíveis evoluções

- Persistir pedidos e estoque em banco de dados (Spring Data JPA), trocando o `PedidoRepository`
  em memória por um repositório real.
- Adicionar testes automatizados (`@SpringBootTest`, `MockMvc`) para os endpoints.
- Adicionar Swagger/OpenAPI (`springdoc-openapi`) para documentação interativa.
- Adicionar um padrão **State** para o ciclo de vida do `StatusPedido`.
- Autenticação/autorização (Spring Security) para os endpoints de administração.

---

## 🛠️ Tecnologias

- Java 17
- Spring Boot 3.3.x (Web, Validation)
- Maven
- Zero dependências externas de persistência — repositório em memória

---

## 📚 Referências de estudo

- Design Patterns: Elements of Reusable Object-Oriented Software (Gang of Four)
- [Refactoring.Guru — Design Patterns](https://refactoring.guru/design-patterns)
- [java-design-patterns (iluwatar) — GitHub](https://github.com/iluwatar/java-design-patterns)
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/index.html)
