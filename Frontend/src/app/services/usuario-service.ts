import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario } from '../models/usuario';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = environment.apiUrl + '/usuarios';

  constructor(private http: HttpClient) { }

  /**
   * Listar todos los usuarios
   */
  listarTodo(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.apiUrl);
  }

  /**
   * Obtener usuario por ID
   */
  listarPorId(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.apiUrl}/${id}`);
  }

  /**
   * Eliminar usuario
   */
  eliminar(id: number): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/${id}`);
  }

  /**
   * Crear nuevo usuario
   */
  nuevo(usuario: Usuario): Observable<string> {
    return this.http.post<string>(this.apiUrl, usuario);
  }

  /**
   * Modificar usuario (actualizar perfil)
   */
  modificar(usuario: Usuario): Observable<string> {
    return this.http.put<string>(this.apiUrl, usuario);
  }
}
