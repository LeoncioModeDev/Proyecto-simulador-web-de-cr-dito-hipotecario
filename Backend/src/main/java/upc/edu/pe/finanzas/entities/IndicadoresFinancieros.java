package upc.edu.pe.finanzas.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "IndicadoresFinancieros")
public class IndicadoresFinancieros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int indicadores_id;

    @Column(name = "van", nullable = false)
    private double van;

    @Column(name = "tir_mensual", nullable = false)
    private double tir_mensual;

    @Column(name = "tir_anual", nullable = false)
    private double tir_anual;

    @Column(name = "tcea_anual", nullable = false)
    private double tcea_anual;

    @Column(name = "total_intereses", nullable = false)
    private double total_intereses;

    @Column(name = "total_costos", nullable = false)
    private double total_costos;

    @Column(name = "total_pagado", nullable = false)
    private double total_pagado;

    @Column(name = "total_amortizacion", nullable = false)
    private double total_amortizacion;

    @Column(name = "total_seguro_desgravamen", nullable = false)
    private double total_seguro_desgravamen;

    @Column(name = "total_seguro_riesgo", nullable = false)
    private double total_seguro_riesgo;

    @Column(name = "total_comisiones", nullable = false)
    private double total_comisiones;

    @Column(name = "total_portes", nullable = false)
    private double total_portes;

    @Column(name = "total_gastos_adm", nullable = false)
    private double total_gastos_adm;

    @Column(name = "tasa_descuento_anual", nullable = false)
    private double tasa_descuento_anual;

    @OneToOne
    @JoinColumn(name = "simulacion_id")
    private SimulacionesCredito simulacionesCredito;

    public IndicadoresFinancieros() {}

    public IndicadoresFinancieros(int indicadores_id, double van, double tir_mensual, double tir_anual, double tcea_anual, double total_intereses, double total_costos, double total_pagado, double total_amortizacion, double total_seguro_desgravamen, double total_seguro_riesgo, double total_comisiones, double total_portes, double total_gastos_adm, double tasa_descuento_anual, SimulacionesCredito simulacionesCredito) {
        this.indicadores_id = indicadores_id;
        this.van = van;
        this.tir_mensual = tir_mensual;
        this.tir_anual = tir_anual;
        this.tcea_anual = tcea_anual;
        this.total_intereses = total_intereses;
        this.total_costos = total_costos;
        this.total_pagado = total_pagado;
        this.total_amortizacion = total_amortizacion;
        this.total_seguro_desgravamen = total_seguro_desgravamen;
        this.total_seguro_riesgo = total_seguro_riesgo;
        this.total_comisiones = total_comisiones;
        this.total_portes = total_portes;
        this.total_gastos_adm = total_gastos_adm;
        this.tasa_descuento_anual = tasa_descuento_anual;
        this.simulacionesCredito = simulacionesCredito;
    }


    public int getIndicadores_id() {
        return indicadores_id;
    }

    public void setIndicadores_id(int indicadores_id) {
        this.indicadores_id = indicadores_id;
    }

    public double getVan() {
        return van;
    }

    public void setVan(double van) {
        this.van = van;
    }

    public double getTir_mensual() {
        return tir_mensual;
    }

    public void setTir_mensual(double tir_mensual) {
        this.tir_mensual = tir_mensual;
    }

    public double getTir_anual() {
        return tir_anual;
    }

    public void setTir_anual(double tir_anual) {
        this.tir_anual = tir_anual;
    }

    public double getTcea_anual() {
        return tcea_anual;
    }

    public void setTcea_anual(double tcea_anual) {
        this.tcea_anual = tcea_anual;
    }

    public double getTotal_intereses() {
        return total_intereses;
    }

    public void setTotal_intereses(double total_intereses) {
        this.total_intereses = total_intereses;
    }

    public double getTotal_costos() {
        return total_costos;
    }

    public void setTotal_costos(double total_costos) {
        this.total_costos = total_costos;
    }

    public double getTotal_pagado() {
        return total_pagado;
    }

    public void setTotal_pagado(double total_pagado) {
        this.total_pagado = total_pagado;
    }

    public SimulacionesCredito getSimulacionesCredito() {
        return simulacionesCredito;
    }

    public void setSimulacionesCredito(SimulacionesCredito simulacionesCredito) {
        this.simulacionesCredito = simulacionesCredito;
    }

    public double getTotal_amortizacion() {
        return total_amortizacion;
    }

    public void setTotal_amortizacion(double total_amortizacion) {
        this.total_amortizacion = total_amortizacion;
    }

    public double getTotal_seguro_desgravamen() {
        return total_seguro_desgravamen;
    }

    public void setTotal_seguro_desgravamen(double total_seguro_desgravamen) {
        this.total_seguro_desgravamen = total_seguro_desgravamen;
    }

    public double getTotal_seguro_riesgo() {
        return total_seguro_riesgo;
    }

    public void setTotal_seguro_riesgo(double total_seguro_riesgo) {
        this.total_seguro_riesgo = total_seguro_riesgo;
    }

    public double getTotal_comisiones() {
        return total_comisiones;
    }

    public void setTotal_comisiones(double total_comisiones) {
        this.total_comisiones = total_comisiones;
    }

    public double getTotal_portes() {
        return total_portes;
    }

    public void setTotal_portes(double total_portes) {
        this.total_portes = total_portes;
    }

    public double getTotal_gastos_adm() {
        return total_gastos_adm;
    }

    public void setTotal_gastos_adm(double total_gastos_adm) {
        this.total_gastos_adm = total_gastos_adm;
    }

    public double getTasa_descuento_anual() {
        return tasa_descuento_anual;
    }

    public void setTasa_descuento_anual(double tasa_descuento_anual) {
        this.tasa_descuento_anual = tasa_descuento_anual;
    }
}
