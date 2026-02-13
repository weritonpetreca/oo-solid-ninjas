package capitulo2_coesao.v4_a_mascara_isp;

/**
 * 🎭 A MÁSCARA (INTERFACE SEGREGADA)
 *
 * No universo SOLID, este arquivo representa a letra "I" (Interface Segregation Principle).
 *
 * ⚔️ O CONCEITO:
 * Uma classe não deve ser forçada a depender de métodos que ela não usa.
 * As regras de cálculo (imposto) só precisam saber sobre dinheiro e cargo.
 * Elas NÃO precisam saber sobre endereço, nome da mãe ou histórico de pedidos.
 *
 * 🛡️ A DEFESA:
 * Ao criar esta interface, nós criamos uma "máscara". Quando a regra olha para
 * o objeto, ela vê apenas estes métodos abaixo. Todo o resto (o "peso" do objeto)
 * fica invisível e inacessível.
 */

public interface DadosParaCalculo {
    double getSalarioBase();
    Cargo getCargo();
}