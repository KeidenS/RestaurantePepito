package com.example.restaurantepepito.Model;

public class PagoModel {

    private String numero_tarjeta;
    private String cvv;
    private String email;
    private String mes;
    private String anio;
    private String codigo_moneda;
    private Integer monto;

    public String getNumero_tarjeta() {
        return numero_tarjeta;
    }
    public void setNumero_tarjeta(String numero_tarjeta) {
        this.numero_tarjeta = numero_tarjeta;
    }
    public String getCvv() {
        return cvv;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMes() {
        return mes;
    }
    public void setMes(String mes) {
        this.mes = mes;
    }
    public String getAnio() {
        return anio;
    }
    public void setAnio(String anio) {
        this.anio = anio;
    }
    public String getCodigo_moneda() {
        return codigo_moneda;
    }
    public void setCodigo_moneda(String codigo_moneda) {
        this.codigo_moneda = codigo_moneda;
    }
    public Integer getMonto() {
        return monto;
    }
    public void setMonto(Integer monto) {
        this.monto = monto;
    }
    public PagoModel(String numero_tarjeta, String cvv, String email, String mes, String anio, String codigo_moneda,
                 Integer monto) {
        super();
        this.numero_tarjeta = numero_tarjeta;
        this.cvv = cvv;
        this.email = email;
        this.mes = mes;
        this.anio = anio;
        this.codigo_moneda = codigo_moneda;
        this.monto = monto;
    }
    public PagoModel() {
        super();
    }




}
