import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../../models/usuario';
import { SimulacionCreditoDTO } from '../../../models/simulacion-credito-dto';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth-service';
import { SimulacionCreditoService } from '../../../services/simulacion-credito-service';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  usuario: Usuario | null = null;
  simulaciones: SimulacionCreditoDTO[] = [];
  cargando = false;
  mensajeError = '';

  constructor(
    private authService: AuthService,
    private simulacionService: SimulacionCreditoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Cargar datos del usuario actual
    this.usuario = this.authService.obtenerUsuarioActual();

    if (!this.usuario) {
      console.warn('No hay usuario logueado');
    }

    // Cargar últimas simulaciones
    this.cargarSimulaciones();
  }

  /**
   * Cargar últimas simulaciones desde el backend
   */
  cargarSimulaciones(): void {
    this.cargando = true;
    this.mensajeError = '';

    this.simulacionService.obtenerUltimas(3).subscribe(
      (response: SimulacionCreditoDTO[]) => {
        console.log('📊 Respuesta del backend:', response);
        
        // Procesar y validar las simulaciones
        this.simulaciones = response.map(sim => ({
          ...sim,
          // Asegurar que fecha_simulacion es un string válido
          fecha_simulacion: typeof sim.fecha_simulacion === 'string' 
            ? sim.fecha_simulacion 
            : new Date(sim.fecha_simulacion).toISOString().split('T')[0]
        }));
        
        this.cargando = false;
        console.log('✅ Simulaciones procesadas:', this.simulaciones);
      },
      (error) => {
        console.error('❌ Error al cargar simulaciones:', error);
        this.mensajeError = 'Error al cargar las simulaciones. Intenta de nuevo.';
        this.cargando = false;
      }
    );
  }

  /**
   * Ver detalles de simulación y navegar al plan de pagos
   */
  verPlanPagos(simulacionId: number): void {
    console.log('🔍 Cargando simulación completa:', simulacionId);
    
    // Cargar la simulación completa
    this.simulacionService.obtenerSimulacionCompleta(simulacionId).subscribe(
      (response) => {
        console.log('✅ Simulación completa cargada:', response);
        
        // Guardar en el servicio
        this.simulacionService.setSimulacionActual(response);
        
        // Navegar al plan de pagos CON EL ID
        this.router.navigate(['/home/simulacion/plan-pagos', simulacionId]);
      },
      (error) => {
        console.error('❌ Error al cargar simulación:', error);
        this.mensajeError = 'Error al cargar los detalles de la simulación';
      }
    );
  }

  /**
   * Formatear moneda
   */
  formatearMoneda(valor: number): string {
    if (!valor || isNaN(valor)) return 'S/. 0.00';
    
    return new Intl.NumberFormat('es-PE', {
      style: 'currency',
      currency: 'PEN'
    }).format(valor);
  }

  /**
   * Formatear porcentaje
   */
  formatearPorcentaje(valor: number): string {
    if (!valor || isNaN(valor)) return '0.00%';
    
    return (valor * 100).toFixed(2) + '%';
  }

  /**
   * Formatear fecha - maneja LocalDateTime del backend
   */
  formatearFecha(fecha: any): string {
    try {
      let fechaObj: Date;
      
      // Caso 1: String ISO (2024-12-03T15:30:00)
      if (typeof fecha === 'string') {
        fechaObj = new Date(fecha);
      } 
      // Caso 2: Array [año, mes, día, hora, minuto, segundo]
      else if (Array.isArray(fecha) && fecha.length >= 3) {
        // LocalDateTime viene como [año, mes, día, hora, min, seg]
        fechaObj = new Date(fecha[0], fecha[1] - 1, fecha[2]);
      } 
      // Caso 3: Objeto Date
      else if (fecha instanceof Date) {
        fechaObj = fecha;
      } 
      // Caso 4: Objeto con propiedades
      else if (typeof fecha === 'object' && fecha !== null) {
        if (fecha.year && fecha.month && fecha.dayOfMonth) {
          fechaObj = new Date(fecha.year, fecha.month - 1, fecha.dayOfMonth);
        } else {
          fechaObj = new Date(fecha);
        }
      } 
      else {
        return 'Fecha inválida';
      }

      // Verificar validez
      if (isNaN(fechaObj.getTime())) {
        console.warn('Fecha inválida:', fecha);
        return 'Fecha inválida';
      }

      return fechaObj.toLocaleDateString('es-PE', {
        year: 'numeric',
        month: 'short',
        day: '2-digit'
      });
    } catch (error) {
      console.error('Error al formatear fecha:', fecha, error);
      return 'Fecha inválida';
    }
  }
}