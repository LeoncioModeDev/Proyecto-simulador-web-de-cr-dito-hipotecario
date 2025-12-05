import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Usuario } from '../../../models/usuario';
import { AuthService } from '../../../services/auth-service';
import { UsuarioService } from '../../../services/usuario-service';

@Component({
  selector: 'app-configuracion',
  standalone: false,
  templateUrl: './configuracion.html',
  styleUrl: './configuracion.css',
})
export class Configuracion {
  formularioPerfil!: FormGroup;
  usuario: Usuario | null = null;
  cargando = false;
  mensajeExito = '';
  mensajeError = '';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    this.cargarDatosUsuario();
    this.crearFormulario();
  }

  /**
   * Cargar datos del usuario logueado
   */
  cargarDatosUsuario(): void {
    this.usuario = this.authService.obtenerUsuarioActual();

    if (!this.usuario) {
      console.error('No hay usuario logueado');
      this.router.navigate(['/login']);
    }
  }

  /**
   * Crear formulario con validadores
   */
  crearFormulario(): void {
    this.formularioPerfil = this.formBuilder.group(
      {
        email: [this.usuario?.email || '', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', [Validators.required]],
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

  /**
   * Guardar cambios del perfil
   */
  grabarConfiguracion(): void {
    if (this.formularioPerfil.invalid || !this.usuario) {
      this.mensajeError = 'Por favor, completa todos los campos correctamente';
      return;
    }

    this.cargando = true;
    this.mensajeError = '';
    this.mensajeExito = '';

    // Crear objeto Usuario actualizado
    const usuarioActualizado: Usuario = {
      user_id: this.usuario.user_id,
      username: this.usuario.username,
      email: this.formularioPerfil.get('email')?.value,
      password: this.formularioPerfil.get('password')?.value
    };

    console.log('Enviando datos:', usuarioActualizado);

    // Llamar al servicio para actualizar
    this.usuarioService.modificar(usuarioActualizado).subscribe(
      (respuesta) => {
        console.log('Respuesta del servidor:', respuesta);
        this.cargando = false;
        this.mensajeExito = '✅ Perfil actualizado con éxito';
        
        // Actualizar usuario en memoria (sin password)
        const usuarioActualizadoLocal: Usuario = {
          user_id: this.usuario!.user_id,
          username: this.usuario!.username,
          email: this.formularioPerfil.get('email')?.value
        };
        this.usuario = usuarioActualizadoLocal;
        localStorage.setItem('usuario', JSON.stringify(usuarioActualizadoLocal));
        
        // Limpiar formulario después de 2 segundos
        setTimeout(() => {
          this.formularioPerfil.reset({
            email: this.usuario?.email
          });
          this.mensajeExito = '';
        }, 2000);
      },
      (error) => {
        console.error('Error al actualizar el perfil:', error);
        this.cargando = false;
        
        // Manejar diferentes tipos de errores
        if (error.status === 404) {
          this.mensajeError = 'Usuario no encontrado';
        } else if (error.status === 400) {
          this.mensajeError = error.error || 'Datos inválidos';
        } else if (error.status === 0) {
          this.mensajeError = 'Error de conexión. Verifica que el servidor esté activo';
        } else {
          this.mensajeError = error.error || 'Error al actualizar el perfil. Intenta de nuevo.';
        }
      }
    );
  }

  /**
   * Logout - Cerrar sesión
   */
  logout(): void {
    if (confirm('¿Estás seguro de que deseas cerrar sesión?')) {
      this.authService.logout();
    }
  }
}
