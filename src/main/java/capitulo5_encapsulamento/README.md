# Capítulo 5 — Encapsulamento e Propagação de Mudanças

> *"O lobo branco não pergunta ao monstro como ele ataca. Ele o estuda, aprende o padrão, e age. Código bem encapsulado funciona igual: você sabe o que ele faz, mas não precisa saber como."*

Baseado em **Maurício Aniche — Orientação a Objetos e SOLID para Ninjas**, Capítulo 5.

---

## O que é encapsulamento (de verdade)?

Não é só `private` nos atributos. Encapsulamento é **esconder o COMO e expor só o QUÊ**.

O teste prático do Aniche (seção 5.5): olhe para um método e responda duas perguntas:

| Pergunta | Resposta esperada |
|----------|------------------|
| **O quê** esse método faz? | Você deve conseguir responder pelo nome |
| **Como** ele faz? | Você **não** deve conseguir responder só olhando de fora |

Se você conseguir responder as duas, o método está mal encapsulado.

---

## Estrutura das versões

```
capitulo5_encapsulamento/
├── v1_problema_encapsulamento/   → Fatura exposta, regra de negócio vazando
├── v2_intimidade_inapropriada/   → NotaFiscal e Tell, Don't Ask
├── v3_lei_de_demeter/            → fatura.getCliente().marcaComoInadimplente()
├── v4_solucao_completa/          → Tudo encapsulado, modelo rico
├── v5_modelo_anemico/            → Antipadrão: dados separados de comportamento
└── simulador/                    → Demonstração interativa de todos os cenários
```

---

## V1 — O problema: código que funciona mas está podre por dentro

O código roda. Os testes passam. O gerente está feliz.

```java
// ProcessadorDeBoletos.processa()

// ❌ PROBLEMA 1: acessa a lista interna diretamente
fatura.getPagamentos().add(pagamento);

// ❌ PROBLEMA 2: regra de negócio da Fatura vivendo fora dela
if (total >= fatura.getValor()) {
    fatura.setPago(true);
}
```

**Por que é perigoso?**

A regra de "quando uma fatura está paga" está no `ProcessadorDeBoletos`.  
Amanhã aparece o `ProcessadorDeCartaoDeCredito`. Ele copia esse `if`.  
Depois o `ProcessadorDePix`. Mais uma cópia.

A regra muda? Você faz CTRL+F e torce para não ter esquecido nenhum.  
Isso é **propagação de mudanças descontrolada** — o pesadelo de sistemas legados.

---

## V2 — Intimidade inapropriada e Tell, Don't Ask

```java
// ❌ Intimidade inapropriada: classe cliente sabe demais sobre NotaFiscal
if (nf.getValorSemImposto() > 10000) {
    valor = 0.06 * nf.getValor();
} else {
    valor = 0.12 * nf.getValor();
}
```

Quem conhece as alíquotas fiscais? Quem sabe o limite de 10000?  
Quem **deveria** saber? A própria `NotaFiscal`.

**Tell, Don't Ask (seção 5.4):**  
Não pergunte ao objeto para depois tomar uma decisão.  
Diga a ele o que fazer.

```java
// ✅ Tell, Don't Ask: diga ao objeto o que fazer
double valor = nf.calculaValorImposto();
```

A regra fiscal mudou? Muda dentro de `NotaFiscal`. Um lugar. Só.

---

## V3 — A famosa Lei de Demeter

```java
// ❌ Viola a Lei de Demeter
fatura.getCliente().marcaComoInadimplente();
```

Parece simples. Mas `ServicoDeCobranca` agora depende de:
- `Fatura` — dependência declarada ✅
- `Cliente` — dependência **oculta**, via `getCliente()` ❌

Se `Cliente` mudar sua interface pública, isso quebra.  
E em todo lugar que fez `fatura.getCliente().qualquerCoisa()`.

**Lei de Demeter: fale só com seus amigos imediatos.**

```java
// ✅ Correto: Fatura encapsula o acesso ao Cliente
fatura.marcaComoInadimplente();
// Internamente: cliente.marcaComoInadimplente()
```

Se `Cliente` mudar: só `Fatura` é afetada. Um ponto de mudança, não dez.

> **Exceção válida (o próprio Aniche menciona):** encadear getters na camada de visualização  
> (`fatura.getCliente().getEndereco().getRua()`) é aceitável quando o objetivo é apenas exibir dados.

---

## V4 — Solução completa: modelo rico

```java
public class Fatura {

    // ✅ Única porta de entrada para pagamentos
    public void adicionaPagamento(Pagamento pagamento) {
        this.pagamentos.add(pagamento);
        if (valorTotalDosPagamentos() >= this.valor) {
            this.pago = true; // A Fatura decide sozinha quando está paga
        }
    }

    // ✅ Lista imutável — ninguém modifica por fora
    public List<Pagamento> getPagamentos() {
        return Collections.unmodifiableList(pagamentos);
    }

    // ✅ Privado — implementação escondida
    private double valorTotalDosPagamentos() { ... }

    // setPago() foi removido — não há razão para existir
}
```

```java
// ProcessadorDeBoletos limpo: só faz o que é dele
public void processa(List<Boleto> boletos, Fatura fatura) {
    for (Boleto boleto : boletos) {
        Pagamento pagamento = new Pagamento(boleto.getValor(), MeioDePagamento.BOLETO);
        fatura.adicionaPagamento(pagamento); // ✅ Tell, Don't Ask
    }
}
```

O `ProcessadorDeCartaoDeCredito` vai usar o mesmo `adicionaPagamento()`.  
O `ProcessadorDePix` também. Nenhum deles vai duplicar lógica. Nunca.

---

## V5 — Antipadrão: Modelo Anêmico

```java
// ❌ Classe de dados: zero comportamento
class Fatura {
    private String cliente;
    private double valor;
    private boolean pago;
    // getters e setters para tudo
}

// ❌ Classe de lógica: tudo aqui, nada na Fatura
class FaturaBLL {
    public void finaliza(Fatura f) { ... }
    public double calculaImposto(Fatura f) { ... }
    public void adicionaPagamento(Fatura f, ...) { ... }
}
```

Parece "separação de responsabilidades". É código procedural em Java.  
Dados de um lado, funções do outro — exatamente como em C.

Aniche (seção 5.9): *"É, na maioria dos casos, um grande passo para trás."*

---

## O sistema como quebra-cabeça (seção 5.3)

Aniche usa uma analogia poderosa: pense no seu sistema como um quebra-cabeça.

- **Formato da peça** = a interface pública da classe (o QUÊ)
- **Desenho interno** = a implementação (o COMO)

Se você mudar o **formato** (interface pública), outras peças precisam ser ajustadas.  
Se você mudar só o **desenho** (implementação), ninguém percebe.

Bom encapsulamento = você pode trocar o desenho interno à vontade.

---

## Resumo

| Conceito | Problema | Solução |
|----------|----------|---------|
| **Encapsulamento** | Regra de negócio espalhada | Mova a regra para dentro do dono |
| **Tell, Don't Ask** | Pergunta → decisão → ação | Dê ordens diretas ao objeto |
| **Lei de Demeter** | `a.getB().getC().metodo()` | Encapsule a operação na classe intermediária |
| **Getters perigosos** | `getPagamentos().add(...)` | Retorne listas imutáveis; crie métodos de comportamento |
| **Modelo anêmico** | Dados ≠ comportamento | Dados e comportamento juntos, sempre |

---

