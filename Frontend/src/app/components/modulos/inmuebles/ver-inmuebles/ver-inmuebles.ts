import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { Inmueble } from '../../../../models/inmueble';
import { InmuebleService } from '../../../../services/inmueble-service';

@Component({
  selector: 'app-ver-inmuebles',
  standalone: false,
  templateUrl: './ver-inmuebles.html',
  styleUrl: './ver-inmuebles.css',
})
export class VerInmuebles {
  dsListaInmuebles = new MatTableDataSource<Inmueble>();
  displayedColumns: string[] = [
    'inmueble_id',
    'nombre_proyecto',
    'ciudad',
    'area_m2',
    'precio',
    'opciones'
  ];

  constructor(
    private inmuebleService: InmuebleService
  ) {}

  ngOnInit() {
    this.cargarLista();
  }

  cargarLista() {
    this.inmuebleService.listarTodo().subscribe({
      next: (data) => {
        this.dsListaInmuebles = new MatTableDataSource(data);
      },
      error: (err) => console.error(err)
    });
  }

  borrarInmueble(id: number) {
    const confirmado = confirm('¿Seguro que deseas eliminar este inmueble?');
    if (!confirmado) return;

    this.inmuebleService.eliminar(id).subscribe({
      next: () => this.cargarLista(),
      error: (err) => console.error('Error al eliminar inmueble', err)
    });
  }

  applyFilter(event: Event) {
    const valor = (event.target as HTMLInputElement).value;
    this.dsListaInmuebles.filter = valor.trim().toLowerCase();
  }
}
