package capitulo8_consistencia.v6_imutabilidade;

/**
 * SOLUÇÃO v6: LocalizacaoImutavel — uma vez criada, não muda nunca.
 *
 * Aniche, Cap. 8, seção 8.6:
 * "Imagine o mundo real: algumas coisas não mudam nunca.
 *  Uma data, um endereço... são ótimas candidatas a classes imutáveis."
 *
 * ✅ Campos final — impossível alterar após construção
 * ✅ Sem setters — ninguém pode modificar o estado
 * ✅ Operações de "mudança" retornam NOVA instância — original intacta
 * ✅ Thread-safe por design — sem estado mutável compartilhado
 *
 * Analogia no Java moderno: LocalDate, LocalDateTime (java.time)
 * fazem exatamente isso — toda operação retorna nova instância.
 */
public class LocalizacaoImutavel {

    private final String regiao;
    private final double latitude;
    private final double longitude;

    public LocalizacaoImutavel(String regiao, double latitude, double longitude) {
        if (regiao == null || regiao.isBlank())
            throw new IllegalArgumentException("Região inválida");
        this.regiao = regiao;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * ✅ "Mudança" retorna nova instância — original nunca muda.
     * Quem tinha referência à original continua vendo o mesmo lugar.
     */
    public LocalizacaoImutavel comNovaRegiao(String novaRegiao) {
        return new LocalizacaoImutavel(novaRegiao, this.latitude, this.longitude);
    }

    public LocalizacaoImutavel comNovasCoordenadas(double novaLatitude, double novaLongitude) {
        return new LocalizacaoImutavel(this.regiao, novaLatitude, novaLongitude);
    }

    public double calcularDistanciaAte(LocalizacaoImutavel outra) {
        // Fórmula simplificada de distância euclidiana
        double dLat = this.latitude - outra.latitude;
        double dLon = this.longitude - outra.longitude;
        return Math.sqrt(dLat * dLat + dLon * dLon);
    }

    public String getRegiao() { return regiao; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
