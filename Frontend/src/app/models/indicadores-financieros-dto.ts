export interface IndicadoresFinancierosDTO {
  van: number;
  tir_mensual: number;
  tir_anual: number;
  tcea_anual: number;

  total_intereses: number;
  total_costos: number;
  total_pagado: number;

  total_amortizacion: number;
  total_seguro_desgravamen: number;
  total_seguro_riesgo: number;
  total_comisiones: number;
  total_portes: number;
  total_gastos_adm: number;

  tasa_descuento_anual: number;
}