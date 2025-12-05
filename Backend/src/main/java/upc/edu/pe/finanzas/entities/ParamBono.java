package upc.edu.pe.finanzas.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="ParamBono")
public class ParamBono {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int param_bono_id;

    @Column(name = "trammo", length = 50, nullable = false)
    private String tramo;

    @Column(name = "ingreso_min", nullable = false)
    private double ingreso_min;

    @Column(name = "ingreso_max", nullable = false)
    private double ingreso_max;

    @Column(name = "precio_max", nullable = false)
    private double precio_max;

    @Column(name = "monto_bono", nullable = false)
    private double monto_bono;

    @Column(name = "vigente_desde", nullable = false)
    private LocalDate vigente_desde;

    @Column(name = "vigente_hasta", nullable = false)
    private LocalDate vigente_hasta;

    public ParamBono() {}

    public ParamBono(int param_bono_id, String tramo, double ingreso_min, double ingreso_max, double precio_max, double monto_bono, LocalDate vigente_desde, LocalDate vigente_hasta) {
        this.param_bono_id = param_bono_id;
        this.tramo = tramo;
        this.ingreso_min = ingreso_min;
        this.ingreso_max = ingreso_max;
        this.precio_max = precio_max;
        this.monto_bono = monto_bono;
        this.vigente_desde = vigente_desde;
        this.vigente_hasta = vigente_hasta;
    }

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
