package upc.edu.pe.finanzas.dtos;

import java.util.List;

public class SimulacionCreditoResponse {
    private SimulacionCreditoDTO simulacion;
    private IndicadoresFinancierosDTO indicadores;
    private List<PlanPagoDTO> planPago;

    public SimulacionCreditoResponse() {}

    public SimulacionCreditoResponse(SimulacionCreditoDTO simulacion, IndicadoresFinancierosDTO indicadores, List<PlanPagoDTO> planPago) {
        this.simulacion = simulacion;
        this.indicadores = indicadores;
        this.planPago = planPago;
    }

    public SimulacionCreditoDTO getSimulacion() {
        return simulacion;
    }

    public void setSimulacion(SimulacionCreditoDTO simulacion) {
        this.simulacion = simulacion;
    }

    public IndicadoresFinancierosDTO getIndicadores() {
        return indicadores;
    }

    public void setIndicadores(IndicadoresFinancierosDTO indicadores) {
        this.indicadores = indicadores;
    }

    public List<PlanPagoDTO> getPlanPago() {
        return planPago;
    }

    public void setPlanPago(List<PlanPagoDTO> planPago) {
        this.planPago = planPago;
    }
}
