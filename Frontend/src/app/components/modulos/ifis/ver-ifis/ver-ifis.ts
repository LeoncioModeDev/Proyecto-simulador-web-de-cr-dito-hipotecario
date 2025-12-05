import { Component } from '@angular/core';
import { IFI } from '../../../../models/ifi';
import { Router } from '@angular/router';
import { IFIService } from '../../../../services/ifiservice';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmarEliminar } from '../../../reutilizables/confirmar-eliminar/confirmar-eliminar';

@Component({
  selector: 'app-ver-ifis',
  standalone: false,
  templateUrl: './ver-ifis.html',
  styleUrl: './ver-ifis.css',
})
export class VerIfis {
  listaIfis: IFI[] = [];

  constructor(
    private router: Router,
    private ifiService: IFIService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.cargarLista();
  }

  cargarLista() {
    this.ifiService.listarTodo().subscribe({
      next: (data: IFI[]) => {
        this.listaIfis = data;
        console.log(this.listaIfis);
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  irAgregar() {
    this.router.navigate(['/home/ifis/agregar']);
  }

  editarIfi(id: number) {
    this.router.navigate([`/home/ifis/editar/${id}`]);
  }

  borrarIfi(id: number) {
    const dialogRef = this.dialog.open(ConfirmarEliminar);

    dialogRef.afterClosed().subscribe(opcionSeleccionada => {
      if (opcionSeleccionada === true) {
        this.ifiService.eliminar(id).subscribe({
          next: () => {
            this.cargarLista();
          },
          error: (err) => {
            console.log(err);
          }
        });
      }
    });
  }
}
