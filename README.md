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

### ⚔️ Crônicas do Concílio: A Busca pela Elegância (V5 e V6)

Durante o clube de leitura, surgiu a dúvida: *"Ok, a Calculadora V3 está limpa, mas onde ficam os IFs que decidem qual regra usar? Como fazemos isso na vida real?"*

Isso nos levou a criar duas novas versões avançadas:

### 🏭 v5_calculadora_factory (A Forja de Armas)
Criamos uma classe **Factory** (`CalculadoraFactory`) responsável por fabricar a Calculadora.
* **Como funciona:** A Factory recebe o tipo de cliente ("VIP", "COMUM") e decide qual implementação instanciar.
* **Vantagem:** Centraliza a complexidade de criação em um único lugar.
* **Limitação:** A própria Factory ainda tem `if/else` e viola o OCP (se surgir um novo cliente, editamos a Factory).

### 🚀 v6_calculadora_strategy_map (A Solução Definitiva com Spring)
Aqui atingimos o nível **Ninja Supremo**. Usamos o poder do **Spring Boot** para eliminar *todos* os IFs.

* **A Mágica:**
    1. Criamos as implementações (`FreteCorreios`, `FreteGratis`) e as anotamos com `@Component("NOME")`.
    2. O Spring injeta automaticamente todas elas em um `Map<String, ServicoDeEntrega>`.
    3. O `CalculadoraService` apenas busca no mapa: `mapa.get("GRATIS")`.

* **Resultado:**
    * **Zero IFs:** A decisão é feita por lookup no mapa.
    * **Extensibilidade Infinita:** Quer um novo frete? Crie a classe `FreteDrone`, anote com `@Component("DRONE")` e pronto. Não precisa tocar em nenhuma outra classe. O sistema detecta o novo plugin automaticamente.

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

## 🔒 Capítulo 5: Encapsulamento e Propagação de Mudanças

> *"O lobo branco não pergunta ao monstro como ele ataca. Ele o estuda, aprende o padrão, e age. Código bem encapsulado funciona igual: você sabe o que ele faz, mas não precisa saber como."*

Encapsulamento não é só `private` nos atributos. É **esconder o COMO e expor só o QUÊ**.

Visitando a pasta `capitulo5_encapsulamento`, exploramos 5 versões:

### 📂 v1 — Problema de Encapsulamento
A `Fatura` expõe sua lista interna de pagamentos diretamente.
* **Problema 1:** `fatura.getPagamentos().add(pagamento)` — qualquer um modifica a lista.
* **Problema 2:** A regra "quando a fatura está paga" vive FORA da Fatura (no `ProcessadorDeBoletos`).
* **Consequência:** Propagação de mudanças descontrolada — a regra é duplicada em vários processadores.

### 📂 v2 — Intimidade Inapropriada
O `CalculadorDeImposto` sabe demais sobre `NotaFiscal`.
* **Problema:** `if (nf.getValorSemImposto() > 10000)` — a regra fiscal vive fora da NotaFiscal.
* **Solução:** **Tell, Don't Ask** — `nf.calculaValorImposto()` encapsula a regra.

### 📂 v3 — Lei de Demeter
O `ServicoDeCobranca` viola a Lei de Demeter.
* **Problema:** `fatura.getCliente().marcaComoInadimplente()` — getter encadeado cria acoplamento oculto.
* **Solução:** `fatura.marcaComoInadimplente()` — a Fatura encapsula o acesso ao Cliente.

### 📂 v4 — Solução Completa (Modelo Rico)
A `Fatura` encapsulada com todas as boas práticas:
* ✅ `adicionaPagamento()` — única porta de entrada, decide sozinha quando está paga.
* ✅ `getPagamentos()` retorna lista imutável — ninguém modifica externamente.
* ✅ `setPago()` removido — não há razão para existir.

### 📂 v5 — Modelo Anêmico (Antipadrão)
Demonstração do que **NÃO fazer**:
* ❌ Classe de dados sem comportamento (`Fatura` só com getters/setters).
* ❌ Lógica separada em classes de serviço (`FaturaBLL`, `FaturaService`).
* ❌ Código procedural disfarçado de OO.

### 🎯 Conceitos Chave do Capítulo

**Tell, Don't Ask:**
> Não pergunte ao objeto para depois tomar uma decisão. Diga a ele o que fazer.

**Lei de Demeter:**
> Fale apenas com seus amigos imediatos. Evite `a.getB().getC().metodo()`.

**Getters Perigosos:**
> Retornar listas mutáveis é abrir a porta dos fundos. Use `Collections.unmodifiableList()`.

**Teste do Encapsulamento (Aniche, seção 5.5):**
- **O quê** esse método faz? → Você deve conseguir responder pelo nome.
- **Como** ele faz? → Você **não** deve conseguir responder só olhando de fora.

---

## 🧬 Capítulo 6: O Dilema do Sangue (Herança vs. Composição)

> *"Herança é como uma maldição de sangue: você herda os poderes do seu pai, mas também suas fraquezas. Composição é como escolher suas armas: você pega apenas o que precisa para a caçada."*

Neste capítulo, exploramos o **Liskov Substitution Principle (LSP)** e o eterno debate entre estender classes ou compor comportamentos.

Visitando a pasta `capitulo6_heranca_composicao`, exploramos 6 versões:

### 📂 v1 — A Maldição da Herança (LSP Violado)
A tentativa ingênua de reutilizar código através da herança leva à quebra de contratos.
* **Problema:** `ContaDeEstudante` herda de `ContaComum`, mas não rende juros.
* **Violação LSP:** Lança exceção onde o pai não lançava — quebra o contrato.

### 📂 v2 — O Dilema Geométrico (Quadrado é Retângulo?)
Matematicamente, todo quadrado é um retângulo. Em OO, **isso é uma mentira**.
* **Problema:** `Quadrado` sobrescreve setters para manter lados iguais, causando efeitos colaterais inesperados.
* **Violação LSP:** Quem usa `Retangulo` não espera que mudar a largura afete a altura.

### 📂 v3 — O Acoplamento de Sangue (Pai e Filho)
Herança cria o acoplamento mais forte possível em OO. Se o pai muda, o filho pode quebrar.
* **Problema:** `Gerente` usa `super.bonus()` e depende da implementação interna do pai.
* **Solução:** O filho deve implementar sua regra de forma explícita e independente.

### 📂 v4 — A Cura pela Composição
Em vez de "ser uma" Conta Comum, ambas as contas "têm um" `ManipuladorDeSaldo`.
* **Vantagens:** Segurança (não expõe métodos indevidos), reuso, testabilidade.
* **Princípio:** "Favoreça a composição sobre a herança." — Gang of Four

### 📂 v5 — O Pergaminho Antigo (Herança para DSLs)
Existe um caso onde a herança é aceitável: **Testes e DSLs**.
* **Uso:** Esconder complexidade do Selenium/WebDriver criando linguagem fluente.
* **Veredito:** Em testes, a legibilidade vale o preço do acoplamento.

### 📂 v6 — O Mundo Real (A Guilda do Leão)
Aplicação completa unindo Herança e Composição em cenário de RPG.
* **Herança (onde faz sentido):** Todos são `Cacador`, compartilham atributos estáveis.
* **Composição (onde varia):** Taxa da guilda injetada como estratégia `CalculadorDeTaxa`.
* **Resultado:** `Bruxo`, `Mago` e `Arqueiro` com regras próprias, sem duplicação.

### 🎯 Conceitos Chave do Capítulo

**Liskov Substitution Principle (LSP):**
> A subclasse não pode exigir mais (pré-condições mais fortes) nem entregar menos (pós-condições mais fracas) que o pai.

**Quando usar Herança:**
- Relação "**é um**" verdadeira (Bruxo **é um** Caçador)
- Comportamento compartilhado estável
- LSP respeitado (substituição sem quebrar)

**Quando usar Composição:**
- Relação "**tem um**" (Conta **tem um** Manipulador)
- Comportamento variável ou plugável
- Evitar acoplamento forte

---

## ⚖️ Capítulo 7: Interfaces Magras e o tal do ISP

> *"Uma espada de duas mãos é inútil para um ladino que precisa de uma adaga. Não force uma classe a carregar o peso de métodos que ela não usa."*

O **Interface Segregation Principle (ISP)** prega que interfaces devem ser coesas e específicas.

Visitando a pasta `capitulo7_interfaces_magras`, exploramos 5 versões:

### 📂 v1 — Interface Gorda (Fat Interface)
A interface `Imposto` obrigava a implementar `geraNota()` e `imposto()`.
* **Problema:** O imposto `IXMX` não gera nota.
* **Gambiarra:** A classe `IXMX` lança `NaoGeraNotaException` ou retorna `null`. Isso polui o código.

### 📂 v2 — Interfaces Coesas
Dividimos a interface gorda em duas magras: `CalculadorDeImposto` e `GeradorDeNota`.
* **Resultado:** `ISS` implementa ambas. `IXMX` implementa apenas `CalculadorDeImposto`. Ninguém é forçado a mentir.

### 📂 v3 — Interface Mínima (Tributável)
Em vez de o `CalculadorDeImposto` depender da classe gigante `NotaFiscal`, ele depende da interface mínima `Tributavel`.
* **Ganho:** Desacoplamento. Podemos calcular imposto de qualquer coisa (Carro, Casa, Serviço) desde que implemente `Tributavel`.

### 📂 v4 — Repositório e Fábrica
Aplicamos interfaces para infraestrutura.
* **Repositório:** `RepositorioDeFaturas` (Interface) esconde o banco de dados.
* **Fábrica:** `FabricaDeCalculadora` centraliza a criação de objetos complexos.

### 📂 v5 — Exemplo Real (Sistema de Notificações)
Um sistema onde canais de notificação (Email, SMS, Slack) são plugáveis.
* **Interfaces Magras:** `Notificavel` (quem recebe) e `CanalDeNotificacao` (quem envia).
* **Composição:** O `ServicoDeNotificacao` recebe uma lista de canais e dispara para todos.

### 🎯 Conceitos Chave do Capítulo

**Interface Segregation Principle (ISP):**
> Clientes não devem ser forçados a depender de métodos que não usam.

**Coesão de Interface:**
> Uma interface deve ter uma única responsabilidade. Se ela faz duas coisas (calcula imposto E gera nota), ela deve ser quebrada.

**Interface Mínima:**
> Ao definir um parâmetro de método, peça o mínimo necessário (`Tributavel`), não o objeto inteiro (`NotaFiscal`).

---

## 📊 Resumo dos Princípios SOLID Abordados

| Sigla | Princípio | Capítulo | Aplicação |
| :---: | :--- | :---: | :--- |
| **S** | Single Responsibility | 2 | Classes de regra separadas, cada uma com uma única razão para mudar |
| **O** | Open/Closed | 4 | Calculadora aberta para extensão (novas tabelas/fretes) sem modificação |
| **L** | Liskov Substitution | 2, 4, 6 | Subclasses podem substituir a classe base sem quebrar o sistema |
| **I** | Interface Segregation | 2, 7 | Interfaces magras (`Tributavel`, `DadosParaCalculo`) evitam acoplamento inútil |
| **D** | Dependency Inversion | 3, 4, 7 | Dependemos de abstrações (interfaces), não de implementações concretas |

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
│   │   │   ├── v4_exemplo_real/
│   │   │   ├── v5_calculadora_factory/ # Factory Pattern
│   │   │   └── v6_calculadora_strategy_map/ # Spring Strategy Map
│   │   ├── capitulo5_encapsulamento/   # Encapsulamento e Tell Don't Ask
│   │   │   ├── v1_problema_encapsulamento/
│   │   │   ├── v2_intimidade_inapropriada/
│   │   │   ├── v3_lei_de_demeter/
│   │   │   ├── v4_solucao_completa/
│   │   │   └── v5_modelo_anemico/
│   │   ├── capitulo6_heranca_composicao/ # LSP e Herança vs Composição
│   │   │   ├── v1_lsp_violacao/
│   │   │   ├── v2_lsp_quadrado_retangulo/
│   │   │   ├── v3_acoplamento_pai_filho/
│   │   │   ├── v4_composicao/
│   │   │   ├── v5_heranca_dsl/
│   │   │   └── v6_mundo_real/
│   │   ├── capitulo7_interfaces_magras/ # ISP e Interfaces Coesas
│   │   │   ├── v1_interface_gorda/
│   │   │   ├── v2_interfaces_coesas/
│   │   │   ├── v3_tributavel/
│   │   │   ├── v4_repositorio_fabrica/
│   │   │   └── v5_mundo_real/
│   │   └── infra/                      # Utilitários (Console UTF-8)
│   └── test/java/
│       ├── capitulo2_coesao/           # Testes unitários do Cap. 2
│       ├── capitulo3_acoplamento/      # Testes unitários + ArchUnit
│       ├── capitulo4_ocp/              # Testes com Mocks e Integração
│       │   └── v6_calculadora_strategy_map/ # Testes de Integração Spring
│       ├── capitulo5_encapsulamento/   # Testes de Encapsulamento
│       ├── capitulo6_heranca_composicao/ # Testes de Herança e LSP
│       └── capitulo7_interfaces_magras/ # Testes de ISP
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

# Capítulo 5 - Encapsulamento
./gradlew run --args="capitulo5_encapsulamento.SimuladorDeEncapsulamento"

# Capítulo 6 - Herança vs Composição
./gradlew run --args="capitulo6_heranca_composicao.SimuladorDeHeranca"

# Capítulo 7 - Interfaces Magras (ISP)
./gradlew run --args="capitulo7_interfaces_magras.SimuladorDeInterfaces"
```

### Executar os Testes

```bash
# Todos os testes
./gradlew test

# Testes de um capítulo específico
./gradlew test --tests "capitulo2_coesao.*"
./gradlew test --tests "capitulo3_acoplamento.*"
./gradlew test --tests "capitulo4_ocp.*"
./gradlew test --tests "capitulo5_encapsulamento.*"
./gradlew test --tests "capitulo6_heranca_composicao.*"
./gradlew test --tests "capitulo7_interfaces_magras.*"

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
* **Spring Boot** (A magia moderna)
* **Gradle** (O construtor de artefatos)
* **JUnit 5** (A prova dos 9)
* **Mockito** (O mestre dos disfarces)
* **REST Assured** (Testes de API)
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
