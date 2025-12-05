package upc.edu.pe.finanzas.dtos;

public class VerificarCreditoResponse {
    private boolean elegibleCredito;
    private String mensaje;
    private boolean elegibleBfh;  // ✅ NUEVO CAMPO

    // Constructor sin parámetros
    public VerificarCreditoResponse() {}

    // Constructor con elegibleCredito, mensaje y elegibleBfh (NUEVO - principal)
    public VerificarCreditoResponse(boolean elegibleCredito, String mensaje, boolean elegibleBfh) {
        this.elegibleCredito = elegibleCredito;
        this.mensaje = mensaje;
        this.elegibleBfh = elegibleBfh;
    }

    // Constructor antiguo (MANTENER para compatibilidad con código existente)
    public VerificarCreditoResponse(boolean elegibleCredito, String mensaje) {
        this.elegibleCredito = elegibleCredito;
        this.mensaje = mensaje;
        this.elegibleBfh = false;  // Por defecto false si no se especifica
    }

    // Getters y Setters

    public boolean isElegibleCredito() {
        return elegibleCredito;
    }

    public void setElegibleCredito(boolean elegibleCredito) {
        this.elegibleCredito = elegibleCredito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    // ✅ NUEVOS GETTERS Y SETTERS PARA elegibleBfh
    public boolean isElegibleBfh() {
        return elegibleBfh;
    }

    public void setElegibleBfh(boolean elegibleBfh) {
        this.elegibleBfh = elegibleBfh;
    }
}