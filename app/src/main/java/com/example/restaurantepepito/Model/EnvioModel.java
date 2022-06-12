package com.example.restaurantepepito.Model;

public class EnvioModel {


    private Integer identrega;

    private String estado;

    private String nombre;

    private String apellido;
    public Integer getIdentrega() {
        return identrega;
    }
    public void setIdentrega(Integer identrega) {
        this.identrega = identrega;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public EnvioModel(Integer identrega, String estado, String nombre, String apellido) {
        super();
        this.identrega = identrega;
        this.estado = estado;
        this.nombre = nombre;
        this.apellido = apellido;
    }
    public EnvioModel() {
        super();
    }

}
