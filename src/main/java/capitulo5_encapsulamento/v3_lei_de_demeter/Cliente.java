package capitulo5_encapsulamento.v3_lei_de_demeter;
/**
 * Cliente com estado de inadimplência.
 * Parece simples. O problema está em como classes externas chegam até cá.
 */
public class Cliente {

    private String nome;
    private boolean inadimplente;

    public Cliente(String nome) {
        this.nome = nome;
        this.inadimplente = false;
    }

    public void marcaComoInadimplente() {
        this.inadimplente = true;
    }

    public String getNome() {
        return nome;
    }

    public boolean isInadimplente() {
        return inadimplente;
    }
}
