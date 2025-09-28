# 📦 Sistema de Microsserviços para Processamento de Pagamentos

## 🎯 Finalidade do Projeto
Este projeto foi desenvolvido como parte do **Trabalho de Conclusão de Curso (TCC)** e tem como objetivo:  

- Implementar um **sistema distribuído** baseado em **microsserviços**.  
- Gerenciar o ciclo de vida de **pedidos e pagamentos**.  
- Integrar **filas de mensageria (RabbitMQ/Kafka)** para comunicação assíncrona.  
- Avaliar e **coletar métricas de escalabilidade** através de **Micrometer, Prometheus e Grafana**.  
- Documentar e disponibilizar uma API REST bem definida com **Swagger/OpenAPI**.  

---

## 🛠️ Tecnologias Utilizadas

### **Backend (Java + Spring Boot)**
- ☕ **Java 17**  
- 🍃 **Spring Boot 3**  
  - `spring-boot-starter-web` → API REST  
  - `spring-boot-starter-data-jpa` → Persistência com JPA/Hibernate  
  - `spring-boot-starter-actuator` → Exposição de métricas  
  - `spring-boot-starter-amqp` → Integração com RabbitMQ  
  - `spring-kafka` → Integração com Kafka  
- 📄 **SpringDoc OpenAPI (Swagger)** → Documentação interativa das APIs  

### **Mensageria**
- 🐇 **RabbitMQ** → Fila para pedidos e pagamentos  
- 🔥 **Apache Kafka** → Stream de eventos e processamento distribuído  

### **Banco de Dados**
- 🐘 **PostgreSQL** → Banco relacional principal  

### **Monitoramento & Observabilidade**
- 📊 **Micrometer** → Coleta de métricas nos microsserviços  
- 📈 **Prometheus** → Armazenamento e consulta das métricas  
- 📉 **Grafana** → Visualização em dashboards  

### **Containerização**
- 🐳 **Docker & Docker Compose** → Orquestração dos serviços (banco, mensageria, Prometheus, Grafana, microsserviços)  

---

### ⚙️ Estrutura de Microsserviços

- **user-service** → Responsável pelo cadastro, autenticação e gestão de usuários (JWT).  
- **produto-service** → Responsável pelo CRUD de produtos.  
- **pedido-service** → Gerencia pedidos, vinculando usuários e produtos.  
- **pagamento-service** → Processa pagamentos (aprovação ou recusa) e emite eventos.  
- **notificacao-service** → Consome eventos e envia notificações simuladas.  

### 🔗 Comunicação entre serviços
- **Síncrona (REST API):**
  - `pedido-service` → consulta usuários no **user-service**  
  - `pedido-service` → consulta produtos no **produto-service**  

- **Assíncrona (Mensageria):**
  - `pedido-service` → envia evento de pedido criado para o **pagamento-service** (RabbitMQ/Kafka)  
  - `pagamento-service` → envia evento de pagamento processado para o **notificacao-service**  

## 📊 Coleta de Métricas

Todos os serviços expõem métricas no endpoint:

Exemplos de métricas coletadas:
- `http_server_requests_seconds_count` → Total de requisições HTTP.  
- `pagamentos_aprovados_total` → Total de pagamentos aprovados.  
- `pagamentos_recusados_total` → Total de pagamentos recusados.  
- `pagamentos_recebidos_total` → Total de pagamentos recebidos.  

Essas métricas são extraídas pelo **Prometheus** e visualizadas em **dashboards Grafana** para análise de escalabilidade.

---

## 📖 Documentação da API

Com **Swagger/OpenAPI**, cada microsserviço expõe documentação interativa no endpoint:  

---

## 🚀 Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/tcc-microsservicos.git
   cd tcc-microsservicos
docker-compose up -d


