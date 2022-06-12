package com.example.restaurantepepito.Model;

public class Resultado_Pago {

    private Boolean error;
    private String mensaje;
    public Boolean getError() {
        return error;
    }
    public void setError(Boolean error) {
        this.error = error;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public Resultado_Pago(Boolean error, String mensaje) {
        super();
        this.error = error;
        this.mensaje = mensaje;
    }
    public Resultado_Pago() {
        super();

    }

}
