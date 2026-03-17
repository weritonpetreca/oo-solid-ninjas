# ⚔️ Capítulo 9: Maus Cheiros de Design

> *"Todo bruxo aprende a identificar rastros de monstros. Todo desenvolvedor precisa aprender a identificar rastros de código ruim — antes que o monstro se manifeste em produção."*

Neste capítulo, damos nomes às más práticas. Reconhecer um problema pelo nome é o primeiro passo para comunicá-lo e corrigi-lo. Baseado no Capítulo 9 do livro de Maurício Aniche.

---

## 🗂️ Estrutura do Projeto

Este capítulo está organizado em **três camadas complementares**, que se leem em sequência:

```
src/
├── main/java/capitulo9_maus_cheiros/
│   ├── livro_original/                        ← Camada 1: exemplos fiéis ao livro
│   │   ├── Secao91_RefusedBequest.java               (Matematica, NotaFiscalComSmell, NotaFiscalCorreta)
│   │   ├── Secao92_FeatureEnvy.java                  (NotaFiscal, Gerenciador, NotaFiscalComportamental)
│   │   ├── Secao93_IntimidadeInapropriada.java        (NotaFiscalIntimidade, ProcessadorComIntimidade)
│   │   ├── Secao94_95_96_GodClass_Divergent_Shotgun.java
│   │   └── DemoLivroOriginal.java                    ← fachada pública para o Simulador
│   │
│   ├── v1_refused_bequest/                    ← Camada 2: versão Witcher — mesmo smell, outro domínio
│   │   ├── RefusedBequest.java                       (EscolaDeGuerraBase, EscolaDeAlquimiaComSmell, EscolaDeGuerraReal, EscolaDeAlquimiaReal)
│   │   └── DemoV1.java                               ← fachada pública para o Simulador
│   ├── v2_feature_envy/
│   │   ├── FeatureEnvy.java                          (ContratoDeCacaComSmell, ProcessadorDeContratosComSmell, ContratoDeCaca, ProcessadorDeContratosLimpo)
│   │   └── DemoV2.java
│   ├── v3_intimidade_inapropriada/
│   │   ├── IntimidadeInapropriada.java                (MissaoComSmell, GerenciadorDeMissoesComSmell, Missao, GerenciadorDeMissoesLimpo)
│   │   └── DemoV3.java
│   ├── v4_god_class/
│   │   ├── GodClass.java                             (GuildaDoContienenteGodClass, RegistroDeCacadores, GestorDeContratos, GuildaCoordenadora)
│   │   └── DemoV4.java
│   ├── v5_divergent_changes/
│   │   ├── DivergentChanges.java                     (ProcessadorDeMissaoDivergente, CalculadorDeImpostoMissao, ClassificadorDeMonstro, GeradorDeRelatorioMissao)
│   │   └── DemoV5.java
│   ├── v6_shotgun_surgery/
│   │   ├── ShotgunSurgery.java                       (ProcessadorDeContratoComShotgun, TaxaDaGuilda, ProcessadorDeContratoLimpo, AuditoriaDeContratoLimpo)
│   │   └── DemoV6.java
│   ├── v7_mundo_real/                         ← Camada 3: todos os smells + refatoração integrada
│   │   ├── GuildaRefatorada.java                     (Missao, CacadorDeGuerra, CacadorMago, TaxaDaGuilda, ServicoDeRecompensa, GuildaRefatorada)
│   │   └── DemoV7.java                               ← fachada pública para o Simulador
│   │
│   ├── SimuladorDeMausCheiros.java            ← Orquestra todos os cenários via fachadas Demo*
│   └── README.md
│
└── test/java/capitulo9_maus_cheiros/
    ├── livro_original/
    │   └── LivroOriginalCap9Test.java
    ├── v1_refused_bequest/
    │   └── RefusedBequestTest.java
    ├── v2_feature_envy/
    │   └── FeatureEnvyTest.java
    ├── v3_intimidade_inapropriada/
    │   └── IntimidadeInapropriadaTest.java
    ├── v4_god_class/
    │   └── GodClassTest.java
    ├── v5_divergent_changes/
    │   └── DivergentChangesTest.java
    ├── v6_shotgun_surgery/
    │   └── ShotgunSurgeryTest.java
    └── v7_mundo_real/
        └── GuildaRefatoradaTest.java
```

**Como usar:** leia o original do livro em `livro_original/` para ver o smell com as classes exatas do Aniche, depois veja a versão Witcher no `v{N}_*/` para o mesmo padrão em outro domínio, e por fim o `v7_mundo_real/` mostra todos os smells concentrados num sistema legado e a refatoração completa.

> **Padrão de visibilidade:** classes internas de cada pacote são intencionalmente *package-private* para preservar o encapsulamento. Cada pacote expõe uma única classe `Demo*` pública que serve de fachada para o `SimuladorDeMausCheiros`.

| Seção do livro | Arquivo original | Versão Witcher |
| :--- | :--- | :--- |
| 9.1 Refused Bequest | `Secao91_RefusedBequest.java` | `v1_refused_bequest/RefusedBequest.java` |
| 9.2 Feature Envy | `Secao92_FeatureEnvy.java` | `v2_feature_envy/FeatureEnvy.java` |
| 9.3 Intimidade Inapropriada | `Secao93_IntimidadeInapropriada.java` | `v3_intimidade_inapropriada/IntimidadeInapropriada.java` |
| 9.4 God Class | `Secao94_95_96_GodClass_Divergent_Shotgun.java` | `v4_god_class/GodClass.java` |
| 9.5 Divergent Changes | `Secao94_95_96_GodClass_Divergent_Shotgun.java` | `v5_divergent_changes/DivergentChanges.java` |
| 9.6 Shotgun Surgery | `Secao94_95_96_GodClass_Divergent_Shotgun.java` | `v6_shotgun_surgery/ShotgunSurgery.java` |
| Integração | *(não há no livro)* | `v7_mundo_real/GuildaRefatorada.java` |

---

## 🚫 v1: Refused Bequest

> *"Bequest" = herança/legado. "Refused" = recusado.*

Ocorre quando uma subclasse herda de uma classe pai, mas **não quer** — ou não consegue — honrar parte dos métodos herdados.

### O Sintoma

```java
// ❌ EscolaDeAlquimia herda de EscolaDeGuerra só para reaproveitar getNome()
// Mas ela não treina combate — o que fazer com treinarCombate()?
class EscolaDeAlquimia extends EscolaDeGuerra {
    @Override
    public void treinarCombate() {
        throw new UnsupportedOperationException("Não treinamos combate!"); // ❌ LSP violado
    }
    @Override
    public int calcularBonusDeBatalha() { return 0; } // ❌ silenciosamente errado
}
```

### A Solução

```java
// ✅ Interface com o mínimo comum
interface Escola {
    String getNome();
    int getReputacao();
    String gerarInsignia();
}

// ✅ Cada escola implementa só o que faz sentido
class EscolaDeGuerraReal implements Escola {
    public void treinarCombate()       { /* faz sentido aqui */ }
    public int calcularBonusDeBatalha(){ /* faz sentido aqui */ }
}

class EscolaDeAlquimiaReal implements Escola {
    public void fabricarPocao(String nome) { /* faz sentido aqui */ }
    // treinarCombate() simplesmente não existe — impossível chamar
}
```

> **Relação com o Cap. 6:** Refused Bequest é a manifestação do LSP violado + herança mal usada. A composição ou interfaces específicas resolvem.

---

## 👀 v2: Feature Envy

Um método com *inveja de feature* — ele está mais interessado nos dados de **outro objeto** do que nos dados da própria classe onde está inserido.

### O Sintoma

```java
// ❌ processarContrato() não usa NADA da classe ProcessadorDeContratos
// Ele deveria estar DENTRO de ContratoDeCaca
class ProcessadorDeContratos {
    public void processarContrato(ContratoDeCaca contrato) {
        double imposto = contrato.getValorBase() * 0.15;          // dados de contrato
        if (contrato.getNivelDePerigo() > 3) imposto *= 1.5;       // dados de contrato
        contrato.setImposto(imposto);                               // dados de contrato
        contrato.setEncerrado(true);                                // dados de contrato
    }
}
```

### A Solução

```java
// ✅ O comportamento vive onde os dados vivem
class ContratoDeCaca {
    public void processar() {
        this.imposto = valorBase * 0.15;
        if (nivelDePerigo > 3) this.imposto *= 1.5;
        this.encerrado = true;
    }
}

// ✅ Processador delega — sem inveja
class ProcessadorDeContratos {
    public void processar(ContratoDeCaca contrato) {
        contrato.processar(); // tell, don't ask
    }
}
```

> **Regra prática:** Se um método usa mais `outraClasse.getX()` do que `this.x`, o método provavelmente está no lugar errado.

---

## 🕵️ v3: Intimidade Inapropriada

Uma classe que **conhece e manipula os detalhes internos** de outra — fazendo perguntas sobre o estado para então tomar decisões que deveriam estar dentro do objeto interrogado.

### O Sintoma

```java
// ❌ Gerenciador pergunta o estado interno para decidir por Missao
public void avaliarMissao(Missao missao) {
    if (missao.isEncerrada() && missao.getValor() > 5000) {
        missao.marcarComoUrgente(); // a decisão deveria estar DENTRO de Missao
    }
}
```

Se a regra de "urgente" mudar, é necessário encontrar todos os `if (missao.isEncerrada() && ...)` espalhados pelo sistema.

### A Solução — *Tell, Don't Ask*

```java
// ✅ A missão sabe quando deve ser urgente — regra encapsulada
class Missao {
    public void encerrar() {
        this.encerrada = true;
        if (valor > 5000) this.urgente = true; // regra em um só lugar
    }
}

// ✅ Gerenciador diz o que fazer, não pergunta para decidir
public void encerrarMissao(Missao missao) {
    missao.encerrar(); // tell!
}
```

> **Diferença entre Feature Envy e Intimidade Inapropriada:** Feature Envy é sobre um método no lugar errado. Intimidade Inapropriada é sobre uma classe que sabe demais sobre outra — quebrando o encapsulamento ao perguntar estado interno para tomar decisões externas.

---

## 🐉 v4: God Class

A classe que **faz tudo** — controla muitos objetos, acumula responsabilidades, cresce indefinidamente. É o Anti-SRP em sua forma mais extrema.

### O Sintoma

```java
// ❌ GuildaGodClass: cadastra caçadores, cria contratos, calcula imposto,
// processa pagamentos, envia notificações, gera relatórios...
class GuildaGodClass {
    public void registrarCacador(...) { ... }
    public void criarContrato(...)    { ... }
    public double calcularImposto(...)  { ... }
    public void processarPagamento(...) { ... }
    public void notificarCacador(...)   { ... }
    public String gerarRelatorio()      { ... }
    public void concluirMissao(...)     { ... } // mistura tudo
    // + 500 linhas de lógica misturada
}
```

Sintomas práticos: impossível testar isoladamente, o time tem medo de mexer, qualquer mudança no sistema passa por ela, commits com mensagens completamente diferentes na mesma classe.

### A Solução

```java
// ✅ Cada classe tem UMA responsabilidade
class RegistroDeCacadores   { ... } // só cadastro
class GestorDeContratos     { ... } // só contratos
class CalculadorDeImposto   { ... } // só imposto
class TesouroDaGuilda       { ... } // só pagamentos
class ServicoDeNotificacao  { ... } // só notificações

// ✅ Coordenadora: orquestra sem implementar
class GuildaCoordendora {
    void concluirMissao(String cacador, String monstro, double recompensa) {
        double imposto = calculador.calcular(recompensa);
        tesouro.pagar(cacador, recompensa - imposto);
        notificacao.notificar(cacador, "Missão concluída: " + monstro);
    }
}
```

---

## 🌪️ v5: Divergent Changes

Uma classe que **muda por muitas razões diferentes**. O sintoma aparece no histórico de commits: a mesma classe alterada por "corrigindo imposto", "ajustando relatório", "mudando canal de notificação", "adicionando monstro".

### O Sintoma

```java
// ❌ ProcessadorDeMissao — muda por 4 razões independentes:
class ProcessadorDeMissao {
    double calcularImposto(...)   { ... } // muda quando lei tributária muda
    String gerarRelatorio(...)    { ... } // muda quando formato muda
    void   notificar(...)         { ... } // muda quando canal muda
    String classificarMonstro(){ ... } // muda quando novo tipo é adicionado
}
```

### A Solução

```java
// ✅ Uma classe, uma razão para mudar
class CalculadorDeImpostoMissao  { double calcular(DadosDaMissao d) { ... } }
class GeradorDeRelatorioMissao   { String gerar(DadosDaMissao d) { ... } }
class NotificadorDeMissao        { void notificar(DadosDaMissao d) { ... } }
class ClassificadorDeMonstro     { String classificar(String nome) { ... } }
```

> **Relação com SRP:** Divergent Changes é o SRP violado visto pelo ângulo do histórico de mudanças. Se você pode prever que a classe mudará por razões distintas, divida-a agora.

---

## 💥 v6: Shotgun Surgery

O inverso do Divergent Changes. Uma única mudança de negócio exige **editar muitos arquivos** ao mesmo tempo. Causa raiz: um conceito sem casa própria, espalhado por todo o sistema.

### O Sintoma

```java
// ❌ Taxa da Guilda (0.15) hardcoded em 8 lugares diferentes
class ProcessadorDeContrato {
    double liquida(double bruta) { return bruta - (bruta * 0.15); } // aqui
}
class RelatorioDeContrato {
    String gerar(double bruta) {
        double taxa = bruta * 0.15; // e aqui
    }
}
class AuditoriaDeContrato {
    boolean taxaCorreta(double b, double l) {
        return (b - l) == b * 0.15; // e aqui também
    }
}
// + 5 outros lugares...
```

Mudar a taxa de 15% para 12% = cirurgia de espingarda em 8 arquivos. Com certeza algum será esquecido.

### A Solução

```java
// ✅ O conceito tem uma casa
class TaxaDaGuilda {
    private static final double PADRAO = 0.15; // um único lugar

    public double calcular(double bruta)        { return bruta * PADRAO; }
    public double calcularLiquida(double bruta) { return bruta - calcular(bruta); }
}

// ✅ Todas as classes dependem de TaxaDaGuilda
class ProcessadorDeContrato {
    double liquida(double bruta) { return taxa.calcularLiquida(bruta); } // sem hardcode
}
```

> **Diferença entre Divergent Changes e Shotgun Surgery:**
> - Divergent Changes → 1 classe, N razões para mudar
> - Shotgun Surgery   → 1 razão para mudar, N classes afetadas

---

## 🏰 v7: Mundo Real — A Auditoria de Triss Merigold

O sistema legado da Guilda concentrava **todos os seis smells**. A versão refatorada distribui responsabilidades corretamente.

**Mapa da refatoração:**

| Smell identificado | Classe problemática | Solução aplicada |
| :--- | :--- | :--- |
| **God Class** | `GuildaLegada` (fazia tudo) | `ServicoDeRecompensa`, `RelatorioDeGuilda`, `TaxaDaGuilda` |
| **Refused Bequest** | `CacadorMago extends CacadorDeGuerra` | `CacadorMago implements Cacador, Feiticeiro` |
| **Feature Envy** | Lógica de missão fora de `Missao` | `missao.concluir()`, `missao.descricaoCompleta()` |
| **Intimidade Inapropriada** | Gerenciador perguntando estado de `Missao` | `missao.encerrar()` — Tell, don't ask |
| **Divergent Changes** | `Missao` com responsabilidades de relatório e notificação | `RelatorioDeGuilda` separado |
| **Shotgun Surgery** | Taxa 0.15 hardcoded em todo lugar | `TaxaDaGuilda` centralizada |

---

## 🛡️ Resumo dos Maus Cheiros

| Smell | Definição curta | Sinal de alerta | Solução | Original do livro | Versão Witcher |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Refused Bequest** | Filho rejeita herança do pai | `throw new UnsupportedOperationException()` em override | Composição ou interface menor | `NotaFiscal extends Matematica` | `EscolaDeAlquimia extends EscolaDeGuerra` |
| **Feature Envy** | Método inveja outra classe | Método que só usa `outra.getX()` | Mover o comportamento para onde os dados vivem | `Gerenciador.processa(NotaFiscal)` | `ProcessadorDeContratos.processarContrato()` |
| **Intimidade Inapropriada** | Classe conhece demais outra | Sequência de getters seguida de decisão externa | Tell, don't ask | `if (nf.isEncerrada() && nf.getValor() > 5000)` | `if (missao.isEncerrada() && missao.getValor() > 5000)` |
| **God Class** | Classe que faz tudo | Centenas de linhas, dezenas de dependências | Distribuir responsabilidades | `SistemaDeNotaFiscalGodClass` | `GuildaDoContienenteGodClass` |
| **Divergent Changes** | Classe muda por N razões | Commits com assuntos completamente diferentes | Uma classe, uma razão para mudar | `ProcessadorDeNotaFiscalDivergente` | `ProcessadorDeMissaoDivergente` |
| **Shotgun Surgery** | 1 mudança, N arquivos | Grep + edição manual em todo o projeto | Centralizar o conceito em uma classe | `AliquotaDeImposto` (solução) | `TaxaDaGuilda` (solução) |

> *"Conhecer os maus cheiros é tão importante quanto conhecer as boas práticas. Um bruxo que não conhece as fraquezas dos monstros é apenas um homem com uma espada."*
