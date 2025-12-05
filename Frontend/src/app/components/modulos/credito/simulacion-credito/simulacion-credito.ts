import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { SimulacionCreditoRequest } from '../../../../models/simulacion-credito-request';
import { Cliente } from '../../../../models/cliente';
import { Inmueble } from '../../../../models/inmueble';
import { IFI } from '../../../../models/ifi';
import { CatMoneda } from '../../../../models/cat-moneda';
import { CatTipoTasa } from '../../../../models/cat-tipo-tasa';
import { CatTipoGracia } from '../../../../models/cat-tipo-gracia';

import { ClienteService } from '../../../../services/cliente-service';
import { InmuebleService } from '../../../../services/inmueble-service';
import { IFIService } from '../../../../services/ifiservice';
import { SimulacionCreditoService } from '../../../../services/simulacion-credito-service';
import { CatMonedaService } from '../../../../services/cat-moneda-service';
import { CatTipoTasaService } from '../../../../services/cat-tipo-tasa-service';
import { CatTipoGraciaService } from '../../../../services/cat-tipo-gracia-service';

@Component({
  selector: 'app-simulacion-credito',
  standalone: false,
  templateUrl: './simulacion-credito.html',
  styleUrls: ['./simulacion-credito.css'],
})
export class SimulacionCredito implements OnInit {
  formulario!: FormGroup;

  clientes: Cliente[] = [];
  inmuebles: Inmueble[] = [];
  ifis: IFI[] = [];

  monedas: CatMoneda[] = [];
  tiposTasa: CatTipoTasa[] = [];
  tiposGracia: CatTipoGracia[] = [];

  // Flags de UI
  verificando = false;
  calculando = false;
  mensajeVerificacion = '';
  creditoElegible: boolean | null = null;
  elegibleBfh: boolean | null = null;  // ✅ NUEVO: Rastrear elegibilidad BFH
  intentoSinVerificar = false;
  
  // Control de visibilidad
  mostrarBfh = false;
  mostrarBotonCalcular = false;
  capitalizacionDisabled = false;  // ✅ NUEVO: Deshabilitar capitalización

  // ✅ NUEVO: Datos de BFH para mostrar
  montoBfhDisponible: number | null = null;
  tramoBfh: string = '';

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private clienteService: ClienteService,
    private inmuebleService: InmuebleService,
    private ifiService: IFIService,
    private catMonedaService: CatMonedaService,
    private catTipoTasaService: CatTipoTasaService,
    private catTipoGraciaService: CatTipoGraciaService,
    private simulacionService: SimulacionCreditoService
  ) {}

  ngOnInit(): void {
    this.crearFormulario();
    this.cargarCombos();
  }

  private crearFormulario(): void {
    this.formulario = this.fb.group({
      // Datos base
      cliente_id: [null, [Validators.required]],
      inmueble_id: [null, [Validators.required]],
      ifi_id: [null, [Validators.required]],

      moneda_id: [null, [Validators.required]],
      cuota_inicial_pct: [
        10,
        [Validators.required, Validators.min(0), Validators.max(80)],
      ],
      plazo_meses: [180, [Validators.required, Validators.min(12)]],

      tipo_tasa_id: [null, [Validators.required]],
      tasa_anual_ingresada: [12, [Validators.required, Validators.min(0)]],
      capitalizacion_m: [12, [Validators.required, Validators.min(1)]],

      tipo_gracia_id: [null, [Validators.required]],
      meses_gracia: [0, [Validators.required, Validators.min(0)]],

      // COSTOS INICIALES
      costo_notarial: [0, [Validators.min(0)]],
      costo_registral: [0, [Validators.min(0)]],
      costo_tasacion: [0, [Validators.min(0)]],
      costo_estudio_titulos: [0, [Validators.min(0)]],
      comision_activacion: [0, [Validators.min(0)]],
      gastos_adicionales: [0, [Validators.min(0)]],

      // COSTOS PERIÓDICOS
      comision_periodica_mensual: [0, [Validators.min(0)]],
      portes_mensuales: [0, [Validators.min(0)]],
      gastos_administracion_mensual: [0, [Validators.min(0)]],
      tasa_seguro_desgravamen: [0, [Validators.min(0)]],
      tasa_seguro_riesgo: [0, [Validators.min(0)]],

      // BFH: deshabilitado hasta verificar crédito
      aplica_bfh: [{ value: false, disabled: true }],
      bfh_monto: [{ value: null, disabled: true }, [Validators.min(0)]],
    });

    // ✅ MEJORADO: Habilitar/deshabilitar capitalización según tipo de tasa
    this.formulario.get('tipo_tasa_id')?.valueChanges.subscribe((tipoId) => {
      const tipo = this.tiposTasa.find((t) => t.tipo_tasa_id === tipoId);
      const ctrlCap = this.formulario.get('capitalizacion_m');

      if (tipo && tipo.codigo?.toUpperCase() === 'EFECTIVA') {
        ctrlCap?.disable();
        ctrlCap?.setValue(null);
        this.capitalizacionDisabled = true;
      } else {
        ctrlCap?.enable();
        ctrlCap?.setValue(12);  // Mensual por defecto
        this.capitalizacionDisabled = false;
      }
    });

    // Slide de BFH
    this.formulario.get('aplica_bfh')?.valueChanges.subscribe((aplica) => {
      const ctrl = this.formulario.get('bfh_monto');
      if (aplica) {
        ctrl?.enable();
        // ✅ Precargar monto disponible si existe
        if (this.montoBfhDisponible) {
          ctrl?.setValue(this.montoBfhDisponible);
        }
      } else {
        ctrl?.disable();
        ctrl?.reset();
      }
    });
  }

  private cargarCombos(): void {
    this.clienteService.listarTodo().subscribe({
      next: (data) => (this.clientes = data),
      error: (err) => console.error(err),
    });

    this.inmuebleService.listarTodo().subscribe({
      next: (data) => (this.inmuebles = data),
      error: (err) => console.error(err),
    });

    this.ifiService.listarTodo().subscribe({
      next: (data) => (this.ifis = data),
      error: (err) => console.error(err),
    });

    this.catMonedaService.listarTodo().subscribe({
      next: (data) => (this.monedas = data),
      error: (err) => console.error(err),
    });

    this.catTipoTasaService.listarTodo().subscribe({
      next: (data) => (this.tiposTasa = data),
      error: (err) => console.error(err),
    });

    this.catTipoGraciaService.listarTodo().subscribe({
      next: (data) => (this.tiposGracia = data),
      error: (err) => console.error(err),
    });
  }

  private buildRequest(): SimulacionCreditoRequest {
    const v = this.formulario.getRawValue();

    const request: SimulacionCreditoRequest = {
      cliente_id: v.cliente_id,
      inmueble_id: v.inmueble_id,
      ifi_id: v.ifi_id,
      moneda_id: v.moneda_id,
      tipo_tasa_id: v.tipo_tasa_id,
      tipo_gracia_id: v.tipo_gracia_id,

      cuota_inicial_pct: v.cuota_inicial_pct,
      plazo_meses: v.plazo_meses,
      gastos_adicionales: v.gastos_adicionales || 0,
      tasa_anual_ingresada: v.tasa_anual_ingresada,

      capitalizacion_m: v.capitalizacion_m ?? 12,
      meses_gracia: v.meses_gracia,

      aplica_bfh: v.aplica_bfh ?? false,
      bfh_monto: v.aplica_bfh ? v.bfh_monto : null,

      costo_notarial: v.costo_notarial || 0,
      costo_registral: v.costo_registral || 0,
      costo_tasacion: v.costo_tasacion || 0,
      costo_estudio_titulos: v.costo_estudio_titulos || 0,
      comision_activacion: v.comision_activacion || 0,

      comision_periodica_mensual: v.comision_periodica_mensual || 0,
      portes_mensuales: v.portes_mensuales || 0,
      gastos_administracion_mensual: v.gastos_administracion_mensual || 0,
      tasa_seguro_desgravamen: v.tasa_seguro_desgravamen || 0,
      tasa_seguro_riesgo: v.tasa_seguro_riesgo || 0,
    };

    return request;
  }

  // 🔹 Botón "Verificar crédito"
  verificarCredito(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const request = this.buildRequest();
    this.verificando = true;
    this.mensajeVerificacion = '';
    this.creditoElegible = null;
    this.elegibleBfh = null;
    this.intentoSinVerificar = false;
    this.montoBfhDisponible = null;
    this.tramoBfh = '';

    this.simulacionService.verificarCredito(request).subscribe({
      next: (resp) => {
        this.verificando = false;
        this.creditoElegible = resp.elegibleCredito;
        this.elegibleBfh = resp.elegibleBfh;
        this.mensajeVerificacion = resp.mensaje;

        console.log('Respuesta verificación:', resp);

        // Si es elegible, mostrar opciones
        if (resp.elegibleCredito) {
          this.mostrarBotonCalcular = true;
          
          if (resp.elegibleBfh) {
            this.mostrarBfh = true;
            this.formulario.get('aplica_bfh')?.enable();
            
            const match = resp.mensaje.match(/S\/\.\s*([\d,.]+)/);
            if (match) {
              const montoStr = match[1].replace(/,/g, '').trim();
              this.montoBfhDisponible = parseFloat(montoStr);
              
              // 🔍 DEBUG: Ver en consola qué monto se extrajo
              console.log('✅ Monto BFH extraído:', this.montoBfhDisponible);
              console.log('📝 Mensaje completo:', resp.mensaje);
            }
            
            // Extrae el tramo (Tramo 1, Tramo 2, etc)
            const tramMatch = resp.mensaje.match(/Tramo\s+(\d+)|Tramo\s+(\d+)\s*-/);
            if (tramMatch) {
              this.tramoBfh = `Tramo ${tramMatch[1] || tramMatch[2]}`;
              console.log('✅ Tramo extraído:', this.tramoBfh);
            }
          } else {
            this.mostrarBfh = false;
            this.formulario.get('aplica_bfh')?.disable();
            this.formulario.get('aplica_bfh')?.setValue(false);
          }
        } else {
          this.mostrarBfh = false;
          this.mostrarBotonCalcular = false;
        }
      },
      error: (err) => {
        this.verificando = false;
        console.error(err);
        this.mensajeVerificacion = 'Ocurrió un error al verificar el crédito.';
        this.mostrarBfh = false;
        this.mostrarBotonCalcular = false;
      },
    });
  }

  // 🔹 Botón "Calcular Plan de Pagos"
  calcularPlanPagos(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    // Verificar si se validó el crédito
    if (this.creditoElegible !== true) {
      this.intentoSinVerificar = true;
      return;
    }

    const request = this.buildRequest();
    this.calculando = true;

    this.simulacionService.simularCredito(request).subscribe({
      next: (resp) => {
        this.calculando = false;
        
        if (!resp || !resp.simulacion) {
          console.error('La API devolvió una respuesta incompleta.');
          alert('Error: No se pudo obtener la simulación del servidor.');
          return;
        }

        // El servicio ya guardó los datos en el subject
        this.router.navigate(['/home/simulacion/plan-pagos']);
      },
      error: (err) => {
        this.calculando = false;
        console.error('Error al calcular plan de pagos:', err);
        
        let mensaje = 'Ocurrió un error al calcular el plan de pagos.';
        if (err.error?.message) {
          mensaje = err.error.message;
        }
        alert(mensaje);
      },
    });
  }

  get f() {
    return this.formulario.controls;
  }

}