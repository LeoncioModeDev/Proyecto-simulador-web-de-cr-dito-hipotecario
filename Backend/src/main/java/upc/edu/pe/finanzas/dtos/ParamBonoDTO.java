package upc.edu.pe.finanzas.dtos;

import java.time.LocalDate;

public class ParamBonoDTO {
    private int param_bono_id;

    private String tramo;

    private double ingreso_min;

    private double ingreso_max;

    private double precio_max;

    private double monto_bono;

    private LocalDate vigente_desde;

    private LocalDate vigente_hasta;

    public int getParam_bono_id() {
        return param_bono_id;
    }

    public void setParam_bono_id(int param_bono_id) {
        this.param_bono_id = param_bono_id;
    }

    public String getTramo() {
        return tramo;
    }

    public void setTramo(String tramo) {
        this.tramo = tramo;
    }

    public double getIngreso_min() {
        return ingreso_min;
    }

    public void setIngreso_min(double ingreso_min) {
        this.ingreso_min = ingreso_min;
    }

    public double getIngreso_max() {
        return ingreso_max;
    }

    public void setIngreso_max(double ingreso_max) {
        this.ingreso_max = ingreso_max;
    }

    public double getPrecio_max() {
        return precio_max;
    }

    public void setPrecio_max(double precio_max) {
        this.precio_max = precio_max;
    }

    public double getMonto_bono() {
        return monto_bono;
    }

    public void setMonto_bono(double monto_bono) {
        this.monto_bono = monto_bono;
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
