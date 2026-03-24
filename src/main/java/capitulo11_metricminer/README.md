# 🔬 Capítulo 11: Exemplo Prático — MetricMiner

> *"Preste muita atenção a cada decisão tomada. Olhe cada classe e pense se ela está coesa, como ela está acoplada, se as abstrações são coesas e estáveis. O código, em particular, é o menos importante aqui."* — Maurício Aniche

Neste capítulo, Aniche para de descrever princípios e **mostra um sistema real** — o MetricMiner, uma ferramenta de pesquisa de engenharia de software desenvolvida na USP. Cada decisão de design é discutida à luz de tudo que foi aprendido. Baseado no Capítulo 11 do livro de Maurício Aniche.

---

## 🐺 O WitcherMiner: A Arquitetura Final

Para este capítulo, em vez de criar versões incrementais, consolidamos todo o aprendizado em uma **arquitetura final e profissional** no pacote `witcherminer`.

Essa arquitetura espelha fielmente o design do MetricMiner, mas no contexto da Guilda do Continente.

### 🗂️ Estrutura do Projeto (Clean Architecture + Hexagonal)

```
witcherminer/
├── domain/               # Entidades de Domínio puras (ContratoRegistrado, AcaoDoCacador)
├── ports/                # Interfaces (Portas do Hexágono)
│   ├── ArquivoDeContratos.java
│   ├── EstudoDaGuilda.java
│   ├── FabricaDeMetrica.java
│   └── VisitanteDeContrato.java
├── core/                 # Orquestração / Use Cases (Navegador, CalculadoraDeMetricas)
├── adapters/             # Implementações Concretas (ArquivoHistoricoGuildaFake, CSVGuilda)
├── visitantes/           # Implementações de VisitanteDeContrato
└── metricas/             # Implementações de MetricaDeCacador e suas fábricas
```

### ⚔️ Analogia WitcherMiner vs. MetricMiner

| MetricMiner | WitcherMiner | Análogo a |
| :--- | :--- | :--- |
| `Study` | `EstudoDaGuilda` | Interface do pesquisador |
| `SCM` | `ArquivoDeContratos` | Fonte dos dados |
| `SCMRepository` | `RegistroHistoricoGuilda` | Classe de domínio |
| `ChangeSet` | `ReferenciaDeContrato` | Referência por ID |
| `Commit` | `ContratoRegistrado` | Registro completo |
| `Modification` | `AcaoDoCacador` | Ação dentro do contrato |
| `CommitVisitor` | `VisitanteDeContrato` | Regra de análise |
| `PersistenceMechanism` | `MecanismoDePersistencia` | Destino de saída |
| `CSVFile` | `CSVGuilda` | Arquivo CSV |
| `ClassLevelMetricFactory` | `FabricaDeMetrica` | Cria métricas sem vazar estado |
| `ClassLevelMetric` | `MetricaDeCacador` | Calcula um número |
| `SourceCodeRepositoryNavigator` | `NavegadorDeHistoricoGuilda` | Fluent API |

---

## 🔑 Decisões de Design Discutidas por Aniche

### 1. A Fábrica de Métricas (p.143)

> *"A fábrica também é necessária por uma outra razão. Métricas de código geralmente são implementadas por meio de navegações em árvores abstratas [...] e muitas dessas navegações guardam estados em seus objetos. [...] precisamos de novas instâncias da métrica o tempo inteiro."*

```java
// ✅ Nova instância por arquivo — estado não vaza entre análises
.process(new CalculadoraDeMetricasDeCacador(new FabricaTaxaDeSucesso()),
         new CSVGuilda("taxa-sucesso.csv"))
```

**Versão Witcher:** O `CalculadoraDeMetricasDeCacador` recebe uma fábrica e chama `fabrica.construir()` a cada contrato, garantindo que o estado de uma análise não contamine a próxima.

### 2. Pontos de Extensão (p.144)

Aniche lista os 5 pontos de flexibilização do MetricMiner:

1. `SCM` — suporte a novo gerenciador (Git, SVN, Mercurial...)
2. `SCMRepository` + factory methods — cria repositório específico
3. `ClassLevelMetricFactory` + `ClassLevelMetric` — nova métrica de código
4. `Study` — novo estudo do pesquisador
5. `CommitVisitor` — novo visitante analítico

Todos seguem **DIP + OCP** — o Navigator nunca precisa mudar quando um novo ponto é adicionado.

### 3. Separação de Pacotes (p.144)

```
br.com.metricminer2          → executável do framework
br.com.metricminer2.domain   → Commit, Modification (domínio puro)
br.com.metricminer2.scm      → SCM, CommitVisitor (contratos)
br.com.metricminer2.persistence → mecanismos de saída
br.com.metricminer2.metric   → métricas de código
```

Essa separação é a Arquitetura Hexagonal (Cap. 3) em escala de pacote, exatamente como implementamos no `witcherminer`.

---

## 🧪 Testabilidade Como Consequência do Design

O design do MetricMiner é **trivialmente testável**:

```java
// Visitante testado com implementação fake (lambda)
MecanismoDePersistencia saida = linha -> capturado.add(linha);
visitor.process(repo, commit, saida);

// Fábrica de métrica testada verificando nova instância por chamada
assertNotSame(fabrica.construir(), fabrica.construir());
```

Sem banco de dados, sem arquivos reais, sem Git — apenas interfaces e lambdas.

---

## 🛡️ Princípios SOLID em Ação

| Sigla | Onde aparece no MetricMiner |
| :--- | :--- |
| **SRP** | Cada interface tem uma responsabilidade (SCM acessa, CommitVisitor analisa, PersistenceMechanism salva) |
| **OCP** | Novos visitantes e métricas sem tocar o Navigator |
| **LSP** | Qualquer `SCM` pode ser passado (Git, SVN, Fake) sem quebrar o Navigator |
| **ISP** | `Study`, `CommitVisitor`, `PersistenceMechanism` — interfaces mínimas e focadas |
| **DIP** | Navigator depende de interfaces, nunca de implementações concretas |

> *"Repare que todos eles possuem subpacotes, mas aqui podemos tratá-los em alto nível [...]."* — Maurício Aniche (p.144)
