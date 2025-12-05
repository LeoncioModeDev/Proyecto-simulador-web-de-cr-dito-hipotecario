export interface PlanPagoDTO {
  periodo: number;
  estado: string;
  saldo_inicial: number;
  interes_periodo: number;
  amortizacion_periodo: number;
  costos_periodo: number;
  cuota_periodo: number;
  saldo_final: number;

  seguro_desgravamen: number;
  seguro_riesgo: number;
  comision_periodica: number;
  portes_periodo: number;
  gastos_adm_periodo: number;
  prepago: number;
}