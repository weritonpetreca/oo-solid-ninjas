# ⚔️ Capítulo 7: A Arte das Interfaces Magras (ISP)

> *"Uma interface gorda é como um contrato da Guilda mal redigido: obriga o caçador a fazer coisas que ele não sabe — e ele vai mentir pra cumprir."*

Neste capítulo, exploramos o **Interface Segregation Principle (ISP)** — o quarto princípio SOLID. A regra é simples e poderosa: nenhuma classe deve ser forçada a depender de métodos que não usa. Baseado no Capítulo 7 do livro de Maurício Aniche.

---

## 🍖 v1: A Interface Gorda (Fat Interface)

O problema clássico: uma única interface com responsabilidades demais.

### O Cenário
O arquiteto do sistema fiscal do Continente criou uma interface `Imposto` com dois métodos:

```java
// ❌ Interface com duas responsabilidades distintas
public interface Imposto {
    NotaFiscal geraNota();          // Responsabilidade 1: emitir nota
    double imposto(double valor);   // Responsabilidade 2: calcular imposto
}
```

### O Problema
O `ISS` (Imposto Sobre Serviços) calcula imposto **e** emite nota. Sem problema. Mas o `IXMX` só calcula — ele não emite nota fiscal. O que o desenvolvedor faz?

```java
// IXMX — forçado a implementar o que não sabe fazer
public class IXMX implements Imposto {
    @Override
    public NotaFiscal geraNota() {
        throw new NaoGeraNotaException(); // ❌ Opção 1: quebra LSP
        // return null;                   // ❌ Opção 2: NullPointerException à espreita
    }

    @Override
    public double imposto(double valor) {
        return valor * 0.20; // Isso ele sabe fazer
    }
}
```

> **Regra de Ouro:** Se você está implementando um método só para lançar exceção ou retornar `null`, é sinal de que a interface é gorda demais. Você está sendo forçado a mentir.

---

## ✂️ v2: Interfaces Coesas (ISP Aplicado)

A solução: separar em interfaces menores, cada uma com **uma única responsabilidade**.

### A Refatoração

```java
// ✅ Interface 1: só calcula imposto
public interface CalculadorDeImposto {
    double imposto(double valor);
}

// ✅ Interface 2: só gera nota fiscal
public interface GeradorDeNota {
    NotaFiscal geraNota();
}
```

### O Resultado

```java
// ISS faz as duas coisas → implementa as duas interfaces
public class ISS implements CalculadorDeImposto, GeradorDeNota {
    @Override public double imposto(double valor) { return valor * 0.10; }
    @Override public NotaFiscal geraNota() { return new NotaFiscal("ISS - 10%"); }
}

// IXMX só calcula → implementa apenas o que sabe fazer
public class IXMX implements CalculadorDeImposto {
    @Override public double imposto(double valor) { return valor * 0.20; }
    // ✅ Sem geraNota(). Sem exceção. Sem mentira.
}
```

### O `ProcessadorFiscal` agora é preciso

```java
// Quem precisa calcular recebe CalculadorDeImposto — inclui ISS e IXMX
public double somaTodosImpostos(List<CalculadorDeImposto> calculadores, double valor) { ... }

// Quem precisa de nota recebe GeradorDeNota — só ISS está aqui
public List<NotaFiscal> geraTodasAsNotas(List<GeradorDeNota> geradores) { ... }
```

O tipo em si já documenta e garante o comportamento. O compilador vira seu aliado.

---

## 🎯 v3: A Interface Mínima — `Tributavel`

Além de dividir interfaces gordas, o ISP ensina outro truque: **acoplar ao mínimo necessário**.

### O Problema do Acoplamento Excessivo

O `CalculadorDeImposto` precisa apenas dos itens para calcular. Por que receber a `NotaFiscal` inteira?

```java
// ❌ Acoplamento excessivo: a classe complexa entra como parâmetro
public double calcula(NotaFiscal nf) {
    // Só usa os itens — mas está acoplado a cliente, endereço, pagamento...
}
```

Se `NotaFiscal` ganhar novos campos ou mudar seu construtor, o calculador é impactado sem motivo.

### A Interface Mínima

```java
// ✅ Interface com exatamente o que o calculador precisa
public interface Tributavel {
    List<Item> itensASeremTributados();
}

// ✅ O calculador depende apenas de Tributavel — estável e semântico
public double calcula(Tributavel t) {
    double total = 0;
    for (Item item : t.itensASeremTributados()) {
        total += item.getValor() > 1000 ? item.getValor() * 0.02 : item.getValor() * 0.01;
    }
    return total;
}
```

### Três Ganhos Concretos

1. **Estabilidade:** `Tributavel` raramente muda. `NotaFiscal` muda sempre.
2. **Semântica:** O parâmetro diz o que é: *"algo passível de tributação"*, não *"qualquer coisa"*.
3. **Testabilidade:** Criar um `Tributavel` de teste é trivial — até um lambda funciona:

```java
// Teste sem banco, sem NotaFiscal, sem nada extra
Tributavel contrato = () -> List.of(new Item("Contrato: Wyvern", 5000.0));
double imposto = calculador.calcula(contrato); // Funciona perfeitamente
```

---

## 🏛️ v4: Repositório DDD e Fábrica

Dois padrões que completam o ISP na prática de sistemas reais.

### Repositório — Separando Domínio de Infraestrutura

```java
// Interface que representa o que o domínio precisa
public interface RepositorioDeFaturas {
    List<Fatura> todas();
    void salva(Fatura fatura);
    Fatura buscaPorDescricao(String descricao);
}
```

O domínio depende dessa interface estável. A infraestrutura cuida dos detalhes:

```java
// Em produção: usa Hibernate/JDBC
public class FaturaDao implements RepositorioDeFaturas { ... }

// Nos testes: roda em memória, sem banco de dados
public class FaturaRepositorioEmMemoria implements RepositorioDeFaturas { ... }
```

Trocar de banco de dados? Só cria uma nova implementação. O domínio não toca em nada.

### Fábrica — Montando as Dependências em Um Lugar Só

A `CalculadoraDePrecos` precisa de uma `TabelaDePreco` e um `ServicoDeEntrega`. Quem cria essas dependências?

```java
// A Fábrica: acoplada, mas estável — sem regras de negócio
public class FabricaDeCalculadoraDePrecos {
    public CalculadoraDePrecos constroi() {
        return new CalculadoraDePrecos(
            new TabelaDePrecosPadrao(), // Implementação concreta escolhida aqui
            new Correios()              // Troca de transportadora? Muda só aqui
        );
    }
}
```

> **Aniche sobre fábricas:** *"Ela é altamente acoplada, mas isso é menos problemático: fábricas são classes estáveis, sem regras de negócio, e centralizam decisões de design."*

---

## 🔮 v5: O Mundo Real — Serviço de Inteligência do Continente

Tudo aplicado em conjunto: ISP + DIP + Fábrica num sistema de notificações plugável.

### O Cenário
A Conclave dos Magos precisa alertar caçadores usando diferentes meios: Corvo Mensageiro, Pergaminho Mágico e Cristal de Comunicação. Amanhã pode surgir o Sonho de Bruxa.

### Duas Interfaces Magras

```java
// Quem pode receber mensagens
public interface Destinatario {
    String identificacao();
    String nomeCompleto();
}

// Como mensagens são enviadas
@FunctionalInterface
public interface MeioDeComunicacao {
    void envia(Destinatario destinatario, String mensagem);
}
```

### O Serviço Completamente Plugável

```java
// Não sabe nada sobre Corvo, Pergaminho ou Cristal — só sobre MeioDeComunicacao
public class ServicoDeInteligencia {
    private final List<MeioDeComunicacao> meios;

    public ServicoDeInteligencia(List<MeioDeComunicacao> meios) {
        this.meios = meios;
    }

    public void alertaTodos(List<Destinatario> destinatarios, String mensagem) {
        for (Destinatario dest : destinatarios) {
            meios.forEach(meio -> meio.envia(dest, mensagem)); // cada meio faz a sua parte
        }
    }
}
```

### A Fábrica Controla os Cenários

```java
public class FabricaDoServicoDeInteligencia {

    // Alerta rotineiro: só corvo
    public ServicoDeInteligencia criaServicoBasico() {
        return new ServicoDeInteligencia(List.of(new CorvoMensageiro()));
    }

    // Emergência: pergaminho + cristal
    public ServicoDeInteligencia criaServicoDeEmergencia() {
        return new ServicoDeInteligencia(List.of(
            new PergaminhoMagico(),
            new CristalDeComunicacao("EMERGENCIA-001")
        ));
    }
}
```

**Adicionar o Sonho de Bruxa no futuro?**
```java
// 1. Cria a classe — zero mudança em tudo que já existe
public class SonhoDeBruxa implements MeioDeComunicacao {
    @Override
    public void envia(Destinatario dest, String mensagem) { ... }
}

// 2. Registra na fábrica — só aqui
new ServicoDeInteligencia(List.of(new CorvoMensageiro(), new SonhoDeBruxa()));
```

`ServicoDeInteligencia` não muda. `Cacador` não muda. O sistema cresce sem medo.

---

## 🛡️ Resumo dos Princípios

| Conceito | Problema que resolve | Como aparece no capítulo |
| :--- | :--- | :--- |
| **ISP** | Classe forçada a implementar métodos que não usa | `IXMX` não implementa `geraNota()` — não existe na interface dele |
| **Interface mínima** | Acoplamento a classes complexas e instáveis | `Tributavel` no lugar de `NotaFiscal` inteira |
| **Semântica de tipo** | Parâmetros genéricos sem significado | `Tributavel` comunica intenção; `List<Item>` não |
| **Repositório DDD** | Domínio acoplado à infraestrutura (banco, Hibernate) | `RepositorioDeFaturas` separa abstração da implementação |
| **Fábrica** | Dependências instanciadas em lugares errados | `FabricaDeCalculadoraDePrecos` centraliza a montagem |
| **OCP via ISP** | Adicionar canal exige alterar código existente | Novo `MeioDeComunicacao` não toca em `ServicoDeInteligencia` |

> *"Interfaces magras não são interfaces pequenas por preguiça — são interfaces precisas por sabedoria. Um contrato que diz exatamente o que precisa, nada mais."*