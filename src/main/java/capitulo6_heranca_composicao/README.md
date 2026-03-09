# 🧬 Capítulo 6: O Dilema do Sangue (Herança vs. Composição)

> *"Herança é como uma maldição de sangue: você herda os poderes do seu pai, mas também suas fraquezas. Composição é como escolher suas armas: você pega apenas o que precisa para a caçada."*

Neste capítulo, exploramos o **Liskov Substitution Principle (LSP)** e o eterno debate entre estender classes ou compor comportamentos. Baseado no Capítulo 6 do livro de Maurício Aniche.

---

## 🚨 v1: A Maldição da Herança (LSP Violado)

A tentativa ingênua de reutilizar código através da herança muitas vezes leva à quebra de contratos.

### O Cenário
Temos uma `ContaComum` que rende juros. Criamos uma `ContaDeEstudante` e, para aproveitar o código de `deposita()` e `saca()`, herdamos de `ContaComum`.

### O Problema
A `ContaDeEstudante` não possui rendimento. O que fazemos com o método `rende()` herdado?

```java
// ContaComum (Pai)
public void rende() {
    this.saldo *= 1.1; // Contrato: O saldo aumenta
}

// ContaDeEstudante (Filho)
@Override
public void rende() {
    throw new ContaNaoRendeException(); // ❌ Quebra de Contrato!
}
```

### A Violação do LSP
O **Liskov Substitution Principle** diz que uma subclasse deve poder substituir a classe pai sem quebrar o sistema.
Se o `ProcessadorDeInvestimentos` recebe uma lista de `ContaComum` e chama `rende()`, ele espera que funcione. Se uma das contas for de estudante, o sistema explode em runtime.

> **Regra de Ouro:** A subclasse não pode exigir mais (pré-condições mais fortes) nem entregar menos (pós-condições mais fracas) que o pai. Lançar uma exceção onde o pai não lançava é entregar menos.

---

## 📐 v2: O Dilema Geométrico (Quadrado é Retângulo?)

Matematicamente, todo quadrado é um retângulo. Em Orientação a Objetos, **isso é uma mentira**.

### O Contrato do Retângulo
Um retângulo permite alterar a largura sem afetar a altura.

```java
Retangulo r = new Retangulo(10, 20);
r.setLargura(30);
// Expectativa: Altura continua 20.
```

### A Traição do Quadrado
Para garantir que os lados sejam iguais, o Quadrado sobrescreve os setters:

```java
@Override
public void setLargura(int largura) {
    this.largura = largura;
    this.altura = largura; // ❌ Efeito colateral inesperado!
}
```

Quem usa a classe `Retangulo` não espera que mudar a largura afete a altura. O `Quadrado` viola o comportamento esperado da classe base.

---

## 🔗 v3: O Acoplamento de Sangue (Pai e Filho)

Herança cria o acoplamento mais forte possível em OO. Se o pai muda, o filho pode quebrar.

### O Uso Perigoso do `super`

```java
// Gerente (Filho)
public double bonus() {
    // Depende da implementação interna do pai!
    return super.bonus() + this.salario * 0.1;
}
```

Se a regra do `Funcionario` (pai) mudar de 20% para 15%, o bônus do gerente muda silenciosamente. O filho não tem controle sobre sua própria regra.

### A Solução
O filho deve implementar sua regra de forma explícita e independente.

```java
// Gerente (Filho - Desacoplado)
public double bonus() {
    return this.salario * 0.3; // 30% fixo, independente do pai
}
```

---

## 🧩 v4: A Cura pela Composição

Como resolver o problema da `ContaDeEstudante` sem herança? Usando **Composição**.

Em vez de "ser uma" Conta Comum, ambas as contas "têm um" `ManipuladorDeSaldo`.

```java
public class ContaDeEstudante {
    private ManipuladorDeSaldo manipulador; // Composição

    public void deposita(double valor) {
        manipulador.deposita(valor); // Delegação
    }
    // Simplesmente NÃO criamos o método rende() aqui.
}
```

**Vantagens:**
1.  **Segurança:** A `ContaDeEstudante` não expõe métodos que não deve (`rende`).
2.  **Reuso:** O `ManipuladorDeSaldo` pode ser usado em qualquer lugar.
3.  **Testabilidade:** Podemos testar o manipulador isoladamente.

> *"Favoreça a composição sobre a herança."* — Gang of Four

---

## 📜 v5: O Pergaminho Antigo (Herança para DSLs)

Existe um caso onde a herança é aceitável, mesmo gerando acoplamento: **Testes e DSLs (Domain Specific Languages)**.

No exemplo `TesteDeCadastroDeCacador`, usamos herança para esconder a complexidade do Selenium/WebDriver e criar uma linguagem fluente.

*   **Classe Pai (`TesteComDsl`):** Contém a "sujeira" da infraestrutura (`driver.findElement...`).
*   **Classe Filha (`TesteDeCadastro`):** Fica limpa, parecendo uma linguagem natural.

```java
// Código limpo na classe filha
public void testaCadastro() {
    preenche("nome", "Geralt");
    clica("btnCadastrar");
    validar("Sucesso");
}
```

**Veredito:** Em testes, a legibilidade e a expressividade valem o preço do acoplamento. É uma troca consciente.

---

## 🦁 v6: O Mundo Real (A Guilda do Leão)

Aqui aplicamos tudo em um cenário complexo de RPG, demonstrando como unir Herança e Composição.

### O Desafio
Calcular recompensas para diferentes tipos de caçadores (`Bruxo`, `Arqueiro`, `Mago`), onde cada um tem regras diferentes e taxas de guilda variadas.

### A Arquitetura Híbrida

1.  **Herança (Onde faz sentido):**
    *   Todos são `Cacador`.
    *   Compartilham atributos estáveis (`nome`, `experiencia`).
    *   O contrato `calcularRecompensaBruta()` é respeitado por todos (LSP ✅). Ninguém lança exceção.

2.  **Composição (Onde varia):**
    *   A taxa da guilda não é fixa na herança.
    *   Injetamos uma estratégia `CalculadorDeTaxa` (Composição ✅).
    *   Isso permite que um `Bruxo` da Escola do Lobo pague uma taxa diferente de um `Bruxo` renegado, sem precisar criar subclasses diferentes para isso.

### Resultado Final
*   **Bruxo:** Usa taxa negociada (10%) e ganha bônus por mutação.
*   **Mago:** Dobra a recompensa em missões perigosas (Lógica encapsulada).
*   **Arqueiro:** Ganha por nível de experiência.
*   **Guilda:** Processa uma lista mista (`List<Cacador>`) sem saber quem é quem.

---

## 🛡️ Resumo dos Princípios

| Sigla | Princípio | Aplicação neste Capítulo |
| :--- | :--- | :--- |
| **LSP** | Liskov Substitution | Subclasses (`Bruxo`, `Mago`) podem substituir a classe base (`Cacador`) sem quebrar o sistema. |
| **OCP** | Open/Closed | Adicionamos novos tipos de caçadores sem mexer na classe `GuildaDoLeao`. |
| **Composição** | "Tem um" vs "É um" | Usamos `CalculadorDeTaxa` como um componente plugável, em vez de herdar taxas fixas. |

> *"Não herde problemas. Componha soluções."*
