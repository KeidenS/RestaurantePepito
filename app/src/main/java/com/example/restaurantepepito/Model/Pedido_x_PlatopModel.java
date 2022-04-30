package com.example.restaurantepepito.Model;

public class Pedido_x_PlatopModel {

    Integer id_pedido;
    String nombre;
    Double precio;
    Integer cantidad;
    Double subtotal;
    Integer id_plato;
    Double total;

    public Pedido_x_PlatopModel(Double total) {
        this.total = total;
    }

    public Pedido_x_PlatopModel(Integer cantidad, Double subtotal, Integer id_pedido,Integer id_plato) {
        this.id_pedido = id_pedido;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.id_plato = id_plato;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Integer id_pedido) {
        this.id_pedido = id_pedido;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getId_plato() {
        return id_plato;
    }

    public void setId_plato(Integer id_plato) {
        this.id_plato = id_plato;
    }

    public Pedido_x_PlatopModel(Integer id_pedido, String nombre, Double precio, Integer cantidad, Double subtotal, Integer id_plato) {
        this.id_pedido = id_pedido;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.id_plato = id_plato;
    }

    public Pedido_x_PlatopModel() {
    }

}
