package upc.edu.pe.finanzas.dtos;

public class SimulacionCreditoRequest {
    private int cliente_id;
    private int inmueble_id;
    private int ifi_id;

    private int moneda_id;
    private int tipo_tasa_id;
    private int tipo_gracia_id;

    private double cuota_inicial_pct;
    private int plazo_meses;
    private double gastos_adicionales;
    private double tasa_anual_ingresada;

    private int capitalizacion_m;
    private int meses_gracia;

    private boolean aplica_bfh;
    private Double bfh_monto;

    // Costos iniciales
    private Double costo_notarial;
    private Double costo_registral;
    private Double costo_tasacion;
    private Double costo_estudio_titulos;
    private Double comision_activacion;

    // Costos periódicos
    private Double comision_periodica_mensual;
    private Double portes_mensuales;
    private Double gastos_adm_mensuales;

    // ✅ CORREGIDO: Ambos seguros son tasas anuales en %
    private Double tasa_seguro_desgravamen;  // % anual sobre saldo
    private Double tasa_seguro_riesgo;       // % anual sobre precio del bien

    public SimulacionCreditoRequest() {}

    // Getters y Setters

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public int getInmueble_id() {
        return inmueble_id;
    }

    public void setInmueble_id(int inmueble_id) {
        this.inmueble_id = inmueble_id;
    }

    public int getIfi_id() {
        return ifi_id;
    }

    public void setIfi_id(int ifi_id) {
        this.ifi_id = ifi_id;
    }

    public int getMoneda_id() {
        return moneda_id;
    }

    public void setMoneda_id(int moneda_id) {
        this.moneda_id = moneda_id;
    }

    public int getTipo_tasa_id() {
        return tipo_tasa_id;
    }

    public void setTipo_tasa_id(int tipo_tasa_id) {
        this.tipo_tasa_id = tipo_tasa_id;
    }

    public int getTipo_gracia_id() {
        return tipo_gracia_id;
    }

    public void setTipo_gracia_id(int tipo_gracia_id) {
        this.tipo_gracia_id = tipo_gracia_id;
    }

    public double getCuota_inicial_pct() {
        return cuota_inicial_pct;
    }

    public void setCuota_inicial_pct(double cuota_inicial_pct) {
        this.cuota_inicial_pct = cuota_inicial_pct;
    }

    public int getPlazo_meses() {
        return plazo_meses;
    }

    public void setPlazo_meses(int plazo_meses) {
        this.plazo_meses = plazo_meses;
    }

    public double getGastos_adicionales() {
        return gastos_adicionales;
    }

    public void setGastos_adicionales(double gastos_adicionales) {
        this.gastos_adicionales = gastos_adicionales;
    }

    public double getTasa_anual_ingresada() {
        return tasa_anual_ingresada;
    }

    public void setTasa_anual_ingresada(double tasa_anual_ingresada) {
        this.tasa_anual_ingresada = tasa_anual_ingresada;
    }

    public int getCapitalizacion_m() {
        return capitalizacion_m;
    }

    public void setCapitalizacion_m(int capitalizacion_m) {
        this.capitalizacion_m = capitalizacion_m;
    }

    public int getMeses_gracia() {
        return meses_gracia;
    }

    public void setMeses_gracia(int meses_gracia) {
        this.meses_gracia = meses_gracia;
    }

    public boolean isAplica_bfh() {
        return aplica_bfh;
    }

    public void setAplica_bfh(boolean aplica_bfh) {
        this.aplica_bfh = aplica_bfh;
    }

    public Double getBfh_monto() {
        return bfh_monto;
    }

    public void setBfh_monto(Double bfh_monto) {
        this.bfh_monto = bfh_monto;
    }

    public Double getCosto_notarial() {
        return costo_notarial;
    }

    public void setCosto_notarial(Double costo_notarial) {
        this.costo_notarial = costo_notarial;
    }

    public Double getCosto_registral() {
        return costo_registral;
    }

    public void setCosto_registral(Double costo_registral) {
        this.costo_registral = costo_registral;
    }

    public Double getCosto_tasacion() {
        return costo_tasacion;
    }

    public void setCosto_tasacion(Double costo_tasacion) {
        this.costo_tasacion = costo_tasacion;
    }

    public Double getCosto_estudio_titulos() {
        return costo_estudio_titulos;
    }

    public void setCosto_estudio_titulos(Double costo_estudio_titulos) {
        this.costo_estudio_titulos = costo_estudio_titulos;
    }

    public Double getComision_activacion() {
        return comision_activacion;
    }

    public void setComision_activacion(Double comision_activacion) {
        this.comision_activacion = comision_activacion;
    }

    public Double getComision_periodica_mensual() {
        return comision_periodica_mensual;
    }

    public void setComision_periodica_mensual(Double comision_periodica_mensual) {
        this.comision_periodica_mensual = comision_periodica_mensual;
    }

    public Double getPortes_mensuales() {
        return portes_mensuales;
    }

    public void setPortes_mensuales(Double portes_mensuales) {
        this.portes_mensuales = portes_mensuales;
    }

    public Double getGastos_adm_mensuales() {
        return gastos_adm_mensuales;
    }

    public void setGastos_adm_mensuales(Double gastos_adm_mensuales) {
        this.gastos_adm_mensuales = gastos_adm_mensuales;
    }

    public Double getTasa_seguro_desgravamen() {
        return tasa_seguro_desgravamen;
    }

    public void setTasa_seguro_desgravamen(Double tasa_seguro_desgravamen) {
        this.tasa_seguro_desgravamen = tasa_seguro_desgravamen;
    }

    // ✅ CORREGIDO: Cambio de nombre del método
    public Double getTasa_seguro_riesgo() {
        return tasa_seguro_riesgo;
    }

    public void setTasa_seguro_riesgo(Double tasa_seguro_riesgo) {
        this.tasa_seguro_riesgo = tasa_seguro_riesgo;
    }
}