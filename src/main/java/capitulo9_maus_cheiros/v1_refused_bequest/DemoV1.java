package capitulo9_maus_cheiros.v1_refused_bequest;

public class DemoV1 {

    public static void executar() {
        System.out.println("  ❌ EscolaDeAlquimiaComSmell herda EscolaDeGuerraBase — Refused Bequest:");
        var alquimiaSmell = new EscolaDeAlquimiaComSmell("Escola das Ervas", 80);
        try {
            alquimiaSmell.treinarCombate();
        } catch (UnsupportedOperationException e) {
            System.out.println("    treinarCombate() → " + e.getMessage());
        }
        System.out.println("    calcularBonusDeBatalha() → " + alquimiaSmell.calcularBonusDeBatalha() + " (silenciosamente errado)");

        System.out.println("  ✅ Solução — interfaces segregadas, sem herança indevida:");
        var guerra   = new EscolaDeGuerraReal("Escola do Lobo", 95);
        var alquimia = new EscolaDeAlquimiaReal("Escola das Ervas", 80);

        System.out.println("    " + guerra.gerarInsignia() + " | bônus de batalha: " + guerra.calcularBonusDeBatalha());
        System.out.println("    " + alquimia.gerarInsignia() + " | potência da fórmula: " + alquimia.calcularPotenciaDaFormula());
    }
}
