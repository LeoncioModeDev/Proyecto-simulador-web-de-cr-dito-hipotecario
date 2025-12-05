package upc.edu.pe.finanzas.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Inmuebles")
public class Inmuebles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int inmueble_id;

    @Column(name = "nombre_proyecto", length = 100, nullable = false)
    private String nombre_proyecto;

    @Column(name = "unidad", length = 50, nullable = false)
    private String unidad;

    @Column(name = "ubicacion_general", length = 100, nullable = false)
    private String ubicacion_general;

    @Column(name = "ubicacion_especifica", length = 100, nullable = false)
    private String ubicacion_especifica;

    @Column(name = "area_m2", nullable = false)
    private double area_m2;

    @Column(name = "ciudad", length = 100, nullable = false)
    private String ciudad;

    @Column(name = "precio", nullable = false)
    private double precio;

    public Inmuebles() {}

    public Inmuebles(int inmueble_id, String nombre_proyecto, String unidad, String ubicacion_general,  String ubicacion_especifica, double area_m2, String ciudad, double precio) {
        this.inmueble_id = inmueble_id;
        this.nombre_proyecto = nombre_proyecto;
        this.unidad = unidad;
        this.ubicacion_general = ubicacion_general;
        this.ubicacion_especifica = ubicacion_especifica;
        this.area_m2 = area_m2;
        this.ciudad = ciudad;
        this.precio = precio;
    }

    public int getInmueble_id() {
        return inmueble_id;
    }

    public void setInmueble_id(int inmueble_id) {
        this.inmueble_id = inmueble_id;
    }

    public String getNombre_proyecto() {
        return nombre_proyecto;
    }

    public void setNombre_proyecto(String nombre_proyecto) {
        this.nombre_proyecto = nombre_proyecto;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getUbicacion_general() {
        return ubicacion_general;
    }

    public void setUbicacion_general(String ubicacion_general) {
        this.ubicacion_general = ubicacion_general;
    }

    public String getUbicacion_especifica() {
        return ubicacion_especifica;
    }

    public void setUbicacion_especifica(String ubicacion_especifica) {
        this.ubicacion_especifica = ubicacion_especifica;
    }

    public double getArea_m2() {
        return area_m2;
    }

    public void setArea_m2(double area_m2) {
        this.area_m2 = area_m2;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
