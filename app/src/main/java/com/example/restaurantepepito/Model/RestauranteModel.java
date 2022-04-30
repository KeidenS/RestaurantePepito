package com.example.restaurantepepito.Model;

public class RestauranteModel{


        private Integer idrestaurante;


        private String nombre;

        private Double longitud;


        private Double latitud;


        private String distrito;


        private String direccion;


        private String localidad;


        private String celular;


        private String correo;

        public RestauranteModel(Integer idrestaurante, String nombre, Double longitud, Double latitud, String distrito, String direccion, String localidad, String celular, String correo) {
            this.idrestaurante = idrestaurante;
            this.nombre = nombre;
            this.longitud = longitud;
            this.latitud = latitud;
            this.distrito = distrito;
            this.direccion = direccion;
            this.localidad = localidad;
            this.celular = celular;
            this.correo = correo;
        }

        public RestauranteModel() {
        }


        public Integer getIdrestaurante() {
            return idrestaurante;
        }

        public void setIdrestaurante(Integer idrestaurante) {
            this.idrestaurante = idrestaurante;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Double getLongitud() {
            return longitud;
        }

        public void setLongitud(Double longitud) {
            this.longitud = longitud;
        }

        public Double getLatitud() {
            return latitud;
        }

        public void setLatitud(Double latitud) {
            this.latitud = latitud;
        }

        public String getDistrito() {
            return distrito;
        }

        public void setDistrito(String distrito) {
            this.distrito = distrito;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getLocalidad() {
            return localidad;
        }

        public void setLocalidad(String localidad) {
            this.localidad = localidad;
        }

        public String getCelular() {
            return celular;
        }

        public void setCelular(String celular) {
            this.celular = celular;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

    public String toString(){
        return nombre + ' ' +distrito + ' '+ localidad + ' '+direccion;
    }

}
