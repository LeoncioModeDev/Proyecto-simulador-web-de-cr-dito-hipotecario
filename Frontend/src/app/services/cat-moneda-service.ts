import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { CatMoneda } from '../models/cat-moneda';

@Injectable({
  providedIn: 'root'
})
export class CatMonedaService {
  ruta_servidor = environment.apiUrl;
  recurso = 'moneda';

  constructor(private http: HttpClient) {}

  listarTodo() {
    return this.http.get<CatMoneda[]>(`${this.ruta_servidor}/${this.recurso}`);
  }

  listarPorId(id: number) {
    return this.http.get<CatMoneda>(`${this.ruta_servidor}/${this.recurso}/${id}`);
  }
}
