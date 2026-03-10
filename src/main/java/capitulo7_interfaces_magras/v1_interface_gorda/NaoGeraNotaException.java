package capitulo7_interfaces_magras.v1_interface_gorda;

public class NaoGeraNotaException extends RuntimeException {
    public NaoGeraNotaException() {
        super("Este imposto não emite nota fiscal");
    }
}
