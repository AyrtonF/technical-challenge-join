# 🛍️ Product Catalog

Bem-vindo ao **Product Catalog**, uma aplicação fullstack desenvolvida como parte de um teste técnico para vaga de Desenvolvedor Fullstack Júnior (Java + React).

Este projeto consiste em um CRUD completo de produtos, utilizando **Java + Spring Boot** no backend, e **React + Vite** no frontend, com banco de dados **H2 em memória**. A aplicação oferece uma interface amigável, suporte à paginação, validações e documentação interativa via Swagger.

---

## 🚀 Funcionalidades

- ✅ Listagem de produtos (com paginação)
- ✅ Criação de novos produtos
- ✅ Atualização de produtos existentes
- ✅ Exclusão de produtos
- ✅ Validações básicas de campos
- ✅ Documentação Swagger da API
- ✅ Teste unitário básico

---

## 🧠 Tecnologias Utilizadas

### Backend:
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Swagger (Springfox)
- JUnit e Mockito (teste unitário)

### Frontend:
- React 19
- TypeScript
- Vite
- Axios
- React Query
- Bootstrap
- React Icons

---

## 📦 Estrutura do Projeto

```
product-catalog/
├── backend/
│   ├── src/
│   ├── pom.xml
│   └── ...
├── frontend/
│   ├── src/
│   ├── package.json
│   └── ...
├── README.md
```

---

## ⚙️ Como Executar o Projeto

### ✅ Pré-requisitos:
- Java 17+
- Node.js 18+
- Maven
- (Opcional) Docker

---

### 🔧 Executando o Backend

```bash
# Acesse a pasta do backend
cd backend

# Compile e execute o projeto Spring Boot
./mvnw spring-boot:run
```

Após iniciar, o backend estará disponível em:  
👉 http://localhost:8080

A documentação Swagger estará em:  
👉 http://localhost:8080/swagger-ui.html

---

### 🎨 Executando o Frontend

```bash
# Acesse a pasta do frontend
cd frontend

# Instale as dependências
npm install

# Rode o projeto
npm run dev
```

Após isso, o frontend estará acessível em:  
👉 http://localhost:5173

---

## 🧪 Testes

```bash
# Dentro da pasta backend
./mvnw test
```

Incluí um teste unitário básico utilizando JUnit e Mockito, validando a lógica de criação de produto.

---

## 📖 Como Usar a Aplicação

1. Acesse a interface no navegador: [http://localhost:5173](http://localhost:5173)
2. Clique em **"Adicionar Produto"** para cadastrar um novo produto.
3. Preencha os campos obrigatórios e clique em **"Salvar"**.
4. Os produtos cadastrados aparecerão na listagem com opções para **editar** ou **excluir**.
5. A listagem suporta paginação para facilitar a visualização.

---

## 📘 Swagger API Docs

Para testar os endpoints diretamente pela interface Swagger:

- Acesse: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Endpoints disponíveis:
- `GET /products` – Lista todos os produtos
- `POST /products` – Cria um novo produto
- `PUT /products/{id}` – Atualiza um produto existente
- `DELETE /products/{id}` – Exclui um produto
- `GET /products/{id}` – Retorna detalhes de um produto específico

---

## 🧠 Requisitos Atendidos

- [x] Estrutura de produto com campos essenciais
- [x] Banco de dados H2
- [x] Backend com Spring Boot
- [x] Frontend com React
- [x] CRUD completo
- [x] README com instruções
- [x] Teste unitário básico
- [x] Paginação
- [x] Validações básicas
- [x] Documentação com Swagger

---

## 📎 Autor

Desenvolvido por **Ayrton**  
🧾 Contato: 
- 🔗 [LinkedIn](https://www.linkedin.com/in/ayrton-fernandes-de-melo-956a4026b/) 
- 📩 [Email](ayrtonleonardo14@gmail.com)

---

## ✅ Considerações Finais

A aplicação foi pensada para ser simples, funcional e seguir boas práticas de desenvolvimento. Está pronta para ser testada, avaliada e, se necessário, expandida com autenticação, filtros, ordenações e muito mais.

Muito obrigado pela oportunidade! 😊
