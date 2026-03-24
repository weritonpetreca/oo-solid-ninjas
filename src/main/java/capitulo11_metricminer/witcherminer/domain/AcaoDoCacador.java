package capitulo11_metricminer.witcherminer.domain;

public class AcaoDoCacador {
    private final String monstro;
    private final TipoDeAcao tipo;
    private final double recompensa;
    private final String dadosAdicionais;

    public AcaoDoCacador(String monstro, TipoDeAcao tipo, double recompensa, String dados) {
        this.monstro = monstro; 
        this.tipo = tipo; 
        this.recompensa = recompensa; 
        this.dadosAdicionais = dados;
    }

    public String getMonstro() { return monstro; }
    public TipoDeAcao getTipo() { return tipo; }
    public double getRecompensa() { return recompensa; }
    public String getDadosAdicionais() { return dadosAdicionais; }
}
