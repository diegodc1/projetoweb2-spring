# Manutenção de Equipamentos - Backend

Este repositório contém a implementação do backend para o sistema de **Manutenção de Equipamentos**, desenvolvido em **Java** utilizando o framework **Spring Boot**. O sistema fornece APIs RESTful que suportam as funcionalidades descritas no documento de requisitos.

---

## 🚀 Funcionalidades Principais

### ✅ **RF001 - Autocadastro**
- Qualquer pessoa pode se cadastrar no sistema, indicando: CPF (único), Nome, E-mail (único), Endereço completo, Telefone. 
- Para o endereço o usuário deve digitar o CEP e os dados devem ser preenchidos (usando API ViaCEP). Mesmo assim, todos os dados do endereço devem ser armazenados. 
- O login será o e-mail da pessoa, e uma senha aleatória de 4 números, que será enviada por e-mail no ato do autocadastro.

### ✅ **RF002 - Login**
- Qualquer pessoa cadastrada (qualquer perfil) pode fazer login no sistema informando seu e-mail e senha.
- O sistema automaticamente identifica o perfil do usuário.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
  - Spring Data JPA
  - Spring Security
  - Spring Web
  - Spring Mail
- **Hibernate**
- **Banco de Dados**: PostgreSQL
- **API Externa**: [ViaCEP](https://viacep.com.br/) para busca de endereços por CEP
- **Ferramentas**:
  - Maven para gerenciamento de dependências
  - Postman para testes de API

---

## 🔧 Configuração do Ambiente

### Pré-requisitos

- **Java 17** ou superior instalado.
- **PostgreSQL** configurado e rodando.
- Ferramenta para testar API (Postman).

### Configuração do Banco de Dados

TODO

### Configuração do E-mail

TODO - Adicionar um email específico para a aplicação (ex.fixtech@gmail.com)

---

## 🔄 Executando o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/diegodc1/projetoweb2-spring.git
   cd projetoweb2-spring
   ```

2. Compile o projeto:
   ```bash
   mvn clean install
   ```

3. Inicie o servidor:
   ```bash
   mvn spring-boot:run
   ```

4. O backend estará disponível em:
   ```
   http://localhost:8080
   ```

---

## 🧪 Testando a API com Postman

### Exemplos de Endpoints

- **Autocadastro**: 
  - `POST /api/usuarios/autocadastro`
  - Corpo:
    ```json
    {
      "nome": "João Silva",
      "cpf": "12345678900",
      "email": "joao@gmail.com",
      "telefone": "123456789",
      "endereco": {
        "cep": "01001000",
        "numero": "123",
        "complemento": "Apto 101"
      }
    }
    ```

- **Listar Usuários**:
  - `GET /api/usuarios`

- **Deletar Usuário**:
  - `DELETE /api/usuarios/delete/{id}`

---

## 📄 Estrutura do Projeto

### Principais Diretórios

- **`/src/main/java/com/web2/projetoweb2`**
  - `config/`: Configurações do Spring Security.
  - `entity/`: Classes de modelo da aplicação.
  - `repository/`: Interfaces para acesso ao banco de dados.
  - `services/`: Lógica de negócios.
  - `rest/`: Controladores para APIs REST.
  - `dto/`: 

- **`/src/main/resources`**
  - `application.properties`: Configurações de ambiente.
