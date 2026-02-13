package capitulo1_mindset;

public class BatalhaProcedural {
    public static void main(String[] args) {
        // Dados soltos (Structs disfarçadas)
        String tipoMonstro = "GRIFO";
        int vidaMonstro = 100;
        String tipoEspada = "PRATA";
        int danoBaseEspada = 10;

        // O algoritmo está exposto aqui (Procedural)
        // O "Soldado" precisa saber a lógica de cálculo
        if (tipoMonstro.equals("GRIFO")) {
            if (tipoEspada.equals("PRATA")) {
                int danoFinal = danoBaseEspada * 2; // Prata fere Grifo
                vidaMonstro -= danoFinal;
                System.out.println("Soldado causou " + danoFinal + " de dano.");
            } else {
                int danoFinal = danoBaseEspada / 2; // Aço não fere muito
                vidaMonstro -= danoFinal;
                System.out.println("Aço não é bom contra Grifos!");
            }
        }
         if (vidaMonstro <= 0) {
             System.out.println("O Grifo caiu.");
         }
    }
}
