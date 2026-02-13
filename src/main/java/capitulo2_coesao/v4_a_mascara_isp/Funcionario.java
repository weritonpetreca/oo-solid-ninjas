package capitulo2_coesao.v4_a_mascara_isp;

import java.util.List;

/**
 * 👤 FUNCIONÁRIO (A BESTA ENJAULADA)
 *
 * Esta classe representa uma Entidade rica do sistema (talvez mapeada no Banco de Dados).
 *
 * ⚔️ O PROBLEMA DO CLUBE DE LEITURA:
 * O grupo temeu que passar o objeto inteiro fosse perigoso ("pesado" ou risco de acesso indevido).
 *
 * 🛡️ A SOLUÇÃO ISP:
 * Esta classe implementa 'DadosParaCalculo'. Quando passada para a calculadora,
 * ela é tratada apenas como essa interface.
 *
 * Veja o método 'getPedidos()'. Ele simula um acesso perigoso ao banco (Lazy Load).
 * Graças à interface, a Calculadora NÃO consegue chamar esse método acidentalmente,
 * pois ele não existe no contrato 'DadosParaCalculo'.
 */
public class Funcionario implements DadosParaCalculo {
    private String nome;
    private Cargo cargo;
    private double salarioBase;

    public Funcionario(String nome, Cargo cargo, double salarioBase) {
        this.nome = nome;
        this.cargo = cargo;
        this.salarioBase = salarioBase;
    }

    // ✅ Este método é visível pela máscara (Seguro)
    @Override
    public double getSalarioBase() {
        return salarioBase;
    }

    // ✅ Este método é visível pela máscara (Seguro)
    @Override
    public Cargo getCargo() {
        return cargo;
    }

    public String getNome() {
        return nome;
    }

    /**
     * ⛔ ZONA DE PERIGO (Simulação de ORM/Hibernate)
     *
     * Este método representa uma lista que só é carregada do banco se for chamada.
     * Se a Regra de Cálculo recebesse a classe 'Funcionario' completa e chamasse isso,
     * causaria uma queda de performance (Problema N+1).
     *
     * Como este método NÃO está na interface 'DadosParaCalculo', ele está BLINDADO.
     */
    public List<String> getPedidos() {
        throw new RuntimeException("🔥 ACESSO AO BANCO DE DADOS NÃO AUTORIZADO! 🔥");
    }
}