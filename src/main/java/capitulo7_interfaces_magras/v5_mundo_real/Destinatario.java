package capitulo7_interfaces_magras.v5_mundo_real;

/**
 * Interface magra — qualquer entidade que pode receber comunicados no Continente.
 *
 * Aplica ISP do Cap. 7:
 *   ✅ Uma responsabilidade: expor como localizar e nomear o destinatário
 *   ✅ Caçador, Guilda, Rei — qualquer um pode implementar
 *   ✅ Estável: raramente muda
 */
public interface Destinatario {
    String identificacao(); // nome de código, título ou endereço da guilda
    String nomeCompleto();
}
