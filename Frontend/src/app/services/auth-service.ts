import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment.development';
import { Usuario, LoginRequest, LoginResponse } from '../models/usuario';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl + '/auth';

  private usuarioActualSubject = new BehaviorSubject<Usuario | null>(null);
  public usuarioActual$ = this.usuarioActualSubject.asObservable();

  private tokenSubject = new BehaviorSubject<string | null>(null);
  public token$ = this.tokenSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {
    this.cargarDatosGuardados();
  }

  /**
   * Cargar datos guardados en localStorage
   */
  private cargarDatosGuardados(): void {
    const token = localStorage.getItem('token');
    const usuario = localStorage.getItem('usuario');

    if (token) {
      this.tokenSubject.next(token);
    }

    if (usuario) {
      try {
        const usuarioObj = JSON.parse(usuario);
        this.usuarioActualSubject.next(usuarioObj);
      } catch (error) {
        console.error('Error al cargar usuario guardado:', error);
      }
    }
  }

  /**
   * Login del usuario
   */
  login(credenciales: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credenciales).pipe(
      tap(response => {
        this.guardarSesion(response.token, response.usuario);
      }),
      catchError(error => {
        console.error('Error en login:', error);
        return throwError(() => error.error || error);
      })
    );
  }

  /**
   * Registro de nuevo usuario
   */
  registro(usuario: Usuario): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/registro`, usuario).pipe(
      tap(response => {
        this.guardarSesion(response.token, response.usuario);
      }),
      catchError(error => {
        console.error('Error en registro:', error);
        return throwError(() => error.error || error);
      })
    );
  }

  /**
   * Guardar sesión (token y usuario)
   */
  private guardarSesion(token: string, usuario: Usuario): void {
    localStorage.setItem('token', token);
    localStorage.setItem('usuario', JSON.stringify(usuario));
    this.tokenSubject.next(token);
    this.usuarioActualSubject.next(usuario);
  }

  /**
   * Obtener usuario actual
   */
  obtenerUsuarioActual(): Usuario | null {
    return this.usuarioActualSubject.value;
  }

  /**
   * Obtener token actual
   */
  obtenerToken(): string | null {
    return this.tokenSubject.value;
  }

  /**
   * Verificar si está logueado
   */
  estaLogueado(): boolean {
    return !!this.tokenSubject.value;
  }

  /**
   * Logout
   */
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('usuario');
    this.tokenSubject.next(null);
    this.usuarioActualSubject.next(null);
    this.router.navigate(['/login']);
  }

  /**
   * Obtener token con Bearer
   */
  obtenerTokenConBearer(): string | null {
    const token = this.obtenerToken();
    return token ? `Bearer ${token}` : null;
  }
}