import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Usuario } from '../../../models/usuario';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-registro',
  standalone: false,
  templateUrl: './registro.html',
  styleUrl: './registro.css',
})
export class Registro {
  formulario!: FormGroup;
  cargando = false;
  mensajeError = '';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.crearFormulario();

    // Si ya está logueado, redirige a home
    if (this.authService.estaLogueado()) {
      this.router.navigate(['/home/dashboard']);
    }
  }

  crearFormulario(): void {
    this.formulario = this.formBuilder.group(
      {
        username: ['', [Validators.required, Validators.minLength(3)]],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', [Validators.required]]
      },
      {
        validators: this.passwordMatchValidator
      }
    );
  }

  /**
   * Validador personalizado para verificar que las contraseñas coincidan
   */
  passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password');
    const confirmPassword = control.get('confirmPassword');

    if (!password || !confirmPassword) {
      return null;
    }

    return password.value === confirmPassword.value ? null : { passwordMismatch: true };
  }

  registrar(): void {
    if (this.formulario.invalid) {
      alert('Por favor, completa todos los campos correctamente');
      return;
    }

    this.cargando = true;
    this.mensajeError = '';

    const nuevoUsuario: Usuario = {
      user_id: 0,
      username: this.formulario.get('username')?.value,
      email: this.formulario.get('email')?.value,
      password: this.formulario.get('password')?.value
    } as any;

    this.authService.registro(nuevoUsuario).subscribe(
      (response) => {
        console.log('Registro exitoso:', response);
        this.cargando = false;
        this.router.navigate(['/home/dashboard']);
      },
      (error) => {
        console.error('Error en registro:', error);
        this.cargando = false;
        this.mensajeError = error.error || 'Error al registrarse. Intenta de nuevo.';
      }
    );
  }
}
