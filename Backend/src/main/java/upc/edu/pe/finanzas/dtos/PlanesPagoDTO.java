package upc.edu.pe.finanzas.dtos;

import upc.edu.pe.finanzas.entities.SimulacionesCredito;

public class PlanesPagoDTO {

    private int plan_id;

    private int periodo;

    private String estado;

    private double saldo_inicial;

    private double interes_periodo;

    private double amortizacion_periodo;

    private double costos_periodo;

    private double cuota_periodo;

    private double saldo_final;

    private SimulacionesCredito simulacionesCredito;

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getSaldo_inicial() {
        return saldo_inicial;
    }

    public void setSaldo_inicial(double saldo_inicial) {
        this.saldo_inicial = saldo_inicial;
    }

    public double getInteres_periodo() {
        return interes_periodo;
    }

    public void setInteres_periodo(double interes_periodo) {
        this.interes_periodo = interes_periodo;
    }

    public double getAmortizacion_periodo() {
        return amortizacion_periodo;
    }

    public void setAmortizacion_periodo(double amortizacion_periodo) {
        this.amortizacion_periodo = amortizacion_periodo;
    }

    public double getCostos_periodo() {
        return costos_periodo;
    }

    public void setCostos_periodo(double costos_periodo) {
        this.costos_periodo = costos_periodo;
    }

    public double getCuota_periodo() {
        return cuota_periodo;
    }

    public void setCuota_periodo(double cuota_periodo) {
        this.cuota_periodo = cuota_periodo;
    }

    public double getSaldo_final() {
        return saldo_final;
    }

    public void setSaldo_final(double saldo_final) {
        this.saldo_final = saldo_final;
    }

    public SimulacionesCredito getSimulacionesCredito() {
        return simulacionesCredito;
    }

    public void setSimulacionesCredito(SimulacionesCredito simulacionesCredito) {
        this.simulacionesCredito = simulacionesCredito;
    }
}
