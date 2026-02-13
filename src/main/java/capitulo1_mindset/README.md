# 🐺 Capítulo 1: O Mindset do Bruxo (Procedural vs. Orientado a Objetos)

> *"Pensar em um sistema orientado a objetos é, portanto, mais do que pensar em código. É desenhar cada peça de um quebra-cabeça e pensar em como todas elas se encaixarão juntas."* — Maurício Aniche

Bem-vindo a Kaer Morhen, iniciante. Antes de aprendermos os sinais avançados (Patterns), precisamos entender a diferença fundamental entre lutar como um soldado raso (Procedural) e lutar como um Bruxo (Orientado a Objetos).

---

## ⚔️ O Conflito: Algoritmo vs. Projeto

A primeira lição do livro é que usar uma linguagem OO (como Java) não garante que seu código seja OO. É muito fácil escrever código procedural disfarçado de classes.

### 1. O Caminho do Soldado (Código Procedural)

No código `BatalhaProcedural.java`, vemos como a maioria começa.

* **O Foco:** A preocupação principal é a implementação do algoritmo. O desenvolvedor pensa: *"Como eu subtraio a vida do monstro?"*
* **O Problema:** Dados e comportamentos estão misturados. O "Soldado" precisa saber detalhes íntimos da espada e do monstro para lutar.
* **A Consequência:** Se a regra de dano da espada mudar, você precisa abrir o cérebro do soldado (a classe principal) para alterar a lógica. Isso viola o princípio de que o desenho da peça é importante, mas seu formato (encaixe) é essencial.

### 2. O Caminho do Lobo (Código Orientado a Objetos)

No código `BatalhaOO.java`, mudamos o foco.

* **O Foco:** A preocupação principal é o **projeto de classes** e como elas se encaixam. O desenvolvedor pensa: *"Quem é responsável por calcular o dano? A Espada. Quem é responsável por morrer? O Monstro."*
* **O Encaixe:** O Bruxo (`Geralt`) não precisa saber matemática. Ele apenas envia uma mensagem (`espada.atacar()`).
* **A Vantagem:** O desenho interno da peça (como a espada calcula o dano) pode mudar completamente. Se o encaixe (o método `atacar`) continuar o mesmo, o resto do sistema (o Bruxo) não precisa ser alterado.

---

## 🧩 A Teoria do Quebra-Cabeça (The Jigsaw Puzzle)

O livro traz uma analogia poderosa que guia todo este estudo: **O Software é um Quebra-Cabeça**.

Imagine que cada classe (`Geralt`, `Espada`, `Grifo`) é uma peça desse quebra-cabeça.

1.  **O Formato da Peça:** É a interface pública da classe (seus métodos públicos). É como ela se conecta às outras.
2.  **O Desenho da Peça:** É a implementação interna (o código dentro dos métodos).

### A Regra de Ouro

> "Se você mudar o formato de uma peça, essa mudança precisará ser propagada para as peças ao redor."

No mundo OO, queremos desenhar peças cujos formatos mudem pouco (interfaces estáveis), permitindo que possamos refazer o desenho interno (implementação) quantas vezes quisermos sem quebrar o resto do jogo.

---

## 🛡️ Melhores Práticas de Mercado (Resumo)

Baseado no que vimos neste capítulo introdutório:

1.  **Pare de pensar apenas em "fazer funcionar" (Implementação).** Comece a pensar em "onde colocar essa responsabilidade" (Projeto).
2.  **Evite a Propagação de Mudanças:** Se você altera uma regra de negócio (como o dano de uma arma) e precisa corrigir 5 arquivos diferentes, seu design falhou. Um bom design OO isola a mudança.
3.  **Encapsulamento é Defesa:** Não deixe seus dados vazarem. A classe `Grifo` cuida de sua própria vida; a classe `Espada` cuida de seu próprio dano.

> *“O desenho da peça é importante, mas se um deles estiver cheio, é mais fácil jogar fora e fazer uma nova peça com o mesmo formato e um desenho novo, do que mudar o formato.”* — Maurício Aniche
