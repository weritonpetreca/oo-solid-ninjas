package capitulo9_maus_cheiros.v7_mundo_real;

public class DemoV7 {

    public static void executar() {
        var guilda   = new GuildaRefatorada();
        var geralt   = new CacadorDeGuerra("Geralt",   "Lobo",    90);
        var lambert  = new CacadorDeGuerra("Lambert",  "Lobo",    65);
        var yennefer = new CacadorMago("Yennefer", 95);

        var missao1 = new Missao("Grifo Real",    3000.0, TipoDeMonstro.HIBRIDO);
        var missao2 = new Missao("Grande Strige", 1500.0, TipoDeMonstro.VAMPIRO);
        var missao3 = new Missao("Djinn",         8000.0, TipoDeMonstro.ESPECTRE);

        missao1.aceitar(); missao2.aceitar(); missao3.aceitar();

        guilda.concluirMissao(geralt,   missao1);
        guilda.concluirMissao(lambert,  missao2);
        guilda.concluirMissao(yennefer, missao3);

        System.out.println();
        guilda.imprimirHistorico();
    }
}
