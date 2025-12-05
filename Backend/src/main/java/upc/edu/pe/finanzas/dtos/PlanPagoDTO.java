package upc.edu.pe.finanzas.dtos;

public class PlanPagoDTO {

    private int periodo;
    private String estado;
    private double saldo_inicial;
    private double interes_periodo;
    private double amortizacion_periodo;
    private double costos_periodo;
    private double cuota_periodo;
    private double saldo_final;

    private double seguro_desgravamen;
    private double seguro_riesgo;
    private double comision_periodica;
    private double portes_periodo;
    private double gastos_adm_periodo;
    private double prepago;

    public PlanPagoDTO() {
    }

    public PlanPagoDTO(int periodo,
                       String estado,
                       double saldo_inicial,
                       double interes_periodo,
                       double amortizacion_periodo,
                       double costos_periodo,
                       double cuota_periodo,
                       double saldo_final,
                       double seguro_desgravamen,
                       double seguro_riesgo,
                       double comision_periodica,
                       double portes_periodo,
                       double gastos_adm_periodo,
                       double prepago) {
        this.periodo = periodo;
        this.estado = estado;
        this.saldo_inicial = saldo_inicial;
        this.interes_periodo = interes_periodo;
        this.amortizacion_periodo = amortizacion_periodo;
        this.costos_periodo = costos_periodo;
        this.cuota_periodo = cuota_periodo;
        this.saldo_final = saldo_final;
        this.seguro_desgravamen = seguro_desgravamen;
        this.seguro_riesgo = seguro_riesgo;
        this.comision_periodica = comision_periodica;
        this.portes_periodo = portes_periodo;
        this.gastos_adm_periodo = gastos_adm_periodo;
        this.prepago = prepago;
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

    public double getSeguro_desgravamen() {
        return seguro_desgravamen;
    }

    public void setSeguro_desgravamen(double seguro_desgravamen) {
        this.seguro_desgravamen = seguro_desgravamen;
    }

    public double getSeguro_riesgo() {
        return seguro_riesgo;
    }

    public void setSeguro_riesgo(double seguro_riesgo) {
        this.seguro_riesgo = seguro_riesgo;
    }

    public double getComision_periodica() {
        return comision_periodica;
    }

    public void setComision_periodica(double comision_periodica) {
        this.comision_periodica = comision_periodica;
    }

    public double getPortes_periodo() {
        return portes_periodo;
    }

    public void setPortes_periodo(double portes_periodo) {
        this.portes_periodo = portes_periodo;
    }

    public double getGastos_adm_periodo() {
        return gastos_adm_periodo;
    }

    public void setGastos_adm_periodo(double gastos_adm_periodo) {
        this.gastos_adm_periodo = gastos_adm_periodo;
    }

    public double getPrepago() {
        return prepago;
    }

    public void setPrepago(double prepago) {
        this.prepago = prepago;
    }
}