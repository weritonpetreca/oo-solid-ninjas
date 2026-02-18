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

## 🛠️ Tecnologias e Ferramentas

* **Java 21** (A linguagem antiga)
* **JUnit 5** (A prova dos 9)
* **Mockito** (O mestre dos disfarces)
* **ArchUnit** (O guardião da arquitetura)
* **IntelliJ IDEA** (O laboratório)

> “Vá, programe, e que seu código seja limpo como a lâmina de Geralt.”
