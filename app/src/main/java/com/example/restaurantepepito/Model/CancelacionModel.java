package com.example.restaurantepepito.Model;

public class CancelacionModel {

    private Integer idcancelacion;

    private String estado;

    private String motivo;

    private Integer idpedido;
    public Integer getIdcancelacion() {
        return idcancelacion;
    }
    public void setIdcancelacion(Integer idcancelacion) {
        this.idcancelacion = idcancelacion;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public Integer getIdpedido() {
        return idpedido;
    }
    public void setIdpedido(Integer idpedido) {
        this.idpedido = idpedido;
    }
    public CancelacionModel(Integer idcancelacion, String estado, String motivo, Integer idpedido) {
        super();
        this.idcancelacion = idcancelacion;
        this.estado = estado;
        this.motivo = motivo;
        this.idpedido = idpedido;
    }
    public CancelacionModel(String estado, String motivo, Integer idpedido) {
        super();
        this.estado = estado;
        this.motivo = motivo;
        this.idpedido = idpedido;
    }
    public CancelacionModel() {
        super();
    }

}
