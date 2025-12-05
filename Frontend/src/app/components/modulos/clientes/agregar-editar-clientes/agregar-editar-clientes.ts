import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Cliente } from '../../../../models/cliente';
import { ClienteService } from '../../../../services/cliente-service';

@Component({
  selector: 'app-agregar-editar-clientes',
  standalone: false,
  templateUrl: './agregar-editar-clientes.html',
  styleUrl: './agregar-editar-clientes.css',
})
export class AgregarEditarClientes {
  clienteId: number = 0;
  formulario!: FormGroup;

  estadosCiviles: string[] = [
    'Soltero',
    'Casado',
    'Divorciado',
    'Viudo',
    'Conviviente'
  ];

  residencias: string[] = [
    'Lima Metropolitana',
    'Lima - Miraflores',
    'Lima - San Isidro',
    'Lima - San Juan de Lurigancho',
    'Lima - Comas',
    'Lima - Villa El Salvador',
    'Distrito de Arequipa',
    'Distrito de Trujillo',
    'Distrito de Cusco',
    'Otro distrito de Perú',
    'Fuera del país'
  ];

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private clienteService: ClienteService
  ) {}

  ngOnInit() {
    const idParam = this.activatedRoute.snapshot.paramMap.get('id');
    this.clienteId = idParam ? Number(idParam) : 0;
    this.cargarFormulario();
  }

  cargarFormulario() {
    this.formulario = this.formBuilder.group({
      dni: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(8),
          Validators.pattern(/^[0-9]+$/)
        ]
      ],
      nombres: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100),
          Validators.pattern(/^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$/)
        ]
      ],
      apellidos: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100),
          Validators.pattern(/^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$/)
        ]
      ],
      correo: [
        '',
        [
          Validators.required,
          Validators.email
        ]
      ],
      telefono: [
        '',
        [
          Validators.required,
          Validators.minLength(9),
          Validators.maxLength(9),
          Validators.pattern(/^[0-9]+$/)
        ]
      ],
      direccion: [
        '',
        [
          Validators.required,
          Validators.maxLength(150)
        ]
      ],
      ingreso_mensual: [
        null,
        [
          Validators.required,
          Validators.min(0)
        ]
      ],
      estado_civil: [
        '',
        [Validators.required]
      ],
      residencia_actual: [
        '',
        [Validators.required]
      ],
      dependientes: [
        '',
        [
          Validators.required,
          Validators.pattern(/^[0-9]+$/)
        ]
      ]
    });

    if (this.clienteId > 0) {
      this.clienteService.listarPorId(this.clienteId).subscribe({
        next: (data: Cliente) => {
          this.formulario.patchValue({
            dni: data.dni,
            nombres: data.nombres,
            apellidos: data.apellidos,
            correo: data.correo,
            telefono: data.telefono,
            direccion: data.direccion,
            ingreso_mensual: data.ingreso_mensual,
            estado_civil: data.estado_civil,
            residencia_actual: data.residencia_actual,
            dependientes: data.dependientes
          });
        },
        error: (err) => {
          console.error('Error al obtener cliente', err);
        }
      });
    }
  }

  grabarCliente() {
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

    const cliente: Cliente = {
      cliente_id: this.clienteId > 0 ? this.clienteId : 0,
      ...this.formulario.value
    };

    if (this.clienteId > 0) {
      this.clienteService.modificar(cliente).subscribe({
        next: () => {
          console.log('Cliente actualizado');
          this.router.navigate(['/home/clientes']);
        },
        error: (err) => {
          console.error('Error al actualizar cliente', err);
        }
      });
    } else {
      this.clienteService.new(cliente).subscribe({
        next: () => {
          console.log('Cliente registrado');
          this.router.navigate(['/home/clientes']);
        },
        error: (err) => {
          console.error('Error al registrar cliente', err);
        }
      });
    }
  }
}