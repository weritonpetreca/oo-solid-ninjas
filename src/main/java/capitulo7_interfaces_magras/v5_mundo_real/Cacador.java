package capitulo7_interfaces_magras.v5_mundo_real;

/** Caçador que pode receber mensagens da Guilda. */
public class Cacador implements Destinatario {
    private final String nome;

    private final String escola;
    private final String localizacaoAtual;
    public Cacador(String nome, String escola, String localizacaoAtual) {
        this.nome = nome;
        this.escola = escola;
        this.localizacaoAtual = localizacaoAtual;
    }

    @Override
    public String identificacao() {
        return localizacaoAtual;
    }

    @Override
    public String nomeCompleto() {
        return nome + " da Escola " + escola;
    }

}
