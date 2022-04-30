package com.example.restaurantepepito.Model;

public class Tipo_PedidoModel {

    private Integer idtipo_pedido;


    private String nombre;


    private String descripcion;


    private String plazo;

    public Integer getIdtipo_pedido() {
        return idtipo_pedido;
    }

    public void setIdtipo_pedido(Integer idtipo_pedido) {
        this.idtipo_pedido = idtipo_pedido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public Tipo_PedidoModel(Integer idtipo_pedido, String nombre, String descripcion, String plazo) {
        super();
        this.idtipo_pedido = idtipo_pedido;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.plazo = plazo;
    }

    public Tipo_PedidoModel() {
        super();

    }
}
