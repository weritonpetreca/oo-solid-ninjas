# Capítulo 7 — Interfaces Magras e o tal do ISP

> *"Interfaces coesas são aquelas que possuem apenas uma única responsabilidade."*
> — Maurício Aniche

Baseado em **Maurício Aniche — Orientação a Objetos e SOLID para Ninjas**, Capítulo 7.

---

## Estrutura

```
v1_interface_gorda/        → Imposto com geraNota() + imposto() — fat interface
v2_interfaces_coesas/      → CalculadorDeImposto + GeradorDeNota separados
v3_tributavel/             → Interface mínima Tributavel para CalculadorDeImposto
v4_repositorio_fabrica/    → RepositorioDeFaturas + FabricaDeCalculadoraDePrecos
v5_mundo_real/             → Sistema de notificações com canais plugáveis
```

---

## V1 — Interface Gorda (Fat Interface)

```java
// ❌ Interface com duas responsabilidades
interface Imposto {
    NotaFiscal geraNota();          // Responsabilidade 1
    double imposto(double valor);   // Responsabilidade 2
}

// Resultado: IXMX não gera nota — mas é forçado a implementar geraNota()
class IXMX implements Imposto {
    public NotaFiscal geraNota() {
        throw new NaoGeraNotaException(); // ❌ gambiarra inevitável
    }
}
```

---

## V2 — Interfaces Coesas

```java
// ✅ Separadas — cada uma com uma responsabilidade
interface CalculadorDeImposto { double imposto(double valor); }
interface GeradorDeNota       { NotaFiscal geraNota(); }

class ISS  implements CalculadorDeImposto, GeradorDeNota { /* ambos */ }
class IXMX implements CalculadorDeImposto { /* só calcula — sem mentira */ }
```

---

## V3 — Interface Mínima: Tributavel

```java
// ❌ Acoplado à NotaFiscal inteira (complexa, instável)
public double calcula(NotaFiscal nf) { ... }

// ✅ Acoplado à Tributavel (mínima, estável, semântica)
public double calcula(Tributavel t) { ... }
```

**Três ganhos:**
1. `Tributavel` é mais estável que `NotaFiscal`
2. O parâmetro tem semântica: "algo passível de tributação"
3. Qualquer classe pode ser tributada — sem herança, só implementar a interface

---

## V4 — Repositório DDD + Fábrica

```java
// Interface para o repositório — separa abstração de infraestrutura
interface RepositorioDeFaturas {
    List<Fatura> todas();
    void salva(Fatura fatura);
}

// Fábrica — monta as dependências em um lugar só
class FabricaDeCalculadoraDePrecos {
    public CalculadoraDePrecos constroi() {
        return new CalculadoraDePrecos(
            new TabelaDePrecosPadrao(),
            new Correios()
        );
    }
}
```

---

## V5 — Exemplo Real: Sistema de Notificações

```java
// Interface magra para destinatário
interface Notificavel { String destinatario(); String nomeExibicao(); }

// Interface magra para canal
interface CanalDeNotificacao { void envia(Notificavel dest, String msg); }

// Composição: plugue quantos canais quiser
ServicoDeNotificacao servico = new ServicoDeNotificacao(
    List.of(new CanalEmail(), new CanalSMS(), new CanalSlack("empresa"))
);
```

---

## Resumo

| Conceito | Problema | Solução |
|---|---|---|
| **Interface gorda** | Classe forçada a implementar método que não faz sentido | Divida em interfaces menores e coesas |
| **ISP** | Dependência de métodos não usados | Interface com uma responsabilidade |
| **Interface mínima** | Acoplamento a classe complexa | Crie abstração com só o necessário |
| **Repositório DDD** | Domínio acoplado ao banco de dados | Interface separa abstração de infraestrutura |
| **Fábrica vs DI** | Quem instancia as dependências? | Fábrica (simples) ou Framework DI (escalável) |
