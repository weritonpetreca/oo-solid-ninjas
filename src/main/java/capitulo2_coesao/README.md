# 🧪 Capítulo 2: A Jornada da Coesão (Refatorando a Striga)

> *"Coesão é ter uma classe que faz apenas uma coisa, e faz bem feito. Uma classe coesa não tem crise de identidade."*

Neste módulo, pegamos uma classe procedural (uma verdadeira Striga) e aplicamos os princípios SOLID para torná-la flexível. Acompanhe a evolução nas pastas:

## 📂 v1_a_maldicao_god_class
**O Problema (God Class):** A `CalculadoraDeSalario` sabia demais.
- Continha todas as regras de negócio misturadas.
- Usava uma cadeia de `if/else` para checar cargos.
- **Violação do SRP:** Qualquer mudança em uma regra arriscava quebrar as outras.

## 📂 v2_o_sinal_strategy
**A Estratégia (Strategy Pattern):** Dividir para conquistar.
- Criamos a interface `RegraDeCalculo`.
- Extraímos cada lógica (10% ou 20%, etc.) para classes especialistas.
- **Ganho:** As regras agora são testáveis isoladamente.
- **Pendência:** A Calculadora ainda tinha `ifs` para escolher qual classe instanciar.

## 📂 v3_o_elixir_enum
**A Solução Elegante:** Polimorfismo no Enum.
- Movemos a decisão para o `Cargo`.
- O Enum agora sabe qual regra ele usa (`DESENVOLVEDOR(new DezOuVinte...)`).
- A `CalculadoraDeSalario` foi reduzida a uma única linha.
- **Open/Closed Principle (OCP):** Podemos adicionar novos cargos sem nunca mais tocar na classe da calculadora.

## 📂 v4_a_mascara_isp (Bônus Técnico)
**A Segurança:** Interface Segregation Principle.
- Resolvemos o dilema de passar o objeto inteiro vs. passar dados soltos.
- Criamos a interface `DadosParaCalculo` para servir de "máscara".
- A regra de cálculo não tem mais acesso a métodos perigosos do funcionário (blindagem contra Lazy Loading do Hibernate).

---

# ⚔️ Crônicas do Concílio: Debates Técnicos

Durante o estudo deste capítulo, uma questão fundamental de arquitetura foi levantada:

### O Dilema: Passar o Objeto vs. Passar Valores
Ao definir o método `calcula()`, qual assinatura devemos usar?

#### Opção A: `calcula(double salario)`
* **Argumento:** Desacopla totalmente a regra da entidade. Economiza memória (mito) e evita acesso indevido ao banco.
* **Problema:** Perda de contexto. Se a regra mudar para depender do "Tempo de Casa", teremos que refatorar todas as chamadas do sistema.

#### Opção B: `calcula(Funcionario funcionario)` (Abordagem do Livro)
* **Argumento:** Mantém o contexto para evolução futura.
* **Problema:** A regra ganha acesso a métodos que não deveria (ex: `getSenha()`, `getPedidos()`). Em sistemas com ORM, isso gera risco de performance.

### 🛡️ O Veredito: A Solução ISP (v4)
Para ter o melhor dos dois mundos, aplicamos o **Interface Segregation Principle (ISP)**.

Não passamos o objeto concreto, nem valores soltos. Passamos uma **Interface Focada**:

```java
// A regra enxerga apenas o necessário através da interface
public interface DadosParaCalculo {
    double getSalarioBase();
    Cargo getCargo();
}
```

Isso garante **Segurança** (o método não vê o resto do objeto) e **Flexibilidade** (podemos calcular o imposto de qualquer objeto que implemente essa interface).

---

## 📜 Resumo dos Princípios Abordados

| Sigla | Princípio | Aplicação no Projeto |
| :--- | :--- | :--- |
| **SRP** | Single Responsibility | Classes de regra separadas (v2). |
| **OCP** | Open/Closed | Adicionar cargos sem mexer na Calculadora (v3). |
| **LSP** | Liskov Substitution | As regras implementam a interface e podem ser trocadas sem quebrar a Calculadora. |
| **ISP** | Interface Segregation | A máscara `DadosParaCalculo` protege o objeto `Funcionario` (v4). |

> *"O código perfeito é aquele que, mesmo complexo por dentro, apresenta uma face simples e segura para quem o usa."*

## 📝 Notas Finais

### Conclusão do Mestre:
> Não tenha medo de criar classes pequenas. É melhor ter 10 classes pequenas e focadas (um exército de especialistas) do que uma classe gigante e confusa (um gigante desajeitado).

### Nota do Arquivista:
Perceba que duplicamos a interface `RegraDeCalculo` e as implementações em cada pacote (`v1`, `v2`, `v3`, `v4`). Em um projeto real (refatoração in-place), isso não aconteceria. Fizemos isso aqui apenas para que cada etapa possa ser compilada e estudada isoladamente como um "snapshot" da evolução.
