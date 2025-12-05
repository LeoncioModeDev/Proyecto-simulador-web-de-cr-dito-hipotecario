import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { IFI } from '../models/ifi';

@Injectable({
  providedIn: 'root'
})
export class IFIService {
  ruta_servidor = environment.apiUrl;
  recurso = 'entidadesfinancieras';

  constructor(private http: HttpClient) {}

  listarTodo() {
    return this.http.get<IFI[]>(`${this.ruta_servidor}/${this.recurso}`);
  }

  listarPorId(id: number) {
    return this.http.get<IFI>(`${this.ruta_servidor}/${this.recurso}/${id}`);
  }

  new(ifi: IFI) {
    return this.http.post(`${this.ruta_servidor}/${this.recurso}`, ifi, {
      responseType: 'text'
    });
  }

  modificar(ifi: IFI) {
    return this.http.put(`${this.ruta_servidor}/${this.recurso}`, ifi, {
      responseType: 'text'
    });
  }

  eliminar(id: number) {
    return this.http.delete(`${this.ruta_servidor}/${this.recurso}/${id}`, {
      responseType: 'text'
    });
  }
}
