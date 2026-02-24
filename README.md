# 🐺 O Códice do Lobo Branco: Orientação a Objetos e SOLID

> *"O código é como uma espada de prata: se não for bem forjado e balanceado, ele quebrará na primeira batalha contra um Grifo (ou um bug em produção)."*

Este repositório documenta meus estudos sobre o livro **"Orientação a Objetos e SOLID para Ninjas"** (Maurício Aniche), adaptando os ensinamentos para a **Escola do Lobo**. Aqui, não apenas matamos monstros (bugs), nós entendemos sua anatomia para evitar que eles voltem.

---

## ⚔️ Capítulo 1: A Arte da Espada (OO vs Procedural)

No início da jornada, aprendemos que Orientação a Objetos não é apenas sintaxe, é **estratégia**.

- **Código Procedural:** É como um camponês tentando matar um dragão com um forcado. Ele foca apenas na execução imediata (algoritmo), misturando dados e comportamento.
- **Código OO:** É o estilo do Bruxo. Focamos no projeto das classes (o preparo), em como as peças se encaixam (o quebra-cabeça) e como a mudança em uma peça (uma poção) afeta o todo.

> **A Lição de Kaer Morhen:** O desenho da peça (Classe) é importante, mas o encaixe dela (Acoplamento) é vital.

---

## 🔮 Capítulo 2: A Maldição da Coesão e o SRP

O foco deste capítulo é o **Single Responsibility Principle (SRP)**. Uma classe deve ter apenas uma razão para mudar, assim como uma espada tem apenas um propósito: o de matar.

Visitando a pasta `capitulo2_coesao`, você verá a evolução da refatoração em 4 estágios:

1.  **📂 v1 (A Striga):** A *God Class* cheia de `if/else` e regras misturadas.
2.  **📂 v2 (A Estratégia):** O uso do padrão *Strategy* para separar as regras de cálculo.
3.  **📂 v3 (O Elixir):** O uso de *Enums com Comportamento* para eliminar decisões condicionais.
4.  **📂 v4 (A Máscara):** A aplicação do *ISP* para proteger a entidade.

---

## 🔗 Capítulo 3: As Amarras do Destino (Acoplamento e DIP)

Neste capítulo, saímos das muralhas internas da classe para observar como elas interagem entre si. Aprendemos que depender de classes concretas é perigoso.

Visitando a pasta `capitulo3_acoplamento`, exploramos dois cenários de batalha:

### 🛡️ Cenário A: O Gerador de Notas (DIP)
Combatemos o acoplamento rígido a serviços externos (Email, DAO).
* **v1 (Concreto):** O Gerador dependia de classes específicas. Difícil de testar e evoluir.
* **v2 (Abstrato):** O Gerador depende de uma lista de interfaces (`AcaoAposGerarNota`).
* **v3 (Hexagonal):** A aplicação completa do DIP, isolando o domínio da infraestrutura através de *Ports & Adapters*.

### 📦 Cenário B: O Despachador de Notas (Encapsulamento)
Combatemos o micro-gerenciamento e a "Intimidade Indesejada".
* **v1 (Micro-Gerenciador):** O Despachador sabia demais sobre regras de Correios e Leis.
* **v2 (Delegador):** Encapsulamos a lógica no `EntregadorDeNFs`. O Despachador apenas ordena.
* **v3 (Hexagonal):** O Despachador torna-se um *Use Case* puro, dependendo apenas de interfaces.

> **Lição Aprendida:** "Programe para uma interface, não para uma implementação."

---

## 📜 O Grande Debate: Passar o Objeto ou o Valor?

Durante o concílio dos bruxos (clube de leitura), surgiu um debate interessante sobre o método `calcula()`. Como devemos passar os dados para a regra?

### As Opções na Mesa:

| Abordagem | Vantagem (Luz) | Desvantagem (Sombra) |
| :--- | :--- | :--- |
| **Apenas Valor**<br>`calcula(double salario)` | **Desacoplamento.** A regra vira matemática pura. Não sabe o que é um funcionário. | **Rigidez.** Se a regra mudar (ex: depender de "Tempo de Casa"), quebramos todos os contratos. |
| **Objeto Completo**<br>`calcula(Funcionario f)` | **Extensibilidade.** Se a regra mudar, o objeto já tem os dados. O contrato não quebra. | **Risco.** A regra ganha acesso a métodos que não deveria (ex: `getSenha`, `getPedidos`), podendo causar problemas com ORM/Banco. |

### 🛡️ O Veredito do Mestre (A Solução v4)
Para resolver esse impasse, aplicamos o **Interface Segregation Principle (ISP)**.

Criamos uma interface "Máscara" (`DadosParaCalculo`) que expõe *apenas* o salário e o cargo.
- O `Funcionario` veste essa máscara.
- A regra só vê a máscara.
- O código fica **seguro** (sem acesso indevido) e **extensível**.

---

## 🏰 A Visão do Futuro: Arquitetura Hexagonal e Clean Architecture

O Capítulo 3 consolida a visão de arquiteturas avançadas, como a **Hexagonal (Ports and Adapters)** e a **Clean Architecture**.

Ao separar a `RegraDeCalculo` (Lógica de Negócio) da `Calculadora/Controller` (Fluxo), nós isolamos o **Domínio**.

* **No mundo Procedural:** A lógica de negócio está suja com SQL, Tela e HTTP. Se você troca o banco, a regra quebra.
* **No mundo do Bruxo (Hexagonal):** O Domínio (Kaer Morhen) fica no centro, protegido. O Banco de Dados e a Web são apenas "detalhes" externos (monstros ou clientes) que se conectam através de portões (Interfaces).

**Conclusão:** Praticar o SRP, a Coesão e o DIP é o primeiro passo para construir fortalezas impenetráveis.

---

## 🔓 Capítulo 4: Classes Abertas e o Tal do OCP

> *"Um Bruxo não muda o feitiço toda vez que encontra um novo monstro. Ele aprende novos feitiços. O livro de feitiços está aberto para extensão, mas cada feitiço já escrito está fechado para modificação."*

O **Open/Closed Principle (OCP)** é o segundo dos princípios SOLID. Este capítulo ensina como fazer o sistema evoluir sem precisar mexer no que já funciona.

Visitando a pasta `capitulo4_ocp`, você verá a evolução em 4 estágios:

### 📂 v1 — O Problema Original (A Calculadora Rígida)
A `CalculadoraDePrecos` instancia suas dependências diretamente (`new TabelaDePrecoPadrao()`, `new Frete()`).
* **Problema:** Para adicionar uma nova tabela de preços ou frete, precisamos modificar a classe.
* **Violação:** Não está aberta para extensão, nem fechada para modificação.

### 📂 v2 — O Problema dos IFs (A Solução Errada)
Quando surgem múltiplas regras, o desenvolvedor ingênuo adiciona `ifs`.
* **Problema:** A classe vira uma God Class disfarçada, conhecendo todas as tabelas e fretes do sistema.
* **Violação:** Cada nova regra exige abrir e modificar a classe (viola OCP).

### 📂 v3 — A Calculadora Aberta (A Solução Correta)
Criamos abstrações (`TabelaDePreco`, `ServicoDeEntrega`) e injetamos pelo construtor.
* **✅ Aberta para extensão:** Novas tabelas e fretes podem ser criados sem tocar na Calculadora.
* **✅ Fechada para modificação:** A Calculadora nunca mais precisa mudar.
* **Bônus:** Testabilidade — podemos passar mocks pelo construtor.

### 📂 v4 — Exemplo Real (Sistema de Exercícios da Caelum)
Código real extraído da plataforma de ensino da Caelum.
* **Antes:** `ShowAnswerHelper` cheio de `ifs` para cada tipo de exercício.
* **Depois:** Polimorfismo puro — cada exercício sabe como se exibir através de `viewDetails()`.

### 🎯 A Regra de Ouro do OCP

> *"Seu sistema deve evoluir por meio de novos códigos, não de alterações em códigos já existentes."* — Maurício Aniche

**Como conseguir:**
1. Identifique o que muda (a variação)
2. Crie uma abstração (interface)
3. Injete pelo construtor (não instancie dentro da classe)

**Por que construtor e não setter?**
> "Construtores obrigam os clientes a passarem as dependências no momento da criação. O compilador fará o trabalho de avisá-lo."

### 🧪 Testabilidade: O Termômetro do Design

> *"Se está difícil de testar, é porque seu código pode ser melhorado."* — Maurício Aniche

Classes abertas (OCP) são facilmente testáveis:
* **v1 (Rígida):** Forçados a testar junto com dependências concretas.
* **v3 (Aberta):** Passamos mocks pelo construtor, isolando a lógica.

### ⚖️ IFs Nunca Mais? Abstrações Sempre?

> "Talvez não. Códigos flexíveis têm um custo agregado: eles são mais complexos. Muitas vezes um simples IF resolve o problema. Portanto, seja parcimonioso."

**Regra prática:** Se uma variação já ocorreu mais de uma vez, ou se é previsível que ocorra, crie a abstração. Caso contrário, o IF pode ser mais simples.

---

## 📊 Resumo dos Princípios SOLID Abordados

| Sigla | Princípio | Capítulo | Aplicação |
| :---: | :--- | :---: | :--- |
| **S** | Single Responsibility | 2 | Classes de regra separadas, cada uma com uma única razão para mudar |
| **O** | Open/Closed | 4 | Calculadora aberta para extensão (novas tabelas/fretes) sem modificação |
| **L** | Liskov Substitution | 2, 4 | Qualquer implementação pode substituir outra sem quebrar o sistema |
| **I** | Interface Segregation | 2 | A máscara `DadosParaCalculo` protege o objeto `Funcionario` |
| **D** | Dependency Inversion | 3, 4 | Dependemos de abstrações (interfaces), não de implementações concretas |

---

## 📁 Estrutura do Projeto

```
oo-solid-ninjas/
├── src/
│   ├── main/java/
│   │   ├── capitulo1_mindset/          # OO vs Procedural
│   │   ├── capitulo2_coesao/           # SRP e Strategy Pattern
│   │   │   ├── v1_a_maldicao_god_class/
│   │   │   ├── v2_o_sinal_strategy/
│   │   │   ├── v3_o_elixir_enum/
│   │   │   └── v4_a_mascara_isp/
│   │   ├── capitulo3_acoplamento/      # DIP e Arquitetura Hexagonal
│   │   │   ├── v1_acoplamento_concreto/
│   │   │   ├── v2_inversao_dependencia/
│   │   │   └── v3_dip_completo/
│   │   ├── capitulo4_ocp/              # Open/Closed Principle
│   │   │   ├── v1_o_problema/
│   │   │   ├── v2_o_problema_dos_ifs/
│   │   │   ├── v3_calculadora_aberta/
│   │   │   └── v4_exemplo_real/
│   │   └── infra/                      # Utilitários (Console UTF-8)
│   └── test/java/
│       ├── capitulo2_coesao/           # Testes unitários do Cap. 2
│       ├── capitulo3_acoplamento/      # Testes unitários + ArchUnit
│       └── capitulo4_ocp/              # Testes com Mocks
└── README.md
```

**Cada capítulo possui:**
- 📄 `README.md` — Explicação detalhada da evolução
- 🎯 `Simulador*.java` — Classe main para executar os exemplos
- ✅ Testes unitários correspondentes

---

## 🚀 Como Executar

### Executar os Simuladores (Main Classes)

```bash
# Capítulo 1 - Comparação OO vs Procedural
./gradlew run --args="capitulo1_mindset.BatalhaOO"

# Capítulo 2 - Evolução da Coesão
./gradlew run --args="capitulo2_coesao.SimuladorDeBatalha"

# Capítulo 3 - Acoplamento e DIP
./gradlew run --args="capitulo3_acoplamento.SimuladorDeAcoplamento"

# Capítulo 4 - Open/Closed Principle
./gradlew run --args="capitulo4_ocp.SimuladorDeOCP"
```

### Executar os Testes

```bash
# Todos os testes
./gradlew test

# Testes de um capítulo específico
./gradlew test --tests "capitulo2_coesao.*"
./gradlew test --tests "capitulo3_acoplamento.*"
./gradlew test --tests "capitulo4_ocp.*"

# Teste arquitetural (ArchUnit)
./gradlew test --tests "capitulo3_acoplamento.ArquiteturaTest"
```

### Build do Projeto

```bash
# Compilar
./gradlew build

# Limpar e recompilar
./gradlew clean build
```

---

## 🛠️ Tecnologias e Ferramentas

* **Java 21** (A linguagem antiga)
* **Gradle** (O construtor de artefatos)
* **JUnit 5** (A prova dos 9)
* **Mockito** (O mestre dos disfarces)
* **ArchUnit** (O guardião da arquitetura)
* **IntelliJ IDEA** (O laboratório)

---

## 📚 Sobre o Livro

**Título:** Orientação a Objetos e SOLID para Ninjas  
**Autor:** Maurício Aniche  
**Editora:** Casa do Código

Este repositório é um registro de aprendizado pessoal, adaptando os conceitos do livro para uma narrativa temática do universo The Witcher.

---

> "Vá, programe, e que seu código seja limpo como a lâmina de Geralt."
