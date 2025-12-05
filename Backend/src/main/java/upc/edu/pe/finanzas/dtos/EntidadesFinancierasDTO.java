package upc.edu.pe.finanzas.dtos;

public class EntidadesFinancierasDTO {
    private int ifi_id;
    private String nombre;
    private String telefono;
    private String direccion;

    private double tasa_nominal_anual;
    private double tasa_efectiva_anual;
    private String tipo_tasa_base;
    private int capitalizacion_m;

    // Rangos de crédito
    private int plazo_min_meses;
    private int plazo_max_meses;
    private double porcentaje_cuota_ini_min;
    private double monto_min_credito;
    private double monto_max_credito;

    // Costos iniciales
    private double costo_notarial;
    private double costo_registral;
    private double costo_tasacion;
    private double costo_estudio_titulos;
    private double comision_activacion;

    // Costos periódicos
    private double comision_periodica_mensual;
    private Double portes_mensuales;
    private Double gastos_adm_mensuales;

    // Seguros (ambos como tasas anuales en %)
    private double tasa_seguro_desgravamen;
    private double tasa_seguro_riesgo;

    // Otros
    private boolean permite_bfh;
    private String monedas_permitidas;
    private Double tasa_descuento_anual;

    // Getters y Setters

    public int getIfi_id() {
        return ifi_id;
    }

    public void setIfi_id(int ifi_id) {
        this.ifi_id = ifi_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getTasa_nominal_anual() {
        return tasa_nominal_anual;
    }

    public void setTasa_nominal_anual(double tasa_nominal_anual) {
        this.tasa_nominal_anual = tasa_nominal_anual;
    }

    public double getTasa_efectiva_anual() {
        return tasa_efectiva_anual;
    }

    public void setTasa_efectiva_anual(double tasa_efectiva_anual) {
        this.tasa_efectiva_anual = tasa_efectiva_anual;
    }

    public String getTipo_tasa_base() {
        return tipo_tasa_base;
    }

    public void setTipo_tasa_base(String tipo_tasa_base) {
        this.tipo_tasa_base = tipo_tasa_base;
    }

    public int getCapitalizacion_m() {
        return capitalizacion_m;
    }

    public void setCapitalizacion_m(int capitalizacion_m) {
        this.capitalizacion_m = capitalizacion_m;
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

    public double getPorcentaje_cuota_ini_min() {
        return porcentaje_cuota_ini_min;
    }

    public void setPorcentaje_cuota_ini_min(double porcentaje_cuota_ini_min) {
        this.porcentaje_cuota_ini_min = porcentaje_cuota_ini_min;
    }

    public double getMonto_min_credito() {
        return monto_min_credito;
    }

    public void setMonto_min_credito(double monto_min_credito) {
        this.monto_min_credito = monto_min_credito;
    }

    public double getMonto_max_credito() {
        return monto_max_credito;
    }

    public void setMonto_max_credito(double monto_max_credito) {
        this.monto_max_credito = monto_max_credito;
    }

    public double getCosto_notarial() {
        return costo_notarial;
    }

    public void setCosto_notarial(double costo_notarial) {
        this.costo_notarial = costo_notarial;
    }

    public double getCosto_registral() {
        return costo_registral;
    }

    public void setCosto_registral(double costo_registral) {
        this.costo_registral = costo_registral;
    }

    public double getCosto_tasacion() {
        return costo_tasacion;
    }

    public void setCosto_tasacion(double costo_tasacion) {
        this.costo_tasacion = costo_tasacion;
    }

    public double getCosto_estudio_titulos() {
        return costo_estudio_titulos;
    }

    public void setCosto_estudio_titulos(double costo_estudio_titulos) {
        this.costo_estudio_titulos = costo_estudio_titulos;
    }

    public double getComision_activacion() {
        return comision_activacion;
    }

    public void setComision_activacion(double comision_activacion) {
        this.comision_activacion = comision_activacion;
    }

    public double getComision_periodica_mensual() {
        return comision_periodica_mensual;
    }

    public void setComision_periodica_mensual(double comision_periodica_mensual) {
        this.comision_periodica_mensual = comision_periodica_mensual;
    }

    public double getTasa_seguro_desgravamen() {
        return tasa_seguro_desgravamen;
    }

    public void setTasa_seguro_desgravamen(double tasa_seguro_desgravamen) {
        this.tasa_seguro_desgravamen = tasa_seguro_desgravamen;
    }

    public double getTasa_seguro_riesgo() {
        return tasa_seguro_riesgo;
    }

    public void setTasa_seguro_riesgo(double tasa_seguro_riesgo) {
        this.tasa_seguro_riesgo = tasa_seguro_riesgo;
    }

    public boolean isPermite_bfh() {
        return permite_bfh;
    }

    public void setPermite_bfh(boolean permite_bfh) {
        this.permite_bfh = permite_bfh;
    }

    public String getMonedas_permitidas() {
        return monedas_permitidas;
    }

    public void setMonedas_permitidas(String monedas_permitidas) {
        this.monedas_permitidas = monedas_permitidas;
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

    public Double getTasa_descuento_anual() {
        return tasa_descuento_anual;
    }

    public void setTasa_descuento_anual(Double tasa_descuento_anual) {
        this.tasa_descuento_anual = tasa_descuento_anual;
    }
}