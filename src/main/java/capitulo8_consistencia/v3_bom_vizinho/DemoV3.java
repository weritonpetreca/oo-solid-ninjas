package capitulo8_consistencia.v3_bom_vizinho;

/**
 * Fachada pública para demonstração do v3 — acessa as classes package-private do pacote.
 */
public class DemoV3 {

    public static void executar() {
        ServicoDeContratoComNulos servicoAntigo = new ServicoDeContratoComNulos();
        System.out.println("Com nulos espalhados (serviço antigo):");
        servicoAntigo.processarMissao("Caça à Strige", null);
        servicoAntigo.processarMissao("Caça ao Lobisomem", new CacadorReal("Lambert", "Lobo", 65));

        System.out.println();
        System.out.println("Com Null Object (serviço seguro):");
        ServicoDeContratoSeguro servicoSeguro = new ServicoDeContratoSeguro();
        servicoSeguro.processarMissao("Caça à Strige", new CacadorDesconhecido());
        servicoSeguro.processarMissao("Caça ao Lobisomem", new CacadorReal("Lambert", "Lobo", 65));
    }
}
