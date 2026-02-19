# 🔗 Capítulo 3: As Amarras do Destino (Acoplamento e DIP)

> *"É impossível criar um software sem acoplamento. Se suas classes não se conhecem, elas não colaboram. O segredo não é eliminar o acoplamento, mas sim escolher bem com quem se acoplar."* — Maurício Aniche

Neste capítulo, saímos das muralhas internas da classe (Coesão) para observar como elas interagem entre si (Acoplamento). Aprendemos que depender de classes concretas é como casar com um monstro específico: quando ele muda, você sofre.

Exploramos três níveis de evolução arquitetural:

---

## ⚔️ Cenário A: O Gerador de Notas (Foco em DIP)

Aqui combatemos o acoplamento causado pela dependência direta de classes de serviço.

### 🔴 v1_acoplamento_concreto (O Problema)
O `GeradorDeNotaFiscal` exigia parceiros específicos no construtor (`EnviadorDeEmail`, `NotaFiscalDao`).
* **Rigidez:** Se precisássemos trocar o E-mail por SMS, teríamos que alterar o código do Gerador.
* **Testabilidade Ruim:** Difícil de mockar as dependências concretas.

### 🟡 v2_inversao_dependencia (A Solução Intermediária)
Aplicamos o **DIP (Dependency Inversion Principle)**.
* Criamos a interface `AcaoAposGerarNota`.
* O Gerador agora aceita uma `List<AcaoAposGerarNota>`.
* **Resultado:** O Gerador tornou-se "cego" para a implementação. Adicionamos `SapERP`, `LogDeAuditoria` e `EnviadorDeSMS` sem tocar em uma linha sequer do Gerador (OCP).

### 🟢 v3_dip_completo (A Arquitetura Hexagonal + Clean Arch)
Levamos o desacoplamento ao extremo.
* **Ports & Adapters:** O Gerador (Use Case) define portas (interfaces) que o mundo externo (Adapters) deve implementar.
* **Isolamento Total:** O domínio não conhece nada sobre infraestrutura.

---

## 📦 Cenário B: O Despachador de Notas (Foco em Encapsulamento)

Aqui combatemos o acoplamento causado pelo excesso de conhecimento (Micro-gerenciamento).

### 🔴 v1_acoplamento_concreto (O Micro-Gerenciador)
O `DespachadorDeNotasFiscais` sofria de **Acoplamento Eferente** excessivo.
* Ele conhecia `LeiDeEntrega`, `Correios`, `CalculadorDeImposto` e `NFDao`.
* Ele decidia *como* entregar: `if (lei.urgente(nf)) correios.sedex10()`.
* **Violação:** O Despachador sabia demais sobre a lógica de entrega.

### 🟡 v2_inversao_dependencia (O Delegador)
Aplicamos o **Encapsulamento** para reduzir o acoplamento.
* Criamos a classe `EntregadorDeNFs`.
* Movemos a `LeiDeEntrega` e `Correios` para dentro do Entregador.
* O Despachador agora apenas ordena: `entregador.entrega(nf)`.
* **Resultado:** Reduzimos a complexidade do Despachador e centralizamos a regra de negócio onde ela pertence.

### 🟢 v3_dip_completo (O Comandante Hexagonal)
O Despachador agora depende apenas de **Interfaces (Ports)**.
* `CalculadorDeImposto` (Interface)
* `Entregador` (Interface)
* `Repositorio` (Interface)
* **Benefício:** Podemos trocar o `EntregadorDeNFs` por um `EntregadorDeDrones` ou o `NFDao` por um `ArquivoDao` sem recompilar o Despachador.

---

## 🏰 Arquitetura Hexagonal vs. Clean Architecture

Na versão `v3`, utilizamos uma abordagem híbrida que combina o melhor dos dois mundos.

### 1. Hexagonal (Ports & Adapters)
Focada em **Interfaces**.
* **Ports (Portas):** São as interfaces que definem os serviços (`Repositorio`, `Entregador`). Elas ficam DENTRO do hexágono (núcleo).
* **Adapters (Adaptadores):** São as implementações concretas (`NFDao`, `Correios`). Elas ficam FORA do hexágono.

### 2. Clean Architecture (Onion)
Focada em **Camadas Concêntricas**.
* **Entities (Domain):** O centro absoluto. Regras de negócio puras (`NotaFiscal`, `Fatura`). Não dependem de ninguém.
* **Use Cases (Application):** Orquestram o fluxo (`DespachadorDeNotasFiscais`). Dependem apenas do Domínio.

### 🛡️ Nossa Implementação (v3)
Unimos os conceitos:
* `domain`: Entidades puras (Clean Arch).
* `usecases`: Regras de aplicação (Clean Arch).
* `ports`: Interfaces para saída (Hexagonal).
* `adapters`: Implementações externas (Hexagonal).

---

## 🧪 A Prova de Fogo: Testes Unitários e Arquiteturais

Os testes não servem apenas para garantir que funciona, eles servem como um **Termômetro de Design**.

### 1. O Inferno do Setup (`DespachadorV1Test`)
Para testar a classe acoplada, tivemos que criar **4 Mocks** (`Dao`, `Imposto`, `Lei`, `Correios`) e configurar comportamentos complexos (`when(lei...).thenReturn(...)`).
* **Lição:** Se o setup do seu teste é gigante, sua classe está acoplada demais.

### 2. A Redenção (`DespachadorV2Test`)
Ao encapsular a lógica no `EntregadorDeNFs`, o teste do Despachador ficou limpo. Só precisamos verificar se ele chamou `entregador.entrega()`.
* **Lição:** Classes delegadoras são fáceis de testar.

### 3. O Foco (`EntregadorDeNFsTest`)
Testamos a regra de negócio (Sedex 10 vs Comum) isoladamente.
* **Lição:** Testar classes pequenas e coesas é trivial. Se falhar, sabemos exatamente onde está o erro.

### 4. O Guardião (`ArquiteturaTest`)
Utilizamos a biblioteca **ArchUnit** para garantir que as regras do DIP não sejam violadas.
* Testamos se o pacote `domain` depende de `adapters` (Proibido!).
* Testamos se o pacote `ports` depende de `adapters` (Proibido!).
* **Lição:** Em projetos grandes, testes arquiteturais impedem que desenvolvedores quebrem o isolamento das camadas por descuido.

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

### 4. Intimidade Indesejada (Feature Envy)
Na V1, o Despachador tinha muita "intimidade" com a `LeiDeEntrega` e os `Correios`. Ele sabia detalhes internos de como eles funcionavam. O bom design OO preza pela privacidade: cada objeto cuida de seus dados e comportamentos.

> *"Programe para uma interface, não para uma implementação."* — Gang of Four (GoF)
