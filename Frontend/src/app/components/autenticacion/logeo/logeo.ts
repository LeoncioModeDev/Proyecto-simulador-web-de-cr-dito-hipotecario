import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequest } from '../../../models/usuario';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-logeo',
  standalone: false,
  templateUrl: './logeo.html',
  styleUrl: './logeo.css',
})
export class Logeo {
  formulario!: FormGroup;
  cargando = false;
  mensajeError = '';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {
    this.crearFormulario();

    // Si ya está logueado, redirige a home
    if (this.authService.estaLogueado()) {
      this.router.navigate(['/home/dashboard']);
    }
  }

  crearFormulario(): void {
    this.formulario = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  login(): void {
    if (this.formulario.invalid) {
      alert('Por favor, completa todos los campos');
      return;
    }

    this.cargando = true;
    this.mensajeError = '';

    const credenciales: LoginRequest = {
      username: this.formulario.get('username')?.value,
      password: this.formulario.get('password')?.value
    };

    this.authService.login(credenciales).subscribe(
      (response) => {
        console.log('Login exitoso:', response);
        this.cargando = false;
        this.router.navigate(['/home/dashboard']);
      },
      (error) => {
        console.error('Error en login:', error);
        this.cargando = false;
        this.mensajeError = error.error || 'Error al iniciar sesión. Intenta de nuevo.';
      }
    );
  }
}
