import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { InmuebleService } from '../../../../services/inmueble-service';
import { Inmueble } from '../../../../models/inmueble';

@Component({
  selector: 'app-agregar-editar-inmuebles',
  standalone: false,
  templateUrl: './agregar-editar-inmuebles.html',
  styleUrl: './agregar-editar-inmuebles.css',
})
export class AgregarEditarInmuebles {
  inmuebleId: number = 0;
  formulario!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private inmuebleService: InmuebleService
  ) {}

  ngOnInit() {
    this.cargarFormulario();
  }

  cargarFormulario() {
    this.formulario = this.formBuilder.group({
      nombre_proyecto: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100)
        ]
      ],
      unidad: [
        '',
        [Validators.required]
      ],
      ubicacion_general: [
        '',
        [Validators.required]
      ],
      ubicacion_especifica: [
        '',
        [Validators.required]
      ],
      ciudad: [
        '',
        [Validators.required]
      ],
      area_m2: [
        null,
        [
          Validators.required,
          Validators.min(1)
        ]
      ],
      precio: [
        null,
        [
          Validators.required,
          Validators.min(0)
        ]
      ]
    });

    this.inmuebleId = parseInt(this.activatedRoute.snapshot.params['id']);

    if (this.inmuebleId > 0) {
      this.inmuebleService.listarPorId(this.inmuebleId).subscribe({
        next: (data: Inmueble) => {
          this.formulario.patchValue({
            nombre_proyecto: data.nombre_proyecto,
            unidad: data.unidad,
            ubicacion_general: data.ubicacion_general,
            ubicacion_especifica: data.ubicacion_especifica,
            ciudad: data.ciudad,
            area_m2: data.area_m2,
            precio: data.precio
          });
        },
        error: (err) => {
          console.error('Error al obtener inmueble', err);
        }
      });
    } else {
      this.inmuebleId = 0;
    }
  }

  grabarInmueble() {
    if (this.formulario.invalid) {
      console.log('Formulario inválido');
      console.log('Errores:', this.formulario.errors);
      Object.keys(this.formulario.controls).forEach(key => {
        const control = this.formulario.get(key);
        if (control && control.invalid) {
          console.log(`${key}:`, control.errors);
        }
      });
      this.formulario.markAllAsTouched();
      return;
    }

    const inmueble: Inmueble = {
      inmueble_id: this.inmuebleId > 0 ? this.inmuebleId : 0,
      ...this.formulario.value
    };

    if (this.inmuebleId > 0) {
      this.inmuebleService.modificar(inmueble).subscribe({
        next: () => {
          console.log('Inmueble actualizado');
          this.router.navigate(['/home/inmuebles']);
        },
        error: (err) => {
          console.error('Error al actualizar inmueble', err);
        }
      });
    } else {
      this.inmuebleService.new(inmueble).subscribe({
        next: () => {
          console.log('Inmueble registrado');
          this.router.navigate(['/home/inmuebles']);
        },
        error: (err) => {
          console.error('Error al registrar inmueble', err);
        }
      });
    }
  }
}