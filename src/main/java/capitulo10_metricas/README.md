# 📊 Capítulo 10: Métricas de Código

> *"Todo bruxo sabe que um monstro de 3 metros é perigoso. Mas como você mede se o seu código é 'grande demais'? É para isso que existem as métricas."*

Neste capítulo, saímos dos princípios e das práticas para entrar no mundo das **heurísticas numéricas**. Métricas de código não dizem com certeza se algo está errado — elas funcionam como um **filtro**, apontando para onde olhar primeiro. Baseado no Capítulo 10 do livro de Maurício Aniche.

---

## 🗂️ Estrutura do Projeto

```
capitulo10_metricas/
├── livro_original/                  ← Exemplos fiéis ao livro
│   ├── Secao101_ComplexidadeCiclomatica.java
│   ├── Secao102_105_Metricas.java
│   └── DemoLivroOriginal.java
├── v1_complexidade_ciclomatica/     ← CC: caminhos de execução
│   ├── ComplexidadeCiclomatica.java
│   └── DemoV1.java
├── v2_tamanho/                      ← NOA, NOM, NOP, LOC
│   ├── TamanhoDeClasse.java
│   └── DemoV2.java
├── v3_lcom/                         ← Coesão: LCOM HS
│   ├── LCOM.java
│   └── DemoV3.java
├── v4_acoplamento/                  ← CA (aferente) e CE (eferente)
│   ├── AcoplamentoAferenteEferente.java
│   └── DemoV4.java
└── v5_mundo_real/                   ← Sistema de Análise de Qualidade
    ├── SistemaDeAnaliseDeQualidade.java
    └── DemoV5.java
```

---

## 📐 10.1 — Complexidade Ciclomática (CC)

Aniche, p.128:
> *"Quanto mais controladores de fluxo em um código, como ifs, fors e whiles, mais caminhos esse código terá e, por consequência, se tornará mais complicado de ser entendido."*

**Fórmula:** CC = número de instruções de desvio + 1

```java
// CC = 3 (2 ifs + 1)
public int conta(int a, int b) {
    int total = 0;
    if (a > 10) total += a + b;     // +1
    if (b > 20) total += a * 2 + b; // +1
    return total;
}
```

**Versão Witcher:** `EstrategiasDeCacaComAltaCC` tem CC = 12. A solução com polimorfismo reduz o método `decidir()` para CC = 1 — cada `TipoMonstro` encapsula sua própria estratégia.

| Faixa de CC | Avaliação |
| :--- | :--- |
| 1–3 | ✅ Excelente |
| 4–5 | 🟡 Aceitável |
| 6–10 | 🟠 Atenção |
| > 10 | 🔴 Refatora urgente |

---

## 📏 10.2 — Tamanho de Métodos e Classes

Aniche, p.129:
> *"Uma classe com 80 métodos provavelmente é difícil de ser mantida e faz coisa demais."*

Métricas monitoradas:

| Sigla | O que mede | Sinal de alerta |
| :--- | :--- | :--- |
| **NOA** | Number of Attributes | > 7 → possível God Class |
| **NOM** | Number of Methods | > 20 → responsabilidades demais |
| **NOP** | Number of Parameters | > 5 → use Tiny Types ou Builder |
| **LOC** | Lines of Code | > 15 por método → Extract Method |

**Versão Witcher:** `RegistroDeGuildaGigante` viola todos (NOA=9, NOP=9 no construtor, MLOC≈28). Após refatoração: `IdentidadeDeCacador` (NOA=3), `CarteiraDeRecompensas` (NOA=2), `ProcessadorDeMissao` (NOP≤2).

---

## 🧲 10.3 — Coesão e a LCOM

Aniche, p.130:
> *"É isso que a métrica conhecida por LCOM (Lack of Cohesion of Methods) contabiliza. Quanto maior esse número, menos coesa a classe é."*

**LCOM HS** = 1 - (sum(MF) / M × F)

- **LCOM = 0** → máxima coesão (todos os métodos acessam todos os atributos)
- **LCOM = 1** → mínima coesão (cada método acessa apenas seus próprios atributos → divida!)

**Versão Witcher:** `GuildaComDuasResponsabilidades` tem LCOM ≈ 1 — dois grupos completamente independentes. Dividida em `FinanceiroDeGuilda` (LCOM ≈ 0) e `ReputacaoDeCacador` (LCOM ≈ 0).

> **Aniche sobre LCOM (p.131):** *"Pense nessas métricas como um filtro. Você não consegue olhar os 50 mil métodos que existem em seu código, mas consegue olhar um a um os 100 que ela filtrar."*

---

## 🔗 10.4 — Acoplamento Aferente (CA) e Eferente (CE)

Aniche, p.132-133:

- **CE (Eferente)** — quantas classes esta classe **DEPENDE**. Quanto maior, mais frágil.
- **CA (Aferente)** — quantas classes **DEPENDEM** desta. Quanto maior, mais estável.

**Exemplo do livro:** Interface `List<T>` do Java.
- CA = muito alto (todo mundo usa `List`)
- CE = zero (`List` não depende de ninguém)
- → Estável por design

**Versão Witcher:** `GerenciadorCentralComAltoCE` tem CE = 5 dependências **instáveis** (classes concretas). Após aplicar DIP: `GerenciadorLimpo` tem CE = 5 dependências **estáveis** (interfaces) — mesmo número, mas qualidade radicalmente diferente.

> A diferença não está em **quantas** dependências, mas em **quão estáveis** elas são.

---

## 🏷️ 10.5 — Má Nomenclatura

Aniche, p.133:
> *"Variáveis cujo nome contenha muitos caracteres são complicadas de serem lidas. O mesmo para variáveis de uma única letra."*

```java
// ❌ Péssimo
double c(double x, double y) { return x * y * 0.15; }

// ❌ Longo demais (cansa antes de entender)
double calcularTaxaDaGuildaDoContineenteParaOBruxoComBaseNoValorTotalDaRecompensa(double v) { ... }

// ✅ Preciso e legível
double calcularTaxaDaGuilda(double recompensa) { return recompensa * 0.15; }
```

---

## 📊 10.6 — Como Avaliar os Números?

Aniche, p.134:
> *"Você pode criar seu número mágico [...] você não está comparando com um número ideal geral, mas sim com aquilo que você já está acostumado a manter."*

**Abordagens:**
1. **Número mágico da literatura** — valores publicados em pesquisas
2. **Número do projeto** — baseado em estatísticas (quartis) do seu próprio codebase
3. **Análise sistêmica** — *"90% das classes dentro dos limites = sistema ótimo"*

**Versão Witcher:** `RelatorioDeQualidade` calcula o percentual de classes em conformidade e classifica o sistema como ÓTIMO, BOM, ATENÇÃO ou CRÍTICO.

---

## 🔧 10.7 — Ferramentas

Aniche menciona:
- **SonarQube** — plugin de CI/CD, calcula todas as métricas e exibe evolução temporal
- **Eclipse Metrics** — plugin local para visualizações pontuais
- **JDepend / NDepend** — análise de pacotes e dependências
- **JavaNCSS** — contagem de linhas e complexidade

> *"Lembre-se que a ferramenta não importa, mas sim como você analisará os dados gerados por ela."*

---

## 🎯 Regra de Ouro

| Métrica | Problema | Cap. anterior que ensina a solução |
| :--- | :--- | :--- |
| **CC alto** | Muitos caminhos → difícil de testar | Cap. 4 (OCP + polimorfismo) |
| **NOA alto** | Muitos atributos → God Class | Cap. 2 (SRP), Cap. 9 (Divergent Changes) |
| **LCOM alto** | Dois grupos independentes → dividir | Cap. 2 (SRP), Cap. 9 (Divergent Changes) |
| **CE alto instável** | Frágil → quebra com qualquer mudança | Cap. 3 (DIP) |
| **NOP alto** | Construtor monstruoso | Cap. 8 (Tiny Types, Builder) |

> *"As métricas são filtros, não oráculos. Elas não dizem com 100% de certeza que algo está errado — elas dizem onde olhar primeiro."* — Maurício Aniche