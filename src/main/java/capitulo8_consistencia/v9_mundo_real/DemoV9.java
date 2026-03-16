package capitulo8_consistencia.v9_mundo_real;

/**
 * Fachada pública para demonstração do v9 — acessa as classes package-private do pacote.
 */
public class DemoV9 {

    public static void executar() {
        CadastroDeGuilda guilda = new CadastroDeGuilda();

        System.out.println("Registrando caçadores...");
        var r1    = guilda.registrar("Geralt de Rívia", "Lobo", "G01-LOBO", "Kaer Morhen", "Fortaleza do Lobo");
        var r2    = guilda.registrar("Ciri",            "Lobo", "C99-LOBO", "Novigrad",    "Palácio da Corte");
        var r3    = guilda.registrar("Lambert",         "Lobo", "L03-LOBO", "Oxenfurt",    "Taverna do Bruxo");
        var rFail = guilda.registrar("", "Lobo", "credencial-invalida", "", "");

        if (r1.isSucesso()) System.out.println("    ✅ Registrado: " + r1.getCacador().getNome());
        if (r2.isSucesso()) System.out.println("    ✅ Registrado: " + r2.getCacador().getNome());
        if (r3.isSucesso()) System.out.println("    ✅ Registrado: " + r3.getCacador().getNome());
        System.out.println("    ❌ Falha:       " + rFail.getErros());

        System.out.println();
        System.out.println("Operações pós-registro...");
        var geralt = r1.getCacador();
        geralt.registrarMissaoConcluida("Caça ao Grifo de Velen");
        geralt.registrarMissaoConcluida("Caça à Manticora de Skellige");
        geralt.moverPara("Toussaint", "Castelo Beauclair");

        System.out.println();
        System.out.println("Ficha de caçador (DTO):");
        FichaDeCacadorDTO ficha = geralt.gerarFicha();
        System.out.println("    Nome:       " + ficha.nomeDeGuerra);
        System.out.println("    Escola:     " + ficha.escola);
        System.out.println("    Credencial: " + ficha.credencial);
        System.out.println("    Localização:" + ficha.localizacao);
        System.out.println("    Missões:    " + ficha.totalMissoesConcluidas);
        System.out.println("    Status:     " + ficha.statusAtivo);

        System.out.println();
        System.out.println("Busca pelo nome:");
        guilda.buscarPorNome("Ciri").ifPresentOrElse(
                c -> System.out.println("    ✅ Encontrado: " + c.getNome()),
                ()  -> System.out.println("    ❌ Não encontrado")
        );
        guilda.buscarPorNome("Vesemir").ifPresentOrElse(
                c -> System.out.println("    ✅ Encontrado: " + c.getNome()),
                ()  -> System.out.println("    Vesemir não está no cadastro")
        );
        System.out.println("    Total ativos: " + guilda.listarAtivos().size());
    }
}
