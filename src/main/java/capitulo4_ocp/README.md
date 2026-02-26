# 🔓 Capítulo 4: Classes Abertas e o Tal do OCP

> *"Um Bruxo não muda o feitiço toda vez que encontra um novo monstro. Ele aprende novos feitiços. O livro de feitiços está aberto para extensão, mas cada feitiço já escrito está fechado para modificação."*

Chegamos ao **OCP — Open/Closed Principle**, o segundo dos princípios SOLID. Este capítulo constrói em cima de tudo que aprendemos sobre coesão, acoplamento e DIP.

---

## 🧩 O Contexto

Até agora, aprendemos:
- **Cap. 2 (SRP):** Uma classe deve ter uma única razão para mudar.
- **Cap. 3 (DIP):** Programe para abstrações, não para implementações.

Agora damos um passo além: **como fazer o sistema evoluir sem precisar mexer no que já funciona?**

---

## 📂 Estrutura do Capítulo

### v1 — O Problema Original (A Calculadora Rígida)

A `CalculadoraDePrecos` começa inocente: apenas uma tabela de preços e um frete.

```java
public class CalculadoraDePrecos {
    public double calcula(Compra produto) {
        TabelaDePrecoPadrao tabela = new TabelaDePrecoPadrao(); // 🚨
        Frete correios = new Frete();                           // 🚨
        // ...
    }
}
```

O problema? **Ela instancia suas dependências diretamente** (`new TabelaDePrecoPadrao()`).

**Analogia Witcher:** É como um Bruxo que só sabe usar a Espada de Prata. Quando aparecer um monstro humano, ele está sem saída.

### v2 — O Problema dos IFs (A Solução Errada)

Quando o sistema precisa de múltiplas regras, o desenvolvedor ingênuo adiciona `ifs`.
Isso cria uma **God Class disfarçada** — classe que sabe de tudo, mas não faz nada bem.

**Analogia Witcher:** É como adicionar um `if` no Geralt: "Se for um humano, usa aço. Se for um morto-vivo, usa prata. Se for um Grifo, usa flechas..." O livro de feitiços vira um manual enciclopédico impossível de manter.

### v3 — A Calculadora Aberta (A Solução Correta — OCP)

A solução é criar **abstrações** (`TabelaDePreco`, `ServicoDeEntrega`) e injetar pelo construtor.

```java
// As abstrações
public interface TabelaDePreco { double descontoPara(double valor); }
public interface ServicoDeEntrega { double para(String cidade); }

// A calculadora agora está ABERTA para extensão
public class CalculadoraDePrecos {
    public CalculadoraDePrecos(TabelaDePreco tabela, ServicoDeEntrega entrega) { ... }
    public double calcula(Compra produto) {
        // Zero IFs. Zero instanciações. Polimorfismo puro.
        double desconto = tabela.descontoPara(produto.getValor());
        double frete = entrega.para(produto.getCidade());
        return produto.getValor() * (1 - desconto) + frete;
    }
}
```

A calculadora agora está **aberta para extensão** (novas tabelas e fretes) e **fechada para modificação** (seu código não muda).

**Analogia Witcher:** O Geralt não muda — apenas recebe uma `EspadaDiferente` ou um `FeitiçoNovo`. Ele é o mesmo Bruxo, mas pode lidar com qualquer monstro.

### v4 — Exemplo Real (O Sistema de Exercícios da Caelum)

Código real extraído da plataforma de ensino da Caelum.
`ShowAnswerHelper` com `ifs` para cada tipo de exercício → refatorado com `ExerciseViewDetails`.

---

## ⚔️ Crônicas do Concílio: A Busca pela Elegância (V5 e V6)

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

---

## 📜 O Princípio do Aberto-Fechado (OCP)

> *"Seu sistema deve evoluir por meio de novos códigos, não de alterações em códigos já existentes."* — Maurício Aniche

| Propriedade | Significado |
| :--- | :--- |
| **Aberta para Extensão** | Fácil adicionar novos comportamentos |
| **Fechada para Modificação** | O código existente não precisa mudar |

### Como Conseguir Isso?

1. **Identifique o que muda** (a variação: tabela de preços, regra de frete)
2. **Crie uma abstração** para essa variação (interface)
3. **Injete pelo construtor** (não instancie dentro da classe)

### Por que Construtor e Não Setter?

> "Construtores obrigam os clientes a passarem as dependências no momento da criação. Setters não — o programador pode esquecer. O compilador fará o trabalho de avisá-lo."


---

## 🧪 Testabilidade: O Termômetro do Design

O OCP tem um bônus poderoso: **classes abertas são facilmente testáveis**.

Se a classe recebe suas dependências pelo construtor, podemos passar **mocks** nos testes.

```java
// ✅ Com OCP: passamos mocks pelo construtor
@Mock TabelaDePreco simuladorDeTabela;
@Mock ServicoDeEntrega simuladorDeEntrega;

CalculadoraDePrecos calculadora = new CalculadoraDePrecos(
    simuladorDeTabela,
    simuladorDeEntrega
);
when(simuladorDeTabela.descontoPara(50)).thenReturn(0.10);
when(simuladorDeEntrega.para("SP")).thenReturn(10.0);
```
Sem OCP, somos forçados a testar a Calculadora junto com todas as suas dependências. Se o teste falhar, não sabemos quem foi o culpado.


> *"Se está difícil de testar, é porque seu código pode ser melhorado."* — Maurício Aniche

---

## ⚖️ IFs Nunca Mais? Abstrações Sempre?

O livro faz uma ressalva importante:

> "A resposta, talvez para a sua surpresa, é: talvez não. [...] Códigos muito flexíveis, cheios de abstrações e prontos para acomodar qualquer mudança têm um custo agregado: eles são mais complexos. É o que chamamos de complexidade acidental. [...] Seja parcimonioso. Flexibilize apenas as partes do seu sistema que realmente precisam disso."

**Regra prática:** Se uma variação já ocorreu mais de uma vez, ou se é previsível que ocorra, crie a abstração. Caso contrário, o IF pode ser mais simples.

---

## 🧠 Ensinando Abstrações desde a Base

O capítulo termina com uma reflexão filosófica poderosa:

> "Quando dou aula e falo 'gato, cachorro e pássaro', espero que o aluno me responda 'animal'. Quando falo 'ISS, INXPTO e outro-imposto', espero 'imposto'. Isso é programar orientado a objetos. É pensar primeiro na abstração, e depois, na implementação."

No mundo procedural, a preocupação maior é com a implementação ("como fazer").
No mundo OO, a preocupação maior deve ser com a abstração ("o que fazer").

---

## 🛡️ Resumo dos Princípios Abordados

| Sigla | Princípio | Aplicação neste Capítulo |
| :--- | :--- | :--- |
| **OCP** | Open/Closed | `CalculadoraDePrecos` não muda quando surgem novas tabelas/fretes |
| **DIP** | Dependency Inversion | Dependemos de `TabelaDePreco` (interface) e não de `TabelaDePrecoPadrao` (concreto) |
| **SRP** | Single Responsibility | Cada regra de desconto e frete vive em sua própria classe |
| **LSP** | Liskov Substitution | Qualquer `TabelaDePreco` pode substituir outra sem quebrar a Calculadora |

---

## 🏰 A Visão do Mestre

O OCP é a garantia de que seu sistema tem **saúde arquitetural a longo prazo**.

Um sistema sem OCP envelhece mal: cada nova feature exige um `CTRL+F` desesperado em todo o código para encontrar todos os pontos que precisam mudar. O desenvolvedor nunca tem certeza se encontrou todos.

Um sistema com OCP envelhece bem: novas features são implementadas como **novas classes**. O código existente não é tocado, portanto não quebra. Os testes passam. O sistema cresce sem acumular dívida técnica.

> *"A diferença entre um sistema que evolui e um que apodrece está nas abstrações pensadas desde o início."*
