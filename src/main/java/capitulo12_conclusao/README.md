# 🏆 Capítulo 12: A Lenda do Lobo Branco — A Jornada Completa

> *"Fazer um bom projeto de classes orientado a objeto não é fácil. É muita coisa para pensar."* — Maurício Aniche

O último capítulo não tem código novo no livro. É uma reflexão. Uma chamada para a prática. Aqui, a versão Witcher o implementa como o culminar de toda a jornada: uma única classe, `BruxoDoContinente`, que sintetiza **todos os princípios** aprendidos, com cada decisão rastreada ao capítulo que a ensinou.

---

## 🗺️ O Mapa da Jornada: Anatomia de um Bruxo Lendário

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

---

## 🐺 `BruxoDoContinente` — Síntese em Código

```java
// Cap. 3 (DIP) + Cap. 6 (Composição): recebe interfaces no construtor
public final class BruxoDoContinente implements Tributavel { // Cap. 8 (Imutabilidade parcial)
    // Cap. 8 (Tiny Types): tipos específicos, não String nua
    private final NomeDeBruxo         nome;
    private final EscolaDeBruxo       escola;
    // Cap. 6 (Composição): taxa é plugável via Strategy (Cap. 4 OCP)
    private final CalculadorDeTaxa    calculadorDeTaxa;
    // Cap. 8 (Null Object): nunca null
    private final InformacaoDeContato contato;
    // Cap. 8 (Imutabilidade): "mudança" retorna nova instância
    private LocalizacaoAtual          localizacao;
    // Cap. 5 (Encapsulamento): lista protegida externamente
    private final List<String>        missoesConcluidas = new ArrayList<>();

    // Cap. 8 (Construtor Rico): atributos essenciais exigidos na criação
    public BruxoDoContinente(NomeDeBruxo nome, EscolaDeBruxo escola,
                             CalculadorDeTaxa calculadorDeTaxa,
                             InformacaoDeContato contato,
                             LocalizacaoAtual localizacaoInicial) { ... }

    // Cap. 7 (ISP): implementação de interface mínima
    @Override
    public double calcularImposto() {
        return calculadorDeTaxa.calcular(recompensaAcumulada); // Cap. 3 (DIP)
    }

    // Cap. 5 (Lista imutável)
    public List<String> getMissoesConcluidas() {
        return Collections.unmodifiableList(missoesConcluidas);
    }
}

// Cap. 8 (Optional em vez de null)
public Optional<BruxoDoContinente> buscarPorNome(String nome) { ... }
```

---

## 🧪 A Prova Final: Testabilidade

Até mesmo esta classe final e complexa é **trivialmente testável**, graças ao design sólido.
*   **Injeção de Dependência:** Podemos passar `Mocks` para `CalculadorDeTaxa` e `InformacaoDeContato`.
*   **Tiny Types:** Não precisamos testar se o e-mail é válido aqui; o tipo `EmailDeContato` já garante isso.
*   **Imutabilidade:** Testar o método `moverPara()` é fácil: basta verificar se a nova instância tem a localização correta, sabendo que a original não foi alterada.

---

## 📚 Os Tomos Antigos (Leituras Recomendadas por Aniche)

| Livro | Autor | O que você vai ganhar |
| :--- | :--- | :--- |
| **Agile Principles, Patterns and Practices** | Robert Martin | SOLID com profundidade extrema — o livro definitivo |
| **Growing OO Software, Guided by Tests** | Freeman & Pryce | TDD como termômetro de design — o livro que mudou como vemos testes |
| **Design Patterns** | Gang of Four | Um catálogo imenso de OO bem aplicada — padrões que transformam |
| **Refactoring to Patterns** | Joshua Kerievsky | De código procedural para OO real — a jornada do legado |

---

## 🎯 A Mensagem Final do Mestre

Aniche, p.147:

> *"Fazer tudo isso requer prática e experiência. Lembre-se também que você não criará um bom projeto de classes de primeira; valide, aprenda e melhore o seu projeto de classes atual o tempo todo."*

Três pilares que atravessam todos os 12 capítulos:

**1. Coesão** — Classes com responsabilidades bem definidas. Uma razão para mudar.

**2. Acoplamento** — Dependências pensadas e estáveis. Programe para interfaces.

**3. Encapsulamento** — Esconda o COMO. Exponha o QUÊ. Objetos que protegem seu estado.

---

> *"Vá, programe, e que seu código seja limpo como a lâmina de Geralt."*
