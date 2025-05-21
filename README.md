# 🚀 LP1-JavaFX-DataCollection

**🎯 Atividade: Desenvolvimento de Interfaces Gráficas para Coleta de Dados e Apresentação de Resultados**

---

## 📋 Descrição

Este projeto é uma aplicação em JavaFX super amigável que permite coletar dados de diferentes entidades — como **Carro 🚗**, **Cartão de Crédito 💳**, **Bicicleta 🚲**, **Geladeira 🧊**, **Televisão 📺** e **Celular 📱** — instanciar objetos correspondentes e apresentar resultados de forma organizada ao usuário. Desenvolvido para fins acadêmicos, o projeto utiliza Maven para gerenciamento de dependências.

---

## 🛠 Tecnologias Utilizadas

- ☕ Java 17+
- 🎨 JavaFX 17+
- 📦 Apache Maven
- 🖥 IDE sugerida: IntelliJ IDEA, VSCode, Eclipse (com suporte a JavaFX)

---

## ✨ Funcionalidades

1. **📝 Coleta de Dados**
   - Formulários JavaFX para inserção de informações de diferentes entidades.
   - Validações de campos (campos obrigatórios, tipo numérico, limites máximos/mínimos).
2. **📊 Apresentação de Resultados**
   - Exibição dos objetos instanciados em tabelas (`TableView`) com `ObservableList`.
   - Atualização dinâmica de ícones e cores de acordo com valores (por exemplo, nível de bateria, status ligado/desligado).
3. **🔀 Navegação entre Telas**
   - Menu de navegação para alternar entre formulários de cada entidade.
4. **🎨 Uso de Recursos Visuais**
   - Ícones e imagens demonstrativos (em `src/main/resources/org/example/images/`).

---

## ✅ Pré-requisitos

- **Java Development Kit (JDK) 17** ou superior instalado.
- **Apache Maven** instalado e configurado no PATH.
- IDE compatível com JavaFX ou JavaFX configurado manualmente.

---

## 🚀 Como Executar

```bash
# Clone o repositório
git clone https://github.com/DaviGramacho/LP1-JavaFX-DataCollection.git
cd LP1-JavaFX-DataCollection


# Compile e execute com Maven
mvn clean javafx:run

```
---
## 📖 Uso Detalhado

### 📝 Formulários

- **Carro 🚗**: Informe marca, modelo, ano e potência.
- **Cartão de Crédito 💳**: Informe titular, número, limite de crédito e fatura atual (fatura ≤ limite).
- **Bicicleta 🚲**: Informe marca, tipo e número de marchas.
- **Geladeira 🧊**: Informe modelo, capacidade (L) e status (liga/desliga).
- **Televisão 📺**: Informe marca, tamanho da tela (polegadas), status (liga/desliga) e canal atual.
- **Celular 📱**: Informe modelo, sistema operacional, nível de bateria (0–100%) e status (liga/desliga).
- **Lâmpada 💡**: Informe status (ligada/desligada), potência (W) e tipo (LED, incandescente).
- **Livro 📖**: Informe título, autor, gênero e ano de publicação.
- **Pessoa 👤**: Informe nome, idade, ocupação e contato (email/telefone).
- **Relógio ⏰**: Informe marca, formato (analógico/digital), horário atual e alarme configurado.

---

### 📊 Tabela de Resultados

- Cada formulário possui sua própria `TableView` para exibir instâncias.
- As tabelas usam `ObservableList` para refletir alterações em tempo real.

---

### 📦 Dependências do Maven

```maven
<dependencies>
    <!-- JavaFX -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>23.0.2</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>23.0.2</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-media</artifactId>
        <version>23.0.2</version>
    </dependency>
</dependencies>
```

> Desenvolvido por **Davi Gramacho** 🎉
