package upc.edu.pe.finanzas.dtos;

import upc.edu.pe.finanzas.entities.*;

import java.time.LocalDateTime;

public class SimulacionesCreditoDTO {

    private int simulacion_id;
    private double cuota_inicial_pct;
    private double cuota_inicial_monto;
    private int plazo_meses;
    private double gastos_adicionales;
    private double tasa_anual;
    private int capitalizacion_m;
    private int meses_gracia;
    private boolean bfh_aplica;
    private double bfh_monto;
    private double monto_credito;
    private double tasa_efectiva_mensual;
    private String estado;

    private LocalDateTime fecha_simulacion;

    private Clientes clientes;
    private Inmuebles inmuebles;
    private IndicadoresFinancieros indicadoresFinancieros;
    private CatMoneda catMoneda;
    private CatTipoTasa catTipoTasa;
    private CatTipoGracia catTipoGracia;
    private EntidadesFinancieras entidadesFinancieras; // 🔹 nuevo

    public int getSimulacion_id() {
        return simulacion_id;
    }

    public void setSimulacion_id(int simulacion_id) {
        this.simulacion_id = simulacion_id;
    }

    public double getCuota_inicial_pct() {
        return cuota_inicial_pct;
    }

    public void setCuota_inicial_pct(double cuota_inicial_pct) {
        this.cuota_inicial_pct = cuota_inicial_pct;
    }

    public double getCuota_inicial_monto() {
        return cuota_inicial_monto;
    }

    public void setCuota_inicial_monto(double cuota_inicial_monto) {
        this.cuota_inicial_monto = cuota_inicial_monto;
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

    public double getTasa_anual() {
        return tasa_anual;
    }

    public void setTasa_anual(double tasa_anual) {
        this.tasa_anual = tasa_anual;
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

    public boolean isBfh_aplica() {
        return bfh_aplica;
    }

    public void setBfh_aplica(boolean bfh_aplica) {
        this.bfh_aplica = bfh_aplica;
    }

    public double getBfh_monto() {
        return bfh_monto;
    }

    public void setBfh_monto(double bfh_monto) {
        this.bfh_monto = bfh_monto;
    }

    public double getMonto_credito() {
        return monto_credito;
    }

    public void setMonto_credito(double monto_credito) {
        this.monto_credito = monto_credito;
    }

    public double getTasa_efectiva_mensual() {
        return tasa_efectiva_mensual;
    }

    public void setTasa_efectiva_mensual(double tasa_efectiva_mensual) {
        this.tasa_efectiva_mensual = tasa_efectiva_mensual;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha_simulacion() {
        return fecha_simulacion;
    }

    public void setFecha_simulacion(LocalDateTime fecha_simulacion) {
        this.fecha_simulacion = fecha_simulacion;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Inmuebles getInmuebles() {
        return inmuebles;
    }

    public void setInmuebles(Inmuebles inmuebles) {
        this.inmuebles = inmuebles;
    }

    public IndicadoresFinancieros getIndicadoresFinancieros() {
        return indicadoresFinancieros;
    }

    public void setIndicadoresFinancieros(IndicadoresFinancieros indicadoresFinancieros) {
        this.indicadoresFinancieros = indicadoresFinancieros;
    }

    public CatMoneda getCatMoneda() {
        return catMoneda;
    }

    public void setCatMoneda(CatMoneda catMoneda) {
        this.catMoneda = catMoneda;
    }

    public CatTipoTasa getCatTipoTasa() {
        return catTipoTasa;
    }

    public void setCatTipoTasa(CatTipoTasa catTipoTasa) {
        this.catTipoTasa = catTipoTasa;
    }

    public CatTipoGracia getCatTipoGracia() {
        return catTipoGracia;
    }

    public void setCatTipoGracia(CatTipoGracia catTipoGracia) {
        this.catTipoGracia = catTipoGracia;
    }

    public EntidadesFinancieras getEntidadesFinancieras() {
        return entidadesFinancieras;
    }

    public void setEntidadesFinancieras(EntidadesFinancieras entidadesFinancieras) {
        this.entidadesFinancieras = entidadesFinancieras;
    }
}
