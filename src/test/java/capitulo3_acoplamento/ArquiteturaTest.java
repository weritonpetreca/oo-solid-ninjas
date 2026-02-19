package capitulo3_acoplamento;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * 🏰 O GUARDIÃO DA ARQUITETURA (ARCHUNIT)
 *
 * Este teste não verifica se o código funciona (lógica), mas sim se ele está ORGANIZADO corretamente.
 * Ele garante que as regras da Arquitetura Hexagonal (DIP) não sejam violadas por descuido.
 *
 * 🛡️ POR QUE ISSO É IMPORTANTE?
 * Em projetos grandes, é fácil um desenvolvedor júnior importar uma classe de Banco de Dados
 * dentro de uma Regra de Negócio sem querer. O ArchUnit impede que esse código seja commitado.
 *
 * ⚔️ AS LEIS DE KAER MORHEN (REGRAS):
 * 1. O Domínio é sagrado e não deve conhecer ninguém de fora.
 * 2. As Portas (Interfaces) definem o contrato e não podem depender de quem as implementa (Adapters).
 */
@AnalyzeClasses(packages = "capitulo3_acoplamento.v3_dip_completo")
public class ArquiteturaTest {

    /**
     * 📜 REGRA 1: PUREZA DO DOMÍNIO
     * Nenhuma classe no pacote 'domain' deve depender de classes no pacote 'adapters'.
     * (A Entidade não pode conhecer o Banco de Dados ou a Web).
     */
    @ArchTest
    static final ArchRule dominio_nao_deve_conhecer_adapters =
            noClasses()
                    .that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat().resideInAPackage("..adapters..");

    /**
     * 📜 REGRA 2: INDEPENDÊNCIA DA APLICAÇÃO
     * O Domínio não deve conhecer os Casos de Uso.
     * (A Regra da Empresa não depende da Regra da Aplicação).
     */
    @ArchTest
    static final ArchRule dominio_nao_deve_conhecer_usecases =
            noClasses()
                    .that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat().resideInAPackage("..usecases..");

    /**
     * 📜 REGRA 3: ESTABILIDADE DAS PORTAS
     * As Portas (Interfaces) devem ser independentes.
     * Elas definem o contrato, não podem depender de quem implementa (Adapters).
     */
    @ArchTest
    static final ArchRule ports_nao_devem_conhecer_adapters =
            noClasses()
                    .that().resideInAPackage("..ports..")
                    .should().dependOnClassesThat().resideInAPackage("..adapters..");

    /**
     * 📜 REGRA 4: AGNOSTICISMO TECNOLÓGICO
     * Só os Adapters podem depender de Bibliotecas Pesadas (ex: SQL, Spring Web).
     * (Aqui simulamos proibindo o 'domain' de acessar java.sql).
     */
    @ArchTest
    static final ArchRule dominio_deve_ser_agnostico_a_sql =
            noClasses()
                    .that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat().resideInAPackage("java.sql..");
}
