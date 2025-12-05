package upc.edu.pe.finanzas.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "EntidadesFinancieras")
public class EntidadesFinancieras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ifi_id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "telefono", length = 100, nullable = false)
    private String telefono;

    @Column(name = "direccion", length = 100, nullable = false)
    private String direccion;

    @Column(name = "tasa_nominal_anual")
    private double tasa_nominal_anual;

    @Column(name = "tasa_efectiva_anual")
    private double tasa_efectiva_anual;

    @Column(name = "tipo_tasa_base", length = 50)
    private String tipo_tasa_base;

    @Column(name = "capitalizacion_m")
    private int capitalizacion_m;

    @Column(name = "plazo_min_meses")
    private int plazo_min_meses;

    @Column(name = "plazo_max_meses")
    private int plazo_max_meses;

    @Column(name = "porcentaje_cuota_ini_min")
    private double porcentaje_cuota_ini_min;

    @Column(name = "monto_min_credito")
    private double monto_min_credito;

    @Column(name = "monto_max_credito")
    private double monto_max_credito;

    // Costos iniciales
    @Column(name = "costo_notarial")
    private double costo_notarial;

    @Column(name = "costo_registral")
    private double costo_registral;

    @Column(name = "costo_tasacion")
    private double costo_tasacion;

    @Column(name = "costo_estudio_titulos")
    private double costo_estudio_titulos;

    @Column(name = "comision_activacion")
    private double comision_activacion;

    // Costos periódicos
    @Column(name = "comision_periodica_mensual")
    private double comision_periodica_mensual;

    @Column(name = "portes_mensuales")
    private Double portes_mensuales;

    @Column(name = "gastos_adm_mensuales")
    private Double gastos_adm_mensuales;

    // Seguros (ambos como tasas anuales en %)
    @Column(name = "tasa_seguro_desgravamen")
    private double tasa_seguro_desgravamen;

    @Column(name = "tasa_seguro_riesgo")
    private Double tasa_seguro_riesgo;  // Ahora puede ser null

    // Otros
    @Column(name = "permite_bfh")
    private boolean permite_bfh;

    @Column(name = "monedas_permitidas", length = 100)
    private String monedas_permitidas;

    @Column(name = "tasa_descuento_anual")
    private Double tasa_descuento_anual;

    public EntidadesFinancieras() {}

    // Constructor completo
    public EntidadesFinancieras(int ifi_id, String nombre, String telefono, String direccion,
                                double tasa_nominal_anual, double tasa_efectiva_anual,
                                String tipo_tasa_base, int capitalizacion_m,
                                int plazo_min_meses, int plazo_max_meses,
                                double porcentaje_cuota_ini_min, double monto_min_credito,
                                double monto_max_credito, double costo_notarial,
                                double costo_registral, double costo_tasacion,
                                double costo_estudio_titulos, double comision_activacion,
                                double comision_periodica_mensual, double tasa_seguro_desgravamen,
                                double tasa_seguro_riesgo, boolean permite_bfh,
                                String monedas_permitidas, Double portes_mensuales,
                                Double gastos_adm_mensuales, Double tasa_descuento_anual) {
        this.ifi_id = ifi_id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.tasa_nominal_anual = tasa_nominal_anual;
        this.tasa_efectiva_anual = tasa_efectiva_anual;
        this.tipo_tasa_base = tipo_tasa_base;
        this.capitalizacion_m = capitalizacion_m;
        this.plazo_min_meses = plazo_min_meses;
        this.plazo_max_meses = plazo_max_meses;
        this.porcentaje_cuota_ini_min = porcentaje_cuota_ini_min;
        this.monto_min_credito = monto_min_credito;
        this.monto_max_credito = monto_max_credito;
        this.costo_notarial = costo_notarial;
        this.costo_registral = costo_registral;
        this.costo_tasacion = costo_tasacion;
        this.costo_estudio_titulos = costo_estudio_titulos;
        this.comision_activacion = comision_activacion;
        this.comision_periodica_mensual = comision_periodica_mensual;
        this.tasa_seguro_desgravamen = tasa_seguro_desgravamen;
        this.tasa_seguro_riesgo = tasa_seguro_riesgo;
        this.permite_bfh = permite_bfh;
        this.monedas_permitidas = monedas_permitidas;
        this.portes_mensuales = portes_mensuales;
        this.gastos_adm_mensuales = gastos_adm_mensuales;
        this.tasa_descuento_anual = tasa_descuento_anual;
    }

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

    public Double getTasa_seguro_riesgo() {
        return tasa_seguro_riesgo;
    }

    public void setTasa_seguro_riesgo(Double tasa_seguro_riesgo) {
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