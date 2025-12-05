import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { CatTipoGracia } from '../models/cat-tipo-gracia';

@Injectable({
  providedIn: 'root'
})
export class CatTipoGraciaService {
  ruta_servidor = environment.apiUrl;
  recurso = 'cattipogracia';

  constructor(private http: HttpClient) {}

  listarTodo() {
    return this.http.get<CatTipoGracia[]>(`${this.ruta_servidor}/${this.recurso}`);
  }

  listarPorId(id: number) {
    return this.http.get<CatTipoGracia>(`${this.ruta_servidor}/${this.recurso}/${id}`);
  }
}