# 🔗 Capítulo 3: As Amarras do Destino (Acoplamento e DIP)

> *"É impossível criar um software sem acoplamento. Se suas classes não se conhecem, elas não colaboram. O segredo não é eliminar o acoplamento, mas sim escolher bem com quem se acoplar."* — Maurício Aniche

Neste capítulo, saímos das muralhas internas da classe (Coesão) para observar como elas interagem entre si (Acoplamento). Aprendemos que depender de classes concretas é como casar com um monstro específico: quando ele muda, você sofre.

Acompanhe a evolução da nossa arquitetura:

## 📂 v1_acoplamento_concreto

**O Problema (Amor Tóxico):** O `GeradorDeNotaFiscal` exigia parceiros específicos.
* O construtor pedia explicitamente `EnviadorDeEmail` e `NotaFiscalDao`.
* **Risco:** Se precisássemos trocar o E-mail por SMS, ou o DAO por um Arquivo, o Gerador teria que ser operado (modificado).
* **Violação:** O código de alto nível (Regra de Negócio) dependia de detalhes de baixo nível (Infraestrutura).

## 📂 v2_inversao_dependencia

**A Solução (DIP - Dependency Inversion Principle):** O Contrato da Lei da Surpresa.
* Criamos a interface `AcaoAposGerarNota`.
* O `GeradorDeNotaFiscal` agora aceita qualquer um que assine esse contrato (`List<AcaoAposGerarNota>`).
* **Ganho:** O Gerador tornou-se "cego" para a implementação. Ele apenas manda executar.
* **Efeito Colateral (OCP):** Conseguimos adicionar novas funcionalidades (`SapERP`, `LogDeAuditoria`, `EnviadorDeSMS`) sem tocar no código do Gerador.

---

## ⚔️ Crônicas do Concílio: Debates Técnicos

Durante a implementação, enfrentamos conceitos vitais para a sobrevivência de um sistema a longo prazo.

### 1. Estabilidade vs. Instabilidade
Por que podemos depender de `String` ou `List`, mas não de `EnviadorDeEmail`?
* **Classes Estáveis:** Mudam muito pouco (ex: Bibliotecas do Java). É seguro acoplar-se a elas.
* **Classes Instáveis:** Mudam com frequência (Regras de Negócio, Infraestrutura). Fugimos desse acoplamento usando Interfaces.

### 2. O Mito do "New"
Muitos acham que apenas remover a palavra `new` resolve o acoplamento.
* **Mito:** "Se eu recebo no construtor, estou desacoplado."
* **Verdade:** Se você recebe uma Classe Concreta no construtor (`public Gerador(EnviadorDeEmail email)`), você ainda está fortemente acoplado. O desacoplamento real só acontece quando você depende de uma Abstração (`public Gerador(AcaoAposGerarNota acao)`).

### 3. Inteligência Distribuída (Encapsulamento)
No caso do `EnviadorDeSMS`, surgiu a dúvida: Quem decide se o SMS deve ser enviado?
* **Opção Ruim:** O Gerador verifica `if (valor > 1000)`. (Viola o encapsulamento, o Gerador sabe demais).
* **Opção Ninja:** O próprio `EnviadorDeSMS` recebe a ordem e decide se executa ou ignora. O Gerador apenas delega.

---

## 📜 Resumo dos Princípios Abordados

| Sigla | Princípio | Aplicação no Projeto |
| :--- | :--- | :--- |
| **DIP** | Dependency Inversion | O Gerador depende da interface `AcaoAposGerarNota`, não das classes concretas. |
| **OCP** | Open/Closed | Adicionamos `LogDeAuditoria` e `SapERP` sem modificar o `GeradorDeNotaFiscal`. |
| **Encapsulamento** | Tell, Don't Ask | O `EnviadorDeSMS` decide internamente se deve ou não enviar a mensagem baseada no valor. |

> *"Programe para uma interface, não para uma implementação."* — Gang of Four (GoF)
