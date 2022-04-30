package com.example.restaurantepepito.Model;

public class PlatoModel {


    private Integer idplato;


    private String nombre;


    private Double precio;


    private String descripcion;


    private String imagen;


    private String tipo_plato;

    public PlatoModel(Integer idplato, String nombre, Double precio, String descripcion, String imagen, String tipo_plato) {
        this.idplato = idplato;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.tipo_plato = tipo_plato;
    }

    public Integer getIdplato() {
        return idplato;
    }

    public void setIdplato(Integer idplato) {
        this.idplato = idplato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTipo_plato() {
        return tipo_plato;
    }

    public void setTipo_plato(String tipo_plato) {
        this.tipo_plato = tipo_plato;
    }

    public PlatoModel() {
    }
}
