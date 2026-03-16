# ⚔️ Capítulo 8: Consistência, Objetinhos e Objetões

> *"Um objeto sem construtor rico é como um bruxo sem espada: está pronto no papel, mas não serve para nada na caçada."*

Neste capítulo, exploramos um conjunto de boas práticas relacionadas à **consistência dos objetos** — como garantir que objetos nasçam válidos, permaneçam válidos e se comuniquem de forma segura. Baseado no Capítulo 8 do livro de Maurício Aniche.

---

## 🗂️ Estrutura do Projeto

Este capítulo está organizado em **três camadas complementares**, que se leem em sequência:

```
src/
├── main/java/capitulo8_consistencia/
│   ├── livro_original/               ← Camada 1: exemplos fiéis ao livro
│   │   ├── Secao81_ConstrutoresRicos.java   (Pedido, Cliente, Item, Carro)
│   │   ├── Secao82_ValidandoDados.java      (CPF, CPFBuilder, ValidadorDeCliente, chain)
│   │   ├── Secao84_TinyTypes.java           (Telefone, Nome, Email, AlunoSemTinyTypes, AlunoComTinyTypes)
│   │   ├── Secao85_DTO.java                 (DadosDoUsuarioDTO)
│   │   └── Secao86_Imutabilidade.java       (EnderecoImutavel, PedidoComListaExposta)
│   │
│   ├── v1_construtor_rico/           ← Camada 2: versão Witcher — mesmo conceito, outro domínio
│   │   ├── Missao.java
│   │   ├── MissaoSemConstrutorRico.java
│   │   ├── NivelDePerigo.java
│   │   └── StatusDaMissao.java
│   ├── v2_validacao/
│   │   ├── CacadorParaRegistro.java  (+ NomeRequerido, EscolaRequerida, NivelMinimo, EmailValido, ValidadorComposto)
│   │   ├── CredencialDeBruxo.java
│   │   ├── CredencialBuilder.java
│   │   ├── ResultadoDeCriacao.java
│   │   ├── ValidacaoDeCacador.java
│   │   └── DemoV2.java               ← fachada pública para o Simulador
│   ├── v3_bom_vizinho/
│   │   ├── CacadorNullable.java      (+ CacadorReal, CacadorDesconhecido, ServicoDeContratoSeguro)
│   │   ├── ServicoDeContratoComNulos.java
│   │   └── DemoV3.java               ← fachada pública para o Simulador
│   ├── v4_tiny_types/
│   │   ├── CacadorSemTinyTypes.java
│   │   ├── CacadorComTinyTypes.java
│   │   ├── NomeDeGuerra.java
│   │   ├── EscolaDeCaca.java
│   │   ├── EmailDeCacador.java
│   │   └── NivelDeExperiencia.java
│   ├── v5_dto/
│   │   ├── ResumoDeMissaoDTO.java
│   │   └── MontadorDeResumo.java
│   ├── v6_imutabilidade/
│   │   ├── LocalizacaoMutavel.java
│   │   ├── LocalizacaoImutavel.java
│   │   └── RegistroDeRastro.java
│   ├── v7_classes_feias/
│   │   └── FabricaDeMissao.java
│   ├── v8_nomenclatura/
│   │   └── ExemplosDeNomenclatura.java
│   ├── v9_mundo_real/                ← Camada 3: caso de uso real integrando tudo
│   │   ├── Cacador.java              (+ NomeDeGuerra, EscolaDeGuerra, Credencial, UltimaLocalizacao, FichaDeCacadorDTO)
│   │   ├── CadastroDeGuilda.java
│   │   ├── ResultadoDeRegistro.java
│   │   └── DemoV9.java               ← fachada pública para o Simulador
│   │
│   ├── SimuladorDeConsistencia.java  ← Orquestra todos os cenários via fachadas Demo*
│   └── README.md
│
└── test/java/capitulo8_consistencia/
    ├── livro_original/
    │   └── LivroOriginalCap8Test.java
    ├── v1_construtor_rico/
    │   └── MissaoTest.java
    ├── v2_validacao/
    │   └── ValidacaoTest.java
    ├── v3_bom_vizinho/
    │   └── BomVizinhoTest.java
    ├── v4_tiny_types/
    │   └── TinyTypesTest.java
    ├── v6_imutabilidade/
    │   └── ImutabilidadeTest.java
    └── v9_mundo_real/
        └── CadastroDeGuildaTest.java
```

**Como usar:** leia o original do livro em `livro_original/` para entender o conceito com as classes do Aniche, depois veja a versão Witcher no `v{N}_*/` para a mesma ideia em outro domínio, e por fim o `v9_mundo_real/` mostra tudo integrado.

> **Padrão de visibilidade:** classes internas de cada pacote são intencionalmente *package-private* para preservar o encapsulamento. Cada pacote expõe uma única classe `Demo*` pública que serve de fachada para o `SimuladorDeConsistencia`.

| Seção do livro | Arquivo original | Versão Witcher |
| :--- | :--- | :--- |
| 8.1 Construtores Ricos | `Secao81_ConstrutoresRicos.java` | `v1_construtor_rico/Missao.java` |
| 8.2 Validando Dados | `Secao82_ValidandoDados.java` | `v2_validacao/` |
| 8.3 Bom Vizinho | *(conceitual — sem código no livro)* | `v3_bom_vizinho/` |
| 8.4 Tiny Types | `Secao84_TinyTypes.java` | `v4_tiny_types/` |
| 8.5 DTOs | `Secao85_DTO.java` | `v5_dto/` |
| 8.6 Imutabilidade | `Secao86_Imutabilidade.java` | `v6_imutabilidade/` |
| 8.7 Classes Feias | *(conceitual — sem código no livro)* | `v7_classes_feias/` |
| 8.8 Nomenclatura | *(conceitual — sem código no livro)* | `v8_nomenclatura/` |
| Integração | *(não há no livro)* | `v9_mundo_real/` |

---

## 🏗️ v1: Construtores Ricos

O construtor é o guardião do estado inicial do objeto. Se um objeto pode ser criado sem seus atributos essenciais, ele nasce em estado inválido — e só explode mais tarde.

### O Problema

```java
// ❌ Missão sem construtor rico — nasce sem monstro, sem cliente, sem recompensa
Missao festa = new MissaoSemConstrutorRico();
festa.adicionarRequisito("Poção de Swallow");
System.out.println(festa.gerarDescricao()); // 💥 NullPointerException
```

### A Solução

```java
// ✅ Impossível criar Missão sem os atributos essenciais
public Missao(String monstroAlvo, String clientePagador, double recompensa, NivelDePerigo nivel) {
    if (monstroAlvo == null || monstroAlvo.isBlank())
        throw new IllegalArgumentException("Missão precisa de um monstro alvo");
    if (recompensa <= 0)
        throw new IllegalArgumentException("Recompensa deve ser maior que zero");
    // ...
}

// Construtor adicional com valor padrão (NivelDePerigo.MEDIO)
public Missao(String monstroAlvo, String clientePagador, double recompensa) {
    this(monstroAlvo, clientePagador, recompensa, NivelDePerigo.MEDIO);
}
```

> **Regra de Ouro:** Se o objeto possui atributos sem os quais ele não pode viver, exija-os no construtor. Construtores são contratos — e contratos se cumprem desde o início.

---

## ✅ v2: Validando Dados

Onde colocar as validações? Aniche separa em dois tipos e oferece quatro abordagens.

### Dois Tipos de Validação

- **Validação de formato** (campo vazio, e-mail sem `@`, número inválido) → vai no **Controller/Adapter**, antes de chegar ao domínio.
- **Validação de negócio** (credencial com formato específico, nível mínimo exigido) → vai no **domínio**, mais próximo da entidade.

### Quatro Abordagens

**1. No próprio construtor** — mais simples, garante que o objeto nunca nasce inválido:
```java
public CredencialDeBruxo(String codigo) {
    if (!codigo.matches("[A-Z]\\d{2}-[A-Z]+"))
        throw new IllegalArgumentException("Credencial inválida: " + codigo);
    this.codigo = codigo;
}
```

**2. Builder com resultado rico** — separa a validação da classe de domínio e retorna erros descritivos:
```java
ResultadoDeCriacao resultado = new CredencialBuilder()
        .comLetra("G").comNumero("01").comEscola("LOBO").build();

if (!resultado.isSucesso()) {
    System.out.println(resultado.getErros()); // lista descritiva de erros
}
```

**3. Método `valida()`** — permite instanciar e validar depois (útil quando o objeto precisa existir antes da validação completa).

**4. Validador composto** — regras intercambiáveis via composição, usando padrão Chain of Responsibility:
```java
ValidacaoDeCacador validador = new ValidadorComposto(List.of(
        new NomeRequerido(),
        new EscolaRequerida(),
        new NivelMinimo(10),
        new EmailValido()
));

List<String> erros = validador.validar(cacador); // todos os erros de uma vez
```

---

## 🤝 v3: Teorema do Bom Vizinho e Null Object

> *"O que seria do mundo se ninguém nunca passasse nulo para sua classe?"*

### O Problema dos Nulos Espalhados

```java
// ❌ O serviço precisa checar null em todo lugar
public void processarMissao(String nome, CacadorNullable cacador) {
    String nomeCacador = (cacador != null) ? cacador.getNome() : "DESCONHECIDO";
    double bonus       = (cacador != null) ? cacador.calcularBonus() : 0.0;
    // ...
}
```

### Null Object Pattern

```java
// ✅ CacadorDesconhecido implementa a mesma interface com comportamento neutro
class CacadorDesconhecido implements CacadorNullable {
    @Override public String getNome()       { return "Caçador Anônimo"; }
    @Override public double calcularBonus() { return 0.0; }
    @Override public boolean isConhecido()  { return false; }
}

// ✅ Serviço limpo — zero null checks
public void processarMissao(String nome, CacadorNullable cacador) {
    System.out.printf("%s — %s (bônus: R$%.2f)%n",
            nome, cacador.getNome(), cacador.calcularBonus());
}

// O chamador é o bom vizinho — nunca passa null
servicoSeguro.processarMissao("Caça à Strige", new CacadorDesconhecido());
```

---

## 🏷️ v4: Tiny Types

Quando uma classe tem vários parâmetros do mesmo tipo primitivo, o compilador não ajuda se você trocar a ordem. Tiny types criam tipos específicos para cada conceito.

### O Problema

```java
// ❌ String, String, String, int — fácil de inverter sem perceber
new CacadorSemTinyTypes("Escola do Lobo", "Geralt", "geralt@lobo.com", 85);
//                       ↑ nome e escola invertidos — compila e ninguém percebe
```

### A Solução

```java
// ✅ Cada parâmetro tem seu tipo — inversão não compila
new CacadorComTinyTypes(
    new NomeDeGuerra("Geralt de Rívia"),   // não aceita EscolaDeCaca
    new EscolaDeCaca("Escola do Lobo"),    // não aceita NomeDeGuerra
    new EmailDeCacador("geralt@lobo.com"),
    new NivelDeExperiencia(85)             // garante 1–100 automaticamente
);
```

Cada tiny type também carrega sua própria validação:
```java
new NivelDeExperiencia(150); // ❌ IllegalArgumentException: Nível deve estar entre 1 e 100
new EmailDeCacador("semArroba"); // ❌ IllegalArgumentException: E-mail inválido
```

> **Trade-off:** Tiny types trazem segurança e semântica, mas aumentam o número de classes. Em contextos simples, uma `String` pode ser suficiente. Use com critério.

---

## 📦 v5: DTOs do Bem

DTOs são objetos de transferência de dados — sem comportamento, só atributos. Seu propósito é representar exatamente o que uma camada precisa receber ou exibir, sem forçar o domínio a carregar dados de apresentação.

```java
// ✅ DTO representa exatamente o que a tela de "Ficha de Missão" exibe
class ResumoDeMissaoDTO {
    private final String nomeCacador;
    private final String monstroAlvo;
    private final double recompensaLiquida;
    private final String nivelDeAmeaca;      // calculado
    private final List<String> requisitos;   // montado
    // ...
}
```

O `MontadorDeResumo` converte domínio → DTO em um único lugar. A view recebe o objeto pronto.

> **Aniche:** *"O problema não é ter DTOs, mas sim só ter DTOs."* Use quando a tela não mapeia diretamente para uma classe de domínio.

---

## 🧊 v6: Imutabilidade x Mutabilidade

Classes imutáveis não mudam após criação. Isso elimina uma categoria inteira de bugs: modificações inesperadas em objetos compartilhados.

### O Problema da Mutabilidade

```java
// ❌ Objeto mutável — original corrompido por acidente
LocalizacaoMutavel loc = new LocalizacaoMutavel("Novigrad", 52.0, 21.0);
loc.moverPara("Oxenfurt", 51.5, 21.5); // quem tinha referência a 'loc' não vê mais Novigrad
```

### A Solução Imutável

```java
// ✅ "Mudança" retorna nova instância — original intacta
final class LocalizacaoImutavel {
    private final String regiao;     // final!
    private final double latitude;   // final!
    private final double longitude;  // final!

    public LocalizacaoImutavel comNovaRegiao(String novaRegiao) {
        return new LocalizacaoImutavel(novaRegiao, this.latitude, this.longitude); // nova instância
    }
}

LocalizacaoImutavel kaerMorhen = new LocalizacaoImutavel("Kaer Morhen", 52.0, 21.0);
LocalizacaoImutavel novaBase   = kaerMorhen.comNovaRegiao("Novigrad"); // kaerMorhen intacta!
```

Listas internas também devem ser protegidas:
```java
// ✅ Lista retornada como imutável — ninguém pode adicionar por fora
public List<LocalizacaoImutavel> getAvistamentos() {
    return Collections.unmodifiableList(avistamentos);
}
```

> **No Java moderno:** `LocalDate`, `LocalDateTime`, `String` — todos imutáveis por design. Toda operação retorna nova instância.

---

## 🏚️ v7: Classes que São Feias por Natureza

Algumas classes **nasceram para ser feias**: Controllers, Factories, Adapters. Código procedural, cheio de ifs e conversões.

```java
// ❌ Feio? Sim. Errado? Não.
public Missao criar(String monstro, String cliente, String valorStr, String perigoStr) {
    if (monstro == null || monstro.isBlank()) throw new IllegalArgumentException(...);
    double valor;
    try { valor = Double.parseDouble(valorStr.replace(",", ".")); }
    catch (NumberFormatException e) { throw new IllegalArgumentException(...); }
    // ...
}
```

A moral: **mantenha o feio estável e nas pontas**. Controllers e Factories são alterados raramente. O domínio — onde as regras vivem — deve ser limpo, legível e testável.

---

## 📝 v8: Nomenclatura

Nomes são o principal instrumento de comunicação no código.

```java
// ❌ Curto demais — não diz nada
double calc(double x, double y) { return x * y * 0.15; }

// ❌ Longo demais — cansa
double calcularTaxaDaGuildaDoContineenteParaOBruxoComBaseNoValorTotal(double r) { ... }

// ✅ Preciso e legível
double calcularTaxaDaGuilda(double recompensa) { return recompensa * 0.15; }
```

Regras práticas: verbos para métodos (`calcular`, `buscar`, `validar`), substantivos para classes (`CalculadorDeTaxa`, `RepositorioDeContratos`), prefixos para booleanos (`isAtivo`, `podeConcluir`, `temContrato`), plural para coleções (`missoesDisponiveis`, `cacadoresAtivos`).

---

## 🏰 v9: Mundo Real — O Sistema de Registro do Continente

Aplicação integrada de todos os conceitos em um sistema de cadastro de caçadores.

**Arquitetura resultante:**

| Classe | Conceito aplicado |
| :--- | :--- |
| `Cacador` | Construtor rico + tiny types + lista imutável |
| `NomeDeGuerra`, `Credencial` | Tiny types com validação embutida |
| `UltimaLocalizacao` | Imutabilidade — mover retorna nova instância |
| `CadastroDeGuilda` | Bom vizinho — valida antes de criar |
| `FichaDeCacadorDTO` | DTO para exibição |
| `ResultadoDeRegistro` | Objeto de resultado rico |

---

## 🛡️ Resumo dos Princípios

| Conceito | Problema | Solução | Original do livro | Versão Witcher |
| :--- | :--- | :--- | :--- | :--- |
| **Construtor Rico** | Objeto nasce em estado inválido | Exigir atributos essenciais no construtor | `Pedido`, `Cliente`, `Carro` | `Missao`, `NivelDePerigo` |
| **Validação** | Onde e como validar dados? | Formato no controller; negócio no domínio via construtor/builder/validador | `CPF`, `CPFBuilder`, `ValidadorDeCliente`, chain | `CredencialDeBruxo`, `CredencialBuilder`, `ValidadorComposto` |
| **Bom Vizinho** | Null checks espalhados por todo lado | Null Object Pattern; nunca passar null | *(conceitual)* | `CacadorDesconhecido`, `ServicoDeContratoSeguro` |
| **Tiny Types** | Parâmetros trocados sem aviso do compilador | Tipos específicos para cada conceito | `Telefone`, `Nome`, `Email`, `AlunoComTinyTypes` | `NomeDeGuerra`, `EscolaDeCaca`, `NivelDeExperiencia` |
| **DTOs** | Camadas com representações incompatíveis | Objetos de transferência semânticos | `DadosDoUsuarioDTO` | `ResumoDeMissaoDTO`, `MontadorDeResumo` |
| **Imutabilidade** | Estado alterado por acidente | `final`, sem setters, operações retornam nova instância | `EnderecoImutavel`, `PedidoComListaExposta` | `LocalizacaoImutavel`, `RegistroDeRastro` |
| **Classes Feias** | Pressão para limpar código feio por natureza | Aceitar a feiura onde ela é necessária; isolar nas pontas | *(conceitual)* | `FabricaDeMissao` |
| **Nomenclatura** | Código incompreensível | Nomes que revelam intenção — verbos, substantivos, prefixos | *(conceitual)* | `ExemplosDeNomenclatura` |

> *"Garanta consistência dos seus objetos. Um objeto inválido não é um objeto — é uma armadilha esperando ser acionada."*
