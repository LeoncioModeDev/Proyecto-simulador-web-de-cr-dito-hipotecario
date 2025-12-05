export interface SimulacionCreditoDTO {
    simulacion_id: number;
    cuota_inicial_pct: number;
    cuota_inicial_monto: number;
    plazo_meses: number;
    gastos_adicionales: number;
    tasa_anual: number;
    capitalizacion_m: number;
    meses_gracia: number;
    bfh_aplica: boolean;
    bfh_monto: number;
    monto_credito: number;
    tasa_efectiva_mensual: number;
    estado: string;
    fecha_simulacion: string | Date | any;  // Más flexible para manejar diferentes formatos
}