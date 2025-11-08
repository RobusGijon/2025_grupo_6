package util;

import vista.IOptionPane;

public class FalsoOptionPanel implements IOptionPane {
    private String mensaje;

    public FalsoOptionPanel() {
        super();
        this.mensaje = null;
    }

    public String getMensaje() {
        return mensaje;
    }

    @Override
    public void ShowMessage(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
