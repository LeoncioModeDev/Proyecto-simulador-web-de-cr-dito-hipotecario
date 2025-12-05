import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Cliente } from '../models/cliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  ruta_servidor = environment.apiUrl;
  recurso = 'clientes'

  constructor(private http: HttpClient) { }

  listarTodo(){
    return this.http.get<Cliente[]>(this.ruta_servidor + "/" + this.recurso);
  }

  listarPorId(id: number) {
    return this.http.get<Cliente>(this.ruta_servidor + "/" + this.recurso + "/" + id.toString());
  }

  eliminar(id: number){
    return this.http.delete<Cliente>(this.ruta_servidor + "/" + this.recurso + "/" + id.toString(), { responseType: 'text' as 'json' });
  }

  new(cliente: Cliente) {
    return this.http.post<Cliente>(this.ruta_servidor + "/" + this.recurso, cliente, { responseType: 'text' as 'json' });
  }

  modificar(cliente: Cliente) {
    return this.http.put<Cliente>(this.ruta_servidor + "/" + this.recurso, cliente, { responseType: 'text' as 'json' });
  }
}
