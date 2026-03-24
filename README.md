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

## 🔓 Capítulo 4: Classes Abertas e o Tal do OCP

> *"Um Bruxo não muda o feitiço toda vez que encontra um novo monstro. Ele aprende novos feitiços. O livro de feitiços está aberto para extensão, mas cada feitiço já escrito está fechado para modificação."*

O **Open/Closed Principle (OCP)** é o segundo dos princípios SOLID. Este capítulo ensina como fazer o sistema evoluir sem precisar mexer no que já funciona.

Visitando a pasta `capitulo4_ocp`, você verá a evolução retratada pelo livro em 4 estágios:

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

> *"Uma espada de duas mãos é inútil para um ladino que precisa de uma adaga. Não force uma classe a carregar o peso de um arsenal que ela não sabe usar."*

O **Interface Segregation Principle (ISP)** prega que interfaces devem ser coesas, focadas e específicas. Neste capítulo, entendemos que obrigar classes a implementar métodos que não fazem sentido é uma armadilha mortal para o design do software.

Visitando a pasta `capitulo7_interfaces_magras`, exploramos 5 versões:

### 📂 v1 — A Armadura Pesada (Interface Gorda)
Uma única interface `Imposto` tentava fazer duas coisas: calcular valor e gerar nota fiscal.
* **Problema:** O imposto `IXMX` (um imposto menor) não gera nota fiscal. Mas ele é forçado a implementar o método `geraNota()`.
* **A Gambiarra:** O desenvolvedor acaba jogando a toalha e escrevendo `throw new NaoGeraNotaException()` dentro do método. O sistema fica poluído e propenso a quebrar.

### 📂 v2 — O Arsenal Dividido (Interfaces Coesas)
Em vez de uma espada de duas mãos, criamos duas espadas de uma mão.
* **Solução:** Separamos em duas interfaces: `CalculadorDeImposto` e `GeradorDeNota`.
* **Resultado:** O imposto `ISS` implementa as duas. O imposto `IXMX` implementa apenas a de cálculo. Ninguém é forçado a mentir no código.

### 📂 v3 — O Foco da Caçada (Interface Mínima)
Uma classe que calcula imposto não precisa saber a cor, o tamanho e o dono de uma `NotaFiscal`. Ela só precisa saber que o item é tributável e qual o valor.
* **Solução:** Criamos a interface `Tributavel`. O calculador de imposto agora pede um `Tributavel`, não uma `NotaFiscal` inteira.
* **Vantagem:** O acoplamento cai drasticamente. Se amanhã o Rei decidir que cavalos pagam imposto, basta a classe `Cavalo` implementar `Tributavel`. O código de cálculo continua intacto (reutilização extrema).

### 📂 v4 — O Contrato da Guilda (Repositório e Fábrica)
Demonstramos como interfaces são vitais para separar regras de negócio da infraestrutura.
* **Ação:** Em vez de depender do Banco de Dados direto (`FaturaDao`), a classe depende da interface `RepositorioDeFaturas`.
* **Benefício:** Você pode testar suas regras usando um repositório em memória falso (Mocks), sem subir um banco MySQL de verdade.

### 📂 v5 — O Mundo Real (Sistema de Notificações)
Aplicamos o ISP em um serviço de mensagens do Continente.
* **Interfaces Magras:** `Notificavel` (qualquer pessoa que pode receber um aviso) e `CanalDeNotificacao` (qualquer meio de envio: coruja, mago, carta).
* **Resultado:** O `ServicoDeNotificacao` pode enviar mensagens para qualquer entidade usando qualquer canal. Uma composição perfeita e flexível.

### 🎯 Conceitos Chave do Capítulo

**Interface Segregation Principle (ISP):**
> "Nenhum cliente deve ser forçado a depender de métodos que não usa." — Robert C. Martin (Uncle Bob)

**Coesão de Interface:**
> Se sua interface tem métodos não relacionados, ela está gorda. Divida para conquistar.

**O Poder da Interface Mínima:**
> Peça o mínimo que você precisa para fazer o trabalho. Se você só precisa do valor para calcular o imposto, peça uma interface que retorne o valor, não o objeto banco de dados inteiro.

---

## 🧱 Capítulo 8: Consistência, Objetinhos e Objetões

> *"Um objeto sem construtor rico é como um bruxo sem espada: está pronto no papel, mas não serve para nada na caçada."*

Neste capítulo saímos dos princípios SOLID e entramos em boas práticas de **consistência de objetos** — como garantir que objetos nasçam válidos, permaneçam válidos e se comuniquem de forma segura.

Visitando a pasta `capitulo8_consistencia`, exploramos 9 versões organizadas em três camadas:

### 📂 livro_original — Os Exemplos do Aniche
Código fiel ao livro, com as classes originais (`Pedido`, `CPF`, `AlunoComTinyTypes`, `EnderecoImutavel`).
Ponto de partida para entender o conceito antes de ver a versão Witcher.

### 📂 v1 — Construtor Rico
O construtor é o guardião do estado inicial. Se um objeto pode nascer sem seus atributos essenciais, ele nasce como uma armadilha.
* **Problema:** `MissaoSemConstrutorRico` — nasce sem monstro, sem cliente, sem recompensa. `NullPointerException` garantido.
* **Solução:** `Missao` exige os atributos no construtor. Impossível criar uma missão inválida.
* **Bônus:** Dois construtores — um completo, um com `NivelDePerigo.MEDIO` como padrão.

### 📂 v2 — Validando Dados
Aniche separa dois tipos de validação e oferece quatro abordagens:
* **Validação de formato** (campo vazio, e-mail sem `@`) → vai no **Controller**, antes de chegar ao domínio.
* **Validação de negócio** (credencial com formato específico) → vai no **domínio**.

| Abordagem | Quando usar |
| :--- | :--- |
| Construtor com `throw` | Objeto simples, regra única, falha imediata |
| Método `valida()` | Objeto precisa existir antes de ser validado |
| Builder com resultado rico | Separar validação do domínio, retornar lista de erros |
| Validador composto (Chain) | Múltiplas regras intercambiáveis por contexto |

### 📂 v3 — Teorema do Bom Vizinho e Null Object
> *"O que seria do mundo se ninguém nunca passasse nulo para sua classe?"*

* **Problema:** `ServicoDeContratoComNulos` — cheio de `if (cacador != null)` espalhados.
* **Solução:** `CacadorDesconhecido` implementa a mesma interface com comportamento neutro. O serviço nunca recebe `null` — recebe um objeto que sabe se comportar.
* **Regra:** O chamador é o bom vizinho. Se não tem caçador real, passa `new CacadorDesconhecido()`, nunca `null`.

### 📂 v4 — Tiny Types
Quando um construtor tem vários parâmetros do mesmo tipo primitivo, o compilador não avisa se você trocar a ordem.
* **Problema:** `new CacadorSemTinyTypes("Escola do Lobo", "Geralt", ...)` — nome e escola invertidos, compila sem erro.
* **Solução:** `NomeDeGuerra`, `EscolaDeCaca`, `EmailDeCacador`, `NivelDeExperiencia` — cada parâmetro tem seu tipo. Inversão não compila.
* **Bônus:** Cada tiny type carrega sua própria validação (`NivelDeExperiencia(150)` lança exceção).

### 📂 v5 — DTOs do Bem
DTOs não são o problema — o problema é *só* ter DTOs (Modelo Anêmico).
* **Uso correto:** Quando a tela precisa de dados de fontes diferentes que nenhuma classe de domínio representa sozinha.
* **`ResumoDeMissaoDTO`** agrega dados do caçador, da missão e valores calculados. O `MontadorDeResumo` faz a conversão domínio → DTO em um único lugar.

### 📂 v6 — Imutabilidade
Classes imutáveis eliminam uma categoria inteira de bugs: modificações inesperadas em objetos compartilhados.
* **Problema:** `LocalizacaoMutavel.moverPara()` altera o estado interno — quem tinha referência ao objeto vê outro lugar.
* **Solução:** `LocalizacaoImutavel` — toda "mudança" retorna uma nova instância. Original sempre intacta.
* **Lista protegida:** `getAvistamentos()` retorna `Collections.unmodifiableList()` — ninguém modifica por fora.
* **Referência:** `LocalDate`, `LocalDateTime`, `String` do Java seguem exatamente esse padrão.

### 📂 v7 — Classes Feias por Natureza
Algumas classes **nasceram para ser feias**: Controllers, Factories, Adapters.
* `FabricaDeMissao` é procedural, cheia de `ifs` e conversões de tipo — e isso é **normal e esperado**.
* A moral: mantenha o feio **estável e nas pontas**. O domínio deve ser limpo. A fábrica raramente muda.

### 📂 v8 — Nomenclatura
Nomes são o principal instrumento de comunicação no código.
* Verbos para métodos: `calcular`, `buscar`, `validar`
* Substantivos para classes: `CalculadorDeTaxa`, `RepositorioDeContratos`
* Prefixos para booleanos: `isAtivo`, `podeConcluir`, `temContrato`
* Plural para coleções: `missoesDisponiveis`, `cacadoresAtivos`

### 📂 v9 — Mundo Real (Sistema de Registro do Continente)
Aplicação integrada de todos os conceitos em um sistema de cadastro de caçadores da Guilda.
* `Cacador` — construtor rico + tiny types + lista imutável
* `CadastroDeGuilda` — valida antes de criar, retorna `Optional` em vez de `null`
* `FichaDeCacadorDTO` — DTO para exibição, montado pelo próprio domínio
* `ResultadoDeRegistro` — objeto de resultado rico (sucesso ou falha com erros detalhados)

### 🎯 Conceitos Chave do Capítulo

**Construtor Rico:**
> Se o objeto possui atributos sem os quais ele não pode viver, exija-os no construtor. Construtores são contratos.

**Bom Vizinho:**
> Nunca passe `null`. Se não tem o objeto real, passe um Null Object. A responsabilidade é do chamador.

**Tiny Types:**
> O compilador é seu aliado. Tipos específicos documentam a intenção e impedem inversões silenciosas.

**Imutabilidade:**
> Objetos que não mudam não surpreendem. Prefira `final`, sem setters, operações que retornam nova instância.

**Modelo Rico vs. Anêmico:**
> O problema não é ter DTOs, mas sim *só* ter DTOs. Lógica de negócio pertence ao domínio, não a classes de serviço procedurais.

---

## 🦠 Capítulo 9: Maus Cheiros de Design

> *"Todo bruxo aprende a identificar rastros de monstros. Todo desenvolvedor precisa aprender a identificar rastros de código ruim — antes que o monstro se manifeste em produção."*

Neste capítulo saímos dos princípios SOLID e aprendemos a **nomear e reconhecer más práticas**. Reconhecer um problema pelo nome é o primeiro passo para comunicá-lo e corrigi-lo.

Visitando a pasta `capitulo9_maus_cheiros`, exploramos 7 versões organizadas em três camadas:

### 📂 livro_original — Os Exemplos do Aniche
Código fiel ao livro com as classes originais (`NotaFiscal`, `Gerenciador`, `AliquotaDeImposto`).
Ponto de partida para ver o smell com as classes exatas descritas por Aniche.

### 📂 v1 — Refused Bequest
> *"Bequest" = herança/legado. "Refused" = recusado.*

Ocorre quando uma subclasse herda de uma classe pai mas **não quer** honrar parte dos métodos herdados.
* **Problema:** `EscolaDeAlquimiaComSmell extends EscolaDeGuerraBase` — lança `UnsupportedOperationException` em `treinarCombate()`, violando o LSP.
* **Solução:** Interface `Escola` com o mínimo comum. `EscolaDeGuerraReal` e `EscolaDeAlquimiaReal` implementam apenas o que faz sentido para cada uma.

### 📂 v2 — Feature Envy
Um método mais interessado nos dados de **outro objeto** do que nos da própria classe.
* **Problema:** `ProcessadorDeContratosComSmell.processarContrato()` só usa dados de `ContratoDeCaca` — o método está no lugar errado.
* **Solução:** `ContratoDeCaca.processar()` — o comportamento vive onde os dados vivem. O processador apenas delega.

### 📂 v3 — Intimidade Inapropriada
Uma classe que **conhece e manipula os detalhes internos** de outra, fazendo perguntas sobre o estado para tomar decisões que deveriam estar dentro do objeto interrogado.
* **Problema:** `GerenciadorDeMissoesComSmell` pergunta `missao.isEncerrada() && missao.getValor() > 5000` para então decidir marcar como urgente.
* **Solução:** `missao.encerrar()` encapsula a regra. *Tell, don't ask.*

### 📂 v4 — God Class
A classe que **faz tudo** — controla muitos objetos, acumula responsabilidades, cresce indefinidamente.
* **Problema:** `GuildaDoContienenteGodClass` cadastra caçadores, cria contratos, calcula imposto, processa pagamentos, envia notificações e gera relatórios.
* **Solução:** `RegistroDeCacadores`, `GestorDeContratos`, `CalculadorDeImpostoGuilda`, `TesouroDaGuilda`, `ServicoDeNotificacao` — cada um com uma responsabilidade. `GuildaCoordenadora` apenas orquestra.

### 📂 v5 — Divergent Changes
Uma classe que **muda por muitas razões diferentes**. O sintoma aparece no histórico de commits: a mesma classe alterada por motivos completamente distintos.
* **Problema:** `ProcessadorDeMissaoDivergente` calcula imposto, gera relatório, notifica e classifica monstros — quatro razões para mudar.
* **Solução:** `CalculadorDeImpostoMissao`, `GeradorDeRelatorioMissao`, `ClassificadorDeMonstro` — uma classe, uma razão para mudar.

### 📂 v6 — Shotgun Surgery
O inverso do Divergent Changes. Uma única mudança de negócio exige **editar muitos arquivos** ao mesmo tempo.
* **Problema:** Taxa da Guilda (`0.15`) hardcoded em `ProcessadorDeContrato`, `RelatorioDeContrato`, `AuditoriaDeContrato` e mais 5 lugares.
* **Solução:** `TaxaDaGuilda` centraliza o conceito. Mudar a taxa = alterar um único lugar.

### 📂 v7 — Mundo Real (A Auditoria de Triss Merigold)
O sistema legado da Guilda concentrava **todos os seis smells**. A versão refatorada distribui responsabilidades corretamente.

| Smell identificado | Solução aplicada |
| :--- | :--- |
| **God Class** | `ServicoDeRecompensa`, `RelatorioDeGuilda`, `TaxaDaGuilda` |
| **Refused Bequest** | `CacadorMago implements Cacador, Feiticeiro` (sem herdar de `CacadorDeGuerra`) |
| **Feature Envy** | `missao.concluir()`, `missao.descricaoCompleta()` |
| **Intimidade Inapropriada** | `missao.encerrar()` — Tell, don't ask |
| **Divergent Changes** | `RelatorioDeGuilda` separado da lógica de missão |
| **Shotgun Surgery** | `TaxaDaGuilda` centralizada |

### 🎯 Conceitos Chave do Capítulo

**Refused Bequest:**
> A subclasse não pode exigir mais nem entregar menos que o pai. Se ela recusa métodos herdados, a herança está errada.

**Feature Envy:**
> Se um método usa mais `outraClasse.getX()` do que `this.x`, ele provavelmente está no lugar errado.

**Intimidade Inapropriada:**
> Não pergunte ao objeto para depois tomar uma decisão por ele. Diga a ele o que fazer — *Tell, don't ask*.

**God Class:**
> Uma classe que depende de 30 outras é frágil. Qualquer dependência pode forçar mudanças nela.

**Divergent Changes vs. Shotgun Surgery:**
> Divergent Changes → 1 classe, N razões para mudar. Shotgun Surgery → 1 razão para mudar, N classes afetadas.

---

## 📊 Capítulo 10: Métricas de Código

> *"Todo bruxo sabe que um monstro de 3 metros é perigoso. Mas como você mede se o seu código é 'grande demais'? É para isso que existem as métricas."*

Neste capítulo, saímos dos princípios e das práticas para entrar no mundo das **heurísticas numéricas**. Métricas de código não dizem com certeza se algo está errado — elas funcionam como um **filtro**, apontando para onde olhar primeiro.

Visitando a pasta `capitulo10_metricas`, exploramos 5 versões:

### 📂 v1 — Complexidade Ciclomática (CC)
Mede o número de caminhos independentes em um método.
* **Fórmula:** CC = desvios (`if`, `for`, `while`) + 1.
* **Problema:** `EstrategiasDeCacaComAltaCC` tem CC = 12.
* **Solução:** Polimorfismo reduz o método `decidir()` para CC = 1.

| Faixa de CC | Avaliação |
| :--- | :--- |
| 1–3 | ✅ Excelente |
| 4–5 | 🟡 Aceitável |
| > 10 | 🔴 Refatora urgente |

### 📂 v2 — Tamanho de Métodos e Classes
Métricas como **NOA** (atributos), **NOM** (métodos), **NOP** (parâmetros) e **LOC** (linhas) ajudam a identificar classes gigantes.
* **Problema:** `RegistroDeGuildaGigante` viola todos os limites.
* **Solução:** Refatoração em classes menores e coesas.

### 📂 v3 — Coesão e a LCOM
**LCOM (Lack of Cohesion of Methods)** mede o quão relacionados são os métodos de uma classe.
* **LCOM = 0** → máxima coesão (todos os métodos usam os mesmos atributos).
* **LCOM = 1** → mínima coesão (grupos de métodos independentes → divida a classe!).

### 📂 v4 — Acoplamento Aferente (CA) e Eferente (CE)
* **CE (Eferente):** Quantas classes esta classe **DEPENDE**. Alto CE instável = frágil.
* **CA (Aferente):** Quantas classes **DEPENDEM** desta. Alto CA = estável.
* **Solução:** Depender de interfaces (estáveis) em vez de classes concretas (instáveis).

### 📂 v5 — Mundo Real (Sistema de Análise de Qualidade)
Um sistema que calcula todas as métricas para um projeto e gera um relatório de qualidade, classificando o sistema como ÓTIMO, BOM, ATENÇÃO ou CRÍTICO.

### 🎯 Regra de Ouro

| Métrica | Problema | Cap. anterior que ensina a solução |
| :--- | :--- | :--- |
| **CC alto** | Muitos caminhos → difícil de testar | Cap. 4 (OCP + polimorfismo) |
| **NOA alto** | Muitos atributos → God Class | Cap. 2 (SRP), Cap. 9 (Divergent Changes) |
| **LCOM alto** | Dois grupos independentes → dividir | Cap. 2 (SRP), Cap. 9 (Divergent Changes) |
| **CE alto instável** | Frágil → quebra com qualquer mudança | Cap. 3 (DIP) |
| **NOP alto** | Construtor monstruoso | Cap. 8 (Tiny Types, Builder) |

> *"As métricas são filtros, não oráculos. Elas não dizem com 100% de certeza que algo está errado — elas dizem onde olhar primeiro."* — Maurício Aniche

---

## 🔬 Capítulo 11: Exemplo Prático — MetricMiner

> *"Preste muita atenção a cada decisão tomada. Olhe cada classe e pense se ela está coesa, como ela está acoplada, se as abstrações são coesas e estáveis. O código, em particular, é o menos importante aqui."* — Maurício Aniche

Neste capítulo, Aniche para de descrever princípios e **mostra um sistema real** — o MetricMiner. Para este estudo, consolidamos todo o aprendizado em uma **arquitetura final e profissional** no pacote `witcherminer`.

### 🐺 O WitcherMiner: A Arquitetura Final
A versão Witcher espelha fielmente cada decisão do MetricMiner no contexto da Guilda do Continente:

| MetricMiner | WitcherMiner | Análogo a |
| :--- | :--- | :--- |
| `Study` | `EstudoDaGuilda` | Interface do pesquisador |
| `SCM` | `ArquivoDeContratos` | Fonte dos dados |
| `Commit` | `ContratoRegistrado` | Registro completo |
| `CommitVisitor` | `VisitanteDeContrato` | Regra de análise |
| `PersistenceMechanism` | `MecanismoDePersistencia` | Destino de saída |
| `ClassLevelMetricFactory` | `FabricaDeMetrica` | Cria métricas sem vazar estado |

### 🔑 Decisões de Design Discutidas por Aniche
* **Fábrica de Métricas:** Garante que cada análise use uma nova instância da métrica, evitando que o estado de uma contamine a outra.
* **Pontos de Extensão:** O sistema é aberto para extensão em 5 pontos (SCM, SCMRepository, Métricas, Estudos, Visitantes), todos seguindo DIP + OCP.
* **Separação de Pacotes:** A estrutura (`domain`, `ports`, `adapters`) é a Arquitetura Hexagonal em escala de pacote.

### 🧪 Testabilidade Como Consequência do Design
O design do MetricMiner é **trivialmente testável**:
* **Visitantes** testados com implementações fake (lambdas).
* **Fábricas** testadas verificando a criação de novas instâncias.
* **Integração** validada com um repositório fake em memória.

---

## 🏆 Capítulo 12: Conclusão — A Jornada Completa

> *"Fazer um bom projeto de classes orientado a objeto não é fácil. É muita coisa para pensar."* — Maurício Aniche

O último capítulo não tem código novo no livro. É uma reflexão. Uma chamada para a prática. Aqui, a versão Witcher o implementa como o culminar de toda a jornada: uma única classe, `BruxoDoContinente`, que sintetiza **todos os princípios** aprendidos, com cada decisão rastreada ao capítulo que a ensinou.

### 🗺️ O Mapa da Jornada: Anatomia de um Bruxo Lendário
A classe `BruxoDoContinente` é o nosso troféu final. Cada linha de código nela é uma lição aprendida.

| Capítulo | Princípio / Conceito | Aplicado em `BruxoDoContinente` |
| :--- | :--- | :--- |
| **1** | OO Mindset | Estado (`nome`, `localizacao`) + comportamento (`registrarMissao`) juntos |
| **2** | SRP | Uma responsabilidade: representar um caçador e seu histórico |
| **3** | DIP | Depende de `CalculadorDeTaxa` e `InformacaoDeContato` — interfaces |
| **4** | OCP | Nova taxa? Crie `TaxaEspecial`. Não toca na classe `BruxoDoContinente` |
| **5** | Encapsulamento | Lista de missões como `unmodifiableList`; estado interno protegido |
| **6** | LSP / Composição | "Tem um" `CalculadorDeTaxa` — não herda taxa de nenhuma superclasse |
| **7** | ISP | Implementa `Tributavel` — interface mínima com um único método |
| **8** | Consistência | Tiny Types, Construtor Rico, Imutabilidade, Null Object (`ContatoDesconhecido`) |
| **9** | Maus Cheiros | Ausência de God Class, Feature Envy, Shotgun Surgery, LCOM baixo |
| **10** | Métricas | CC ≤ 5, NOA = 7, LCOM baixo, CE ≤ 5 com interfaces estáveis |
| **11** | Framework | Plugável como `VisitanteDeContrato` no WitcherMiner |

### 🎯 A Mensagem Final
Três pilares que atravessam todos os 12 capítulos:
**1. Coesão** — Classes com responsabilidades bem definidas. Uma razão para mudar.
**2. Acoplamento** — Dependências pensadas e estáveis. Programe para interfaces.
**3. Encapsulamento** — Esconda o COMO. Exponha o QUÊ. Objetos que protegem seu estado.

---

## 📊 Resumo dos Princípios SOLID Abordados

| Sigla | Princípio | Capítulo | Aplicação |
| :---: | :--- | :---: | :--- |
| **S** | Single Responsibility | 2, 9 | Classes de regra separadas; God Class e Divergent Changes são o SRP violado |
| **O** | Open/Closed | 4 | Calculadora aberta para extensão (novas tabelas/fretes) sem modificação |
| **L** | Liskov Substitution | 2, 4, 6, 9 | Subclasses podem substituir a classe base sem quebrar o sistema; Refused Bequest é o LSP violado |
| **I** | Interface Segregation | 2, 7 | Interfaces magras (`Tributavel`, `DadosParaCalculo`) evitam acoplamento inútil |
| **D** | Dependency Inversion | 3, 4, 7 | Dependemos de abstrações (interfaces), não de implementações concretas |

> **Caps 8 e 9** não introduzem novos princípios SOLID — aprofundam boas práticas de design (consistência de objetos, imutabilidade, tiny types) e ensinam a nomear e reconhecer más práticas (code smells). São a aplicação prática de tudo que foi aprendido nos capítulos anteriores.

---

## 📁 Estrutura do Projeto

```
oo-solid-ninjas/
├── src/
│   ├── main/java/
│   │   ├── capitulo1_mindset/          # OO vs Procedural
│   │   ├── capitulo2_coesao/           # SRP e Strategy Pattern
│   │   ├── capitulo3_acoplamento/      # DIP e Arquitetura Hexagonal
│   │   ├── capitulo4_ocp/              # Open/Closed Principle
│   │   ├── capitulo5_encapsulamento/   # Encapsulamento e Tell Don't Ask
│   │   ├── capitulo6_heranca_composicao/ # LSP e Herança vs Composição
│   │   ├── capitulo7_interfaces_magras/ # ISP e Interfaces Coesas
│   │   ├── capitulo8_consistencia/     # Consistência de Objetos
│   │   ├── capitulo9_maus_cheiros/     # Maus Cheiros de Design
│   │   ├── capitulo10_metricas/        # Métricas de Código
│   │   ├── capitulo11_metricminer/     # Estudo de Caso: MetricMiner
│   │   │   └── witcherminer/           # Arquitetura final do projeto
│   │   ├── capitulo12_conclusao/       # Conclusão e Síntese
│   │   └── infra/                      # Utilitários (Console UTF-8)
│   └── test/java/
│       ├── capitulo2_coesao/           # Testes unitários do Cap. 2
│       ├── capitulo3_acoplamento/      # Testes unitários + ArchUnit
│       ├── capitulo4_ocp/              # Testes com Mocks e Integração
│       ├── capitulo5_encapsulamento/   # Testes de Encapsulamento
│       ├── capitulo6_heranca_composicao/ # Testes de Herança e LSP
│       ├── capitulo7_interfaces_magras/ # Testes de ISP
│       ├── capitulo8_consistencia/     # Testes de Consistência
│       ├── capitulo9_maus_cheiros/     # Testes de Maus Cheiros
│       ├── capitulo10_metricas/        # Testes de Métricas
│       └── capitulo11_metricminer/     # Testes do WitcherMiner
│           └── witcherminer/
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

# Capítulo 8 - Consistência de Objetos
./gradlew run --args="capitulo8_consistencia.SimuladorDeConsistencia"

# Capítulo 9 - Maus Cheiros de Design
./gradlew run --args="capitulo9_maus_cheiros.SimuladorDeMausCheiros"

# Capítulo 10 - Métricas de Código
./gradlew run --args="capitulo10_metricas.SimuladorDeMetricas"

# Capítulo 11 - Estudo de Caso: MetricMiner
./gradlew run --args="capitulo11_metricminer.SimuladorDoMetricMiner"

# Capítulo 12 - Conclusão
./gradlew run --args="capitulo12_conclusao.SimuladorFinal"
```

### Executar os Testes

```bash
# Todos os testes
./gradlew test

# Testes de um capítulo específico
./gradlew test --tests "capitulo11_metricminer.*"
./gradlew test --tests "capitulo12_conclusao.*"

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
