package capitulo8_consistencia.v6_imutabilidade;

/**
 * PROBLEMA v6: LocalizacaoMutavel — estado interno pode ser alterado por engano.
 *
 * Aniche, Cap. 8, seção 8.6:
 * "Se o desenvolvedor não estiver atento, é fácil cometer um deslize e
 *  trabalhar com uma variável cujo valor atual não sabemos bem."
 *
 * ❌ O método moverPara() altera o estado interno do objeto.
 *    Quem tiver uma referência para esta localização verá o novo valor.
 *    Isso pode causar bugs sutis em sistemas concorrentes ou quando
 *    o mesmo objeto é referenciado em múltiplos lugares.
 */
public class LocalizacaoMutavel {
    private String regiao;
    private double latitude;
    private double longitude;

    public LocalizacaoMutavel(String regiao, double latitude, double longitude) {
        this.regiao = regiao;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // ❌ Muda o estado interno — quem tinha referência agora vê outro lugar
    public void moverPara(String novaRegiao, double novaLatitude, double novaLongitude) {
        regiao = novaRegiao;
        latitude = novaLatitude;
        longitude = novaLongitude;
    }

    public String getRegiao() { return regiao; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
