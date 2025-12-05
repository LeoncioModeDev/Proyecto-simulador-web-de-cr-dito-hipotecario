export interface SimulacionCreditoRequest {
  cliente_id: number;
  inmueble_id: number;
  ifi_id: number;
  moneda_id: number;
  tipo_tasa_id: number;
  tipo_gracia_id: number;

  cuota_inicial_pct: number;
  plazo_meses: number;
  gastos_adicionales: number;

  tasa_anual_ingresada: number;

  capitalizacion_m: number;
  meses_gracia: number;

  aplica_bfh: boolean;
  bfh_monto: number | null;

  // 👇 nuevos campos
  costo_notarial: number;
  costo_registral: number;
  costo_tasacion: number;
  costo_estudio_titulos: number;
  comision_activacion: number;

  comision_periodica_mensual: number;
  portes_mensuales: number;
  gastos_administracion_mensual: number;
  tasa_seguro_desgravamen: number;
  tasa_seguro_riesgo: number;
}
