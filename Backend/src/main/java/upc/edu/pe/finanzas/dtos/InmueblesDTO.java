package upc.edu.pe.finanzas.dtos;

public class InmueblesDTO {

    private int inmueble_id;

    private String nombre_proyecto;

    private String unidad;

    private String ubicacion_general;

    private String ubicacion_especifica;

    private double area_m2;

    private String ciudad;

    private double precio;

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
