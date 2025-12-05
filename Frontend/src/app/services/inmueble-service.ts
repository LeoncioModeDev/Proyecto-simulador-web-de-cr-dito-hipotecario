import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Inmueble } from '../models/inmueble';

@Injectable({
  providedIn: 'root'
})
export class InmuebleService {
  ruta_servidor = environment.apiUrl;
  recurso = 'inmuebles';

  constructor(private http: HttpClient) { }

  listarTodo() {
    return this.http.get<Inmueble[]>(`${this.ruta_servidor}/${this.recurso}`);
  }

  listarPorId(id: number) {
    return this.http.get<Inmueble>(`${this.ruta_servidor}/${this.recurso}/${id}`);
  }

  new(inmueble: Inmueble) {
    return this.http.post(`${this.ruta_servidor}/${this.recurso}`, inmueble, {
      responseType: 'text'
    });
  }

  modificar(inmueble: Inmueble) {
    return this.http.put(`${this.ruta_servidor}/${this.recurso}`, inmueble, {
      responseType: 'text'
    });
  }

  eliminar(id: number) {
    return this.http.delete(`${this.ruta_servidor}/${this.recurso}/${id}`, {
      responseType: 'text'
    });
  }
}
