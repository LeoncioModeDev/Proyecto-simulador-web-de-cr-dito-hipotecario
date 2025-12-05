import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment.development';
import { SimulacionCreditoRequest } from '../models/simulacion-credito-request';
import { SimulacionCreditoResponse } from '../models/simulacion-credito-response';
import { SimulacionCreditoDTO } from '../models/simulacion-credito-dto';

@Injectable({
  providedIn: 'root'
})
export class SimulacionCreditoService {
  private ruta_servidor = environment.apiUrl;
  private recurso = 'simulaciones';

  // 🔹 Subject para compartir datos entre componentes
  private simulacionActualSubject = new BehaviorSubject<SimulacionCreditoResponse | null>(null);
  public simulacionActual$ = this.simulacionActualSubject.asObservable();

  constructor(private http: HttpClient) {}

  // ============================================
  // 🔹 VERIFICAR CRÉDITO
  // ============================================
  verificarCredito(request: SimulacionCreditoRequest): Observable<{
    elegibleCredito: boolean;
    elegibleBfh: boolean;
    mensaje: string;
  }> {
    return this.http.post<{
      elegibleCredito: boolean;
      elegibleBfh: boolean;
      mensaje: string;
    }>(`${this.ruta_servidor}/${this.recurso}/verificar`, request);
  }

  // ============================================
  // 🔹 SIMULAR CRÉDITO (guarda en BD y en memoria)
  // ============================================
  simularCredito(request: SimulacionCreditoRequest): Observable<SimulacionCreditoResponse> {
    return this.http.post<SimulacionCreditoResponse>(
      `${this.ruta_servidor}/${this.recurso}/simular`,
      request
    ).pipe(
      tap(response => {
        // Guardar en el subject para acceso compartido
        this.simulacionActualSubject.next(response);
        console.log('✅ Simulación guardada en el servicio:', response.simulacion.simulacion_id);
      })
    );
  }

  // ============================================
  // 🔹 OBTENER SIMULACIÓN COMPLETA POR ID
  // ============================================
  obtenerSimulacionCompleta(id: number): Observable<SimulacionCreditoResponse> {
    return this.http.get<SimulacionCreditoResponse>(
      `${this.ruta_servidor}/${this.recurso}/${id}/completa`
    ).pipe(
      tap(response => {
        this.simulacionActualSubject.next(response);
        console.log('✅ Simulación cargada desde BD:', id);
      })
    );
  }

  // ============================================
  // 🔹 MÉTODOS AUXILIARES PARA COMPARTIR DATOS
  // ============================================

  /**
   * Establece manualmente la simulación actual
   */
  setSimulacionActual(simulacion: SimulacionCreditoResponse): void {
    this.simulacionActualSubject.next(simulacion);
  }

  /**
   * Obtiene la simulación actual (sincrónico)
   */
  getSimulacionActual(): SimulacionCreditoResponse | null {
    return this.simulacionActualSubject.value;
  }

  /**
   * Limpia la simulación actual de la memoria
   */
  limpiarSimulacionActual(): void {
    this.simulacionActualSubject.next(null);
    console.log('🗑️ Simulación limpiada del servicio');
  }

  // ============================================
  // 🔹 EXPORTAR PLAN DE PAGOS A CSV
  // ============================================

  /**
   * Exporta el plan de pagos actual a CSV
   */
  exportarPlanPagosCSV(simulacionId?: number): void {
    const simulacion = this.simulacionActualSubject.value;
    
    if (!simulacion && !simulacionId) {
      console.error('No hay simulación para exportar');
      return;
    }

    if (simulacionId && !simulacion) {
      // Si solo tenemos ID, cargar primero
      this.obtenerSimulacionCompleta(simulacionId).subscribe({
        next: (response) => {
          this.generarYDescargarCSV(response);
        },
        error: (err) => console.error('Error al cargar simulación para exportar:', err)
      });
    } else if (simulacion) {
      this.generarYDescargarCSV(simulacion);
    }
  }

  /**
   * Genera y descarga el archivo CSV
   */
  private generarYDescargarCSV(response: SimulacionCreditoResponse): void {
    const csv = this.convertirPlanPagosACSV(response);
    const fecha = new Date().toISOString().split('T')[0];
    const nombreArchivo = `plan-pagos-${response.simulacion.simulacion_id}-${fecha}.csv`;
    
    this.descargarCSV(csv, nombreArchivo);
    console.log('📄 CSV descargado:', nombreArchivo);
  }

  /**
   * Convierte el plan de pagos a formato CSV
   */
  private convertirPlanPagosACSV(response: SimulacionCreditoResponse): string {
    // Encabezados
    const headers = [
      'Periodo',
      'Estado',
      'Saldo Inicial',
      'Interés',
      'Amortización',
      'Seguro Desgravamen',
      'Seguro Riesgo',
      'Comisión Periódica',
      'Portes',
      'Gastos Administrativos',
      'Costos Totales',
      'Prepago',
      'Cuota Total',
      'Saldo Final'
    ];

    // Filas de datos
    const rows = response.planPago.map(p => [
      p.periodo,
      p.estado,
      p.saldo_inicial.toFixed(2),
      p.interes_periodo.toFixed(2),
      p.amortizacion_periodo.toFixed(2),
      p.seguro_desgravamen.toFixed(2),
      p.seguro_riesgo.toFixed(2),
      p.comision_periodica.toFixed(2),
      p.portes_periodo.toFixed(2),
      p.gastos_adm_periodo.toFixed(2),
      p.costos_periodo.toFixed(2),
      p.prepago.toFixed(2),
      p.cuota_periodo.toFixed(2),
      p.saldo_final.toFixed(2)
    ]);

    // Información adicional al inicio
    const info = [
      ['PLAN DE PAGOS - SIMULACIÓN DE CRÉDITO'],
      [''],
      ['Simulación ID', response.simulacion.simulacion_id],
      ['Fecha', new Date(response.simulacion.fecha_simulacion).toLocaleDateString()],
      ['Monto Crédito', response.simulacion.monto_credito.toFixed(2)],
      ['Plazo (meses)', response.simulacion.plazo_meses],
      ['TEM', (response.simulacion.tasa_efectiva_mensual * 100).toFixed(4) + '%'],
      ['TCEA', (response.indicadores.tcea_anual * 100).toFixed(2) + '%'],
      [''],
      ['DETALLE DEL PLAN DE PAGOS'],
      ['']
    ];

    // Combinar todo
    return [
      ...info.map(row => row.join(',')),
      headers.join(','),
      ...rows.map(row => row.join(','))
    ].join('\n');
  }

  /**
   * Descarga un string como archivo CSV
   */
  private descargarCSV(contenido: string, nombreArchivo: string): void {
    // Agregar BOM para UTF-8 (soluciona problemas con Excel y caracteres especiales)
    const BOM = '\uFEFF';
    const blob = new Blob([BOM + contenido], { type: 'text/csv;charset=utf-8;' });
    const link = document.createElement('a');
    const url = URL.createObjectURL(blob);
    
    link.setAttribute('href', url);
    link.setAttribute('download', nombreArchivo);
    link.style.visibility = 'hidden';
    
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    
    // Limpiar el objeto URL
    URL.revokeObjectURL(url);
  }

  obtenerUltimas(cantidad: number = 10): Observable<SimulacionCreditoDTO[]> {
    return this.http.get<SimulacionCreditoDTO[]>(
      `${this.ruta_servidor}/${this.recurso}/ultimas?cantidad=${cantidad}`
    );
  }
}