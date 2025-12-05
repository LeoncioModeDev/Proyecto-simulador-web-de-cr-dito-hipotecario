import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { CatTipoTasa } from '../models/cat-tipo-tasa';

@Injectable({
  providedIn: 'root'
})
export class CatTipoTasaService {
  ruta_servidor = environment.apiUrl;
  recurso = 'catTipoTasa';

  constructor(private http: HttpClient) {}

  listarTodo() {
    return this.http.get<CatTipoTasa[]>(`${this.ruta_servidor}/${this.recurso}`);
  }

  listarPorId(id: number) {
    return this.http.get<CatTipoTasa>(`${this.ruta_servidor}/${this.recurso}/${id}`);
  }
}
