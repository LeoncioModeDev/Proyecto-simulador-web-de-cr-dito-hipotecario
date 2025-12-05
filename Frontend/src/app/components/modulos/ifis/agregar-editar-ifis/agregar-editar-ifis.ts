import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { IFIService } from '../../../../services/ifiservice';
import { IFI } from '../../../../models/ifi';

@Component({
  selector: 'app-agregar-editar-ifis',
  standalone: false,
  templateUrl: './agregar-editar-ifis.html',
  styleUrl: './agregar-editar-ifis.css',
})
export class AgregarEditarIfis {
  ifiId: number = 0;
  formulario!: FormGroup;

  // opciones para selects
  tiposTasaBase: string[] = ['EFECTIVA', 'NOMINAL'];
  monedas: string[] = ['PEN', 'USD', 'PEN/USD'];

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private ifiService: IFIService
  ) {}

  ngOnInit(): void {
    this.cargarFormulario();
  }

  cargarFormulario(): void {
    this.formulario = this.formBuilder.group({
      // Datos generales
      nombre: [
        '',
        [Validators.required, Validators.minLength(3), Validators.maxLength(100)]
      ],
      telefono: [
        '',
        [
          Validators.required,
          Validators.minLength(9),
          Validators.maxLength(15),
          Validators.pattern(/^[0-9()+\- ]+$/)
        ]
      ],
      direccion: [
        '',
        [Validators.required, Validators.maxLength(100)]
      ],

      // Tasas y plazos
      tasa_nominal_anual: [
        null,
        [Validators.required, Validators.min(0)]
      ],
      tasa_efectiva_anual: [
        null,
        [Validators.required, Validators.min(0)]
      ],
      tipo_tasa_base: [
        null,
        [Validators.required]
      ],
      capitalizacion_m: [
        12,
        [Validators.required, Validators.min(1)]
      ],
      plazo_min_meses: [
        60,
        [Validators.required, Validators.min(1)]
      ],
      plazo_max_meses: [
        300,
        [Validators.required, Validators.min(1)]
      ],
      porcentaje_cuota_ini_min: [
        null,
        [Validators.required, Validators.min(0), Validators.max(100)]
      ],
      monto_min_credito: [
        null,
        [Validators.required, Validators.min(0)]
      ],
      monto_max_credito: [
        null,
        [Validators.required, Validators.min(0)]
      ],

      // Costos iniciales
      costo_notarial: [
        null,
        [Validators.required, Validators.min(0)]
      ],
      costo_registral: [
        null,
        [Validators.required, Validators.min(0)]
      ],
      costo_tasacion: [
        null,
        [Validators.required, Validators.min(0)]
      ],
      costo_estudio_titulos: [
        null,
        [Validators.required, Validators.min(0)]
      ],
      comision_activacion: [
        0,
        [Validators.required, Validators.min(0)]
      ],

      // Costos periódicos
      comision_periodica_mensual: [
        null,
        [Validators.required, Validators.min(0)]
      ],
      tasa_seguro_desgravamen: [
        null,
        [Validators.required, Validators.min(0)]
      ],
      tasa_seguro_riesgo: [
        null,
        [Validators.required, Validators.min(0)]
      ],

      // Otros
      permite_bfh: [
        false,
        [Validators.required]
      ],
      monedas_permitidas: [
        null,
        [Validators.required]
      ]
    });

    const paramId = this.activatedRoute.snapshot.params['id'];

    if (paramId) {
      this.ifiId = parseInt(paramId, 10);

      if (this.ifiId > 0) {
        this.ifiService.listarPorId(this.ifiId).subscribe({
          next: (data: IFI) => {
            this.formulario.patchValue({
              nombre: data.nombre,
              telefono: data.telefono,
              direccion: data.direccion,
              tasa_nominal_anual: data.tasa_nominal_anual,
              tasa_efectiva_anual: data.tasa_efectiva_anual,
              tipo_tasa_base: data.tipo_tasa_base,
              capitalizacion_m: data.capitalizacion_m,
              plazo_min_meses: data.plazo_min_meses,
              plazo_max_meses: data.plazo_max_meses,
              porcentaje_cuota_ini_min: data.porcentaje_cuota_ini_min,
              monto_min_credito: data.monto_min_credito,
              monto_max_credito: data.monto_max_credito,
              costo_notarial: data.costo_notarial,
              costo_registral: data.costo_registral,
              costo_tasacion: data.costo_tasacion,
              costo_estudio_titulos: data.costo_estudio_titulos,
              comision_activacion: data.comision_activacion,
              comision_periodica_mensual: data.comision_periodica_mensual,
              tasa_seguro_desgravamen: data.tasa_seguro_desgravamen,
              tasa_seguro_riesgo: data.tasa_seguro_riesgo,
              permite_bfh: data.permite_bfh,
              monedas_permitidas: data.monedas_permitidas
            });
          },
          error: (err) => {
            console.error('Error al obtener entidad financiera', err);
          }
        });
      }
    } else {
      this.ifiId = 0;
    }
  }

  grabarIfi(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const ifi: IFI = {
      ifi_id: this.ifiId ? this.ifiId : 0,
      ...this.formulario.value
    };

    if (this.ifiId && this.ifiId > 0) {
      this.ifiService.modificar(ifi).subscribe({
        next: () => this.router.navigate(['/home/ifis']),
        error: (err) => console.error('Error al actualizar entidad financiera', err)
      });
    } else {
      this.ifiService.new(ifi).subscribe({
        next: () => this.router.navigate(['/home/ifis']),
        error: (err) => console.error('Error al registrar entidad financiera', err)
      });
    }
  }
}
