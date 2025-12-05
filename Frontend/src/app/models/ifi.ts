export interface IFI {
  ifi_id: number;
  nombre: string;
  telefono: string;
  direccion: string;

  tasa_nominal_anual: number;
  tasa_efectiva_anual: number;
  tipo_tasa_base: string;
  capitalizacion_m: number;

  plazo_min_meses: number;
  plazo_max_meses: number;

  porcentaje_cuota_ini_min: number;
  monto_min_credito: number;
  monto_max_credito: number;

  costo_notarial: number;
  costo_registral: number;
  costo_tasacion: number;
  costo_estudio_titulos: number;
  comision_activacion: number;

  comision_periodica_mensual: number;
  tasa_seguro_desgravamen: number;
  tasa_seguro_riesgo: number;

  permite_bfh: boolean;
  monedas_permitidas: string;
}
