# 🎬 Worst Movie - Golden Raspberry Awards Analyzer

Este projeto em Quarkus lê um arquivo CSV contendo vencedores do prêmio Golden Raspberry ("Pior Filme") e fornece:

- Carga inicial automática dos dados do CSV
- Endpoints para análise de intervalos entre prêmios por estúdio
- Testes de integração validados com JUnit 5

---

## 🚀 Como rodar o projeto

### ✅ Pré-requisitos

- Java 21 
- Maven 3.8+

---

### 🧪 Executando localmente

```bash
./mvnw clean compile quarkus:dev
```
- A aplicação estará disponível em: http://localhost:8080

### 👨‍💻 Endpoints disponíves
- <b>/gaps</b> - retorna as maiores e menores gaps entre premios para mesmo produtor 

### 🗃️ Carga de dados via CSV
Na inicialização do projeto, o arquivo:
```css
src/main/resources/Movielist.csv
```
é automaticamente carregado para popular o banco de dados H2 em memória via o componente DataInitializer.

### ✅ Executando os testes de integração

O projeto inclui testes de integração para validar a integridade do CSV e dos dados carregados.

Para executar todos os testes:
```bash
./mvnw test
```

Para executar um teste específico:
```bash
./mvnw test -Dtest=NomeDoTeste
```
Exemplos:

```bash
./mvnw test -Dtest=CsvInitializationIT
./mvnw test -Dtest=CsvHeaderValidationIT
```
#### 🧪 Testes de integração disponíveis
|Teste|Validação|
|-------------|------------|
|CsvInitializationIT|Verifica se os filmes do CSV foram carregados no banco H2|
|CsvHeaderValidationIT|Verifica se o cabeçalho do CSV é exatamente year;title;studios;producers;winner|
|CsvRequiredFieldsValidationIT|Garante que todas as linhas têm os 4 campos obrigatórios preenchidos|
|CsvYearValidationIT|Valida se o campo year é um inteiro entre 1800 e o ano atual|
|CsvDuplicateLinesValidationIT|Detecta se há linhas duplicadas no CSV|
	
### 🧑‍💻 Autor
<b>Valcir Balbinotti Junior<br></b>
Backend & DevOps Engineer