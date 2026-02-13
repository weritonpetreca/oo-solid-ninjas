package capitulo2_coesao.v3_o_elixir_enum;

/**
 * 📜 O CONTRATO (VERSÃO 3)
 *
 * Precisamos redefinir a interface aqui porque ela agora lida com o
 * Funcionario desta versão (v3).
 *
 * ⚔️ LIÇÃO DE ARQUITETURA:
 * Em um projeto real, você não teria versões v1, v2, v3. Você apenas alteraria
 * a interface original. Mas, como este é um museu de evolução de código,
 * criamos este isolamento para evitar conflitos de importação.
 */

public interface RegraDeCalculo {
    double calcula(Funcionario funcionario);
}