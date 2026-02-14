# 🔗 Capítulo 3: As Amarras do Destino (Acoplamento e DIP)

> *"É impossível criar um software sem acoplamento. Se suas classes não se conhecem, elas não colaboram. O segredo não é eliminar o acoplamento, mas sim escolher bem com quem se acoplar."* — Maurício Aniche

Neste capítulo, saímos das muralhas internas da classe (Coesão) para observar como elas interagem entre si (Acoplamento). Aprendemos que depender de classes concretas é como casar com um monstro específico: quando ele muda, você sofre.

Exploramos dois cenários distintos de refatoração:

---

## ⚔️ Cenário A: O Gerador de Notas (Foco em DIP)

Aqui combatemos o acoplamento causado pela dependência direta de classes de serviço.

### 🔴 O Problema (v1_acoplamento_concreto)
O `GeradorDeNotaFiscal` exigia parceiros específicos no construtor (`EnviadorDeEmail`, `NotaFiscalDao`).
* **Rigidez:** Se precisássemos trocar o E-mail por SMS, teríamos que alterar o código do Gerador.
* **Testabilidade Ruim:** Difícil de mockar as dependências concretas.

### 🟢 A Solução (v2_classes_estaveis)
Aplicamos o **DIP (Dependency Inversion Principle)**.
* Criamos a interface `AcaoAposGerarNota`.
* O Gerador agora aceita uma `List<AcaoAposGerarNota>`.
* **Resultado:** O Gerador tornou-se "cego" para a implementação. Adicionamos `SapERP`, `LogDeAuditoria` e `EnviadorDeSMS` sem tocar em uma linha sequer do Gerador (OCP).

---

## 📦 Cenário B: O Despachador de Notas (Foco em Encapsulamento)

Aqui combatemos o acoplamento causado pelo excesso de conhecimento (Micro-gerenciamento).

### 🔴 O Problema (v1...despachador_nf)
O `DespachadorDeNotasFiscais` sofria de **Acoplamento Eferente** excessivo.
* Ele conhecia `LeiDeEntrega`, `Correios`, `CalculadorDeImposto` e `NFDao`.
* Ele decidia *como* entregar: `if (lei.urgente(nf)) correios.sedex10()`.
* **Violação:** O Despachador sabia demais sobre a lógica de entrega.

### 🟢 A Solução (v2...despachador_nf)
Aplicamos o **Encapsulamento** para reduzir o acoplamento.
* Criamos a classe `EntregadorDeNFs`.
* Movemos a `LeiDeEntrega` e `Correios` para dentro do Entregador.
* O Despachador agora apenas ordena: `entregador.entrega(nf)`.
* **Resultado:** Reduzimos a complexidade do Despachador e centralizamos a regra de negócio onde ela pertence.

---

## 🧠 Conceitos Chave do Capítulo

### 1. Estabilidade vs. Instabilidade
Nem todo acoplamento é ruim.
* **Classes Estáveis:** Mudam pouco (ex: `String`, `List`, e nossa classe de domínio `Fatura`). É seguro depender delas.
* **Classes Instáveis:** Mudam muito (Serviços, DAOs, Regras de Negócio). Devemos nos proteger delas usando Interfaces ou Encapsulamento.

### 2. O Mito do "New"
* **Mito:** "Se eu recebo no construtor, estou desacoplado."
* **Verdade:** Se você recebe uma Classe Concreta (`public Gerador(EnviadorDeEmail email)`), você ainda está acoplado. O desacoplamento real exige abstrações (`public Gerador(AcaoAposGerarNota acao)`).

### 3. Tell, Don't Ask (Diga, não pergunte)
No cenário do Despachador, paramos de perguntar se a nota era urgente. Passamos a dizer para o especialista: "Entregue". Isso protege o encapsulamento e facilita a manutenção.

---

## 🧪 Testes Unitários

Implementamos testes que provam a evolução da arquitetura:
* `GeradorDeNotaFiscalV1Test` vs `GeradorDeNotaFiscalV2Test`: Mostra como o DIP facilita o uso de Mocks.
* `DespachadorV1Test` vs `DespachadorV2Test`: Mostra como o Encapsulamento simplifica o teste da classe cliente.
* `EntregadorDeNFsTest`: Mostra como testar regras de negócio isoladas.

> *"Programe para uma interface, não para uma implementação."* — Gang of Four (GoF)
