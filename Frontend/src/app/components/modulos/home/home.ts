import { Component } from '@angular/core';
import { Usuario } from '../../../models/usuario';
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  usuario: Usuario | null = null;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // Cargar datos del usuario actual
    this.usuario = this.authService.obtenerUsuarioActual();

    // Si no hay usuario, algo está mal
    if (!this.usuario) {
      console.warn('No hay usuario logueado');
    } else {
      console.log('Usuario cargado:', this.usuario.username);
    }
  }

  /**
   * Logout - Cierra la sesión y redirige a login
   */
  logout(): void {
    if (confirm('¿Estás seguro de que deseas cerrar sesión?')) {
      this.authService.logout();
    }
  }
}
