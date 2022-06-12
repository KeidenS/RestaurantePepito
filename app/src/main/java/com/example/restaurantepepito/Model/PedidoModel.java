package com.example.restaurantepepito.Model;

public class PedidoModel {

    private Integer idpedido;

    private String hora_inicio;

    private String hora_final;

    private String fecha;

    private Double total;

    private String estado;

    private Double precio_entrega;

    private Integer idtipo_pedido;

    private Integer idrestaurante;

    private Integer iddireccion;


    private String fecha_pedido;

    private String nombre;

    private String plazo;

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(String fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdpedido() {
        return idpedido;
    }
    public void setIdpedido(Integer idpedido) {
        this.idpedido = idpedido;
    }
    public String getHora_inicio() {
        return hora_inicio;
    }
    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }
    public String getHora_final() {
        return hora_final;
    }
    public void setHora_final(String hora_final) {
        this.hora_final = hora_final;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Double getPrecio_entrega() {
        return precio_entrega;
    }
    public void setPrecio_entrega(Double precio_entrega) {
        this.precio_entrega = precio_entrega;
    }
    public Integer getIdtipo_pedido() {
        return idtipo_pedido;
    }
    public void setIdtipo_pedido(Integer idtipo_pedido) {
        this.idtipo_pedido = idtipo_pedido;
    }
    public Integer getIdrestaurante() {
        return idrestaurante;
    }
    public void setIdrestaurante(Integer idrestaurante) {
        this.idrestaurante = idrestaurante;
    }
    public Integer getIddireccion() {
        return iddireccion;
    }
    public void setIddireccion(Integer iddireccion) {
        this.iddireccion = iddireccion;
    }
    public PedidoModel(Integer idpedido, String hora_inicio, String hora_final, String fecha, Double total, String estado,
                  Double precio_entrega, Integer idtipo_pedido, Integer idrestaurante, Integer iddireccion) {
        super();
        this.idpedido = idpedido;
        this.hora_inicio = hora_inicio;
        this.hora_final = hora_final;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.precio_entrega = precio_entrega;
        this.idtipo_pedido = idtipo_pedido;
        this.idrestaurante = idrestaurante;
        this.iddireccion = iddireccion;
    }
    public PedidoModel(String hora_inicio, String hora_final, String fecha, Double total, String estado,
                  Double precio_entrega, Integer idtipo_pedido, Integer idrestaurante, Integer iddireccion) {
        super();
        this.hora_inicio = hora_inicio;
        this.hora_final = hora_final;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.precio_entrega = precio_entrega;
        this.idtipo_pedido = idtipo_pedido;
        this.idrestaurante = idrestaurante;
        this.iddireccion = iddireccion;
    }
    public PedidoModel() {
        super();
        // TODO Auto-generated constructor stub
    }

    public PedidoModel(Integer idpedido, String hora_inicio, String hora_final, String fecha, Double total, String estado, Double precio_entrega, Integer idtipo_pedido, Integer idrestaurante, Integer iddireccion, String fecha_pedido, String nombre, String plazo) {
        this.idpedido = idpedido;
        this.hora_inicio = hora_inicio;
        this.hora_final = hora_final;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.precio_entrega = precio_entrega;
        this.idtipo_pedido = idtipo_pedido;
        this.idrestaurante = idrestaurante;
        this.iddireccion = iddireccion;
        this.fecha_pedido = fecha_pedido;
        this.nombre = nombre;
        this.plazo = plazo;
    }
}
