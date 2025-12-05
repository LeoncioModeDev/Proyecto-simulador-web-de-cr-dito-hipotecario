import { SimulacionCreditoDTO } from './simulacion-credito-dto';
import { IndicadoresFinancierosDTO } from './indicadores-financieros-dto';
import { PlanPagoDTO } from './plan-pago-dto';

export interface SimulacionCreditoResponse {
  simulacion: SimulacionCreditoDTO;
  indicadores: IndicadoresFinancierosDTO;
  planPago: PlanPagoDTO[];
}