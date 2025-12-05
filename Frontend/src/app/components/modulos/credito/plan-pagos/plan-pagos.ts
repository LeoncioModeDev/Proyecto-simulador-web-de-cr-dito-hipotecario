import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { SimulacionCreditoResponse } from '../../../../models/simulacion-credito-response';
import { SimulacionCreditoService } from '../../../../services/simulacion-credito-service';

@Component({
  selector: 'app-plan-pagos',
  standalone: false,
  templateUrl: './plan-pagos.html',
  styleUrls: ['./plan-pagos.css'],
})
export class PlanPagos implements OnInit, OnDestroy {
  simulacionRespuesta: SimulacionCreditoResponse | null = null;
  cargando = false;
  private destroy$ = new Subject<void>();

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private simulacionService: SimulacionCreditoService
  ) {}

  ngOnInit(): void {
    this.simulacionRespuesta = this.simulacionService.getSimulacionActual();

    if (this.simulacionRespuesta) {
      console.log('✅ Datos cargados desde el servicio (memoria)');
      return;
    }

    this.simulacionService.simulacionActual$
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (data) => {
          if (data && !this.simulacionRespuesta) {
            this.simulacionRespuesta = data;
            console.log('✅ Datos cargados desde el observable del servicio');
          }
        }
      });

    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam && !this.simulacionRespuesta) {
      const simulacionId = parseInt(idParam, 10);
      console.log('🔄 Cargando simulación desde backend, ID:', simulacionId);
      this.cargarSimulacion(simulacionId);
      return;
    }

    if (!this.simulacionRespuesta) {
      console.warn('⚠️ No hay datos disponibles. Redirigiendo al formulario...');
      setTimeout(() => {
        this.router.navigate(['/home/simulacion']);
      }, 100);
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  cargarSimulacion(id: number): void {
    this.cargando = true;
    this.simulacionService.obtenerSimulacionCompleta(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response) => {
          this.simulacionRespuesta = response;
          this.cargando = false;
          console.log('✅ Simulación cargada desde backend');
        },
        error: (err) => {
          console.error('❌ Error al cargar la simulación:', err);
          this.cargando = false;
          alert('No se pudo cargar la simulación. Redirigiendo al formulario...');
          this.router.navigate(['/home/simulacion']);
        }
      });
  }

  volver(): void {
    this.simulacionService.limpiarSimulacionActual();
    this.router.navigate(['/home/simulacion']);
  }

  exportarCSV(): void {
    if (this.simulacionRespuesta?.simulacion.simulacion_id) {
      this.simulacionService.exportarPlanPagosCSV(
        this.simulacionRespuesta.simulacion.simulacion_id
      );
      console.log('📄 Exportando CSV de la simulación');
    } else {
      alert('No hay datos para exportar');
    }
  }
}