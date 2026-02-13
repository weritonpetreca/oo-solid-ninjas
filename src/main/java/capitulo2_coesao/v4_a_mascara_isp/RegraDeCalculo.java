package capitulo2_coesao.v4_a_mascara_isp;

/**
 * 📜 O CONTRATO SEGURO (v4)
 *
 * A interface que define como uma regra de cálculo deve se comportar.
 *
 * ⚔️ EVOLUÇÃO TÉCNICA:
 * - v2/v3: aceitava 'Funcionario' (Acoplamento forte com a classe concreta).
 * - v4: aceita 'DadosParaCalculo' (Acoplamento suave com uma abstração).
 *
 * Isso permite que qualquer coisa que tenha salário e cargo possa ser calculada,
 * não apenas Funcionários (ex: Prestadores de Serviço, Robôs, etc).
 */

public interface RegraDeCalculo {
    double calcula(DadosParaCalculo dados);
}