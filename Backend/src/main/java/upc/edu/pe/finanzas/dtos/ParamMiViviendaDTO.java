package upc.edu.pe.finanzas.dtos;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class ParamMiViviendaDTO {

    private int param_mivivienda_id;

    private String descripcion;

    private double cuota_ini_min_pct;

    private int plazo_min_meses;

    private int plazo_max_meses;

    private double precio_max_vis;

    private double precio_max_no_vis;

    private double ingreso_max_familiar;

    private LocalDate vigente_desde;

    private LocalDate vigente_hasta;

    public int getParam_mivivienda_id() {
        return param_mivivienda_id;
    }

    public void setParam_mivivienda_id(int param_mivivienda_id) {
        this.param_mivivienda_id = param_mivivienda_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCuota_ini_min_pct() {
        return cuota_ini_min_pct;
    }

    public void setCuota_ini_min_pct(double cuota_ini_min_pct) {
        this.cuota_ini_min_pct = cuota_ini_min_pct;
    }

    public int getPlazo_min_meses() {
        return plazo_min_meses;
    }

    public void setPlazo_min_meses(int plazo_min_meses) {
        this.plazo_min_meses = plazo_min_meses;
    }

    public int getPlazo_max_meses() {
        return plazo_max_meses;
    }

    public void setPlazo_max_meses(int plazo_max_meses) {
        this.plazo_max_meses = plazo_max_meses;
    }

    public double getPrecio_max_vis() {
        return precio_max_vis;
    }

    public void setPrecio_max_vis(double precio_max_vis) {
        this.precio_max_vis = precio_max_vis;
    }

    public double getPrecio_max_no_vis() {
        return precio_max_no_vis;
    }

    public void setPrecio_max_no_vis(double precio_max_no_vis) {
        this.precio_max_no_vis = precio_max_no_vis;
    }

    public double getIngreso_max_familiar() {
        return ingreso_max_familiar;
    }

    public void setIngreso_max_familiar(double ingreso_max_familiar) {
        this.ingreso_max_familiar = ingreso_max_familiar;
    }

    public LocalDate getVigente_desde() {
        return vigente_desde;
    }

    public void setVigente_desde(LocalDate vigente_desde) {
        this.vigente_desde = vigente_desde;
    }

    public LocalDate getVigente_hasta() {
        return vigente_hasta;
    }

    public void setVigente_hasta(LocalDate vigente_hasta) {
        this.vigente_hasta = vigente_hasta;
    }
}
