import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ClienteService } from '../../../../services/cliente-service';
import { MatTableDataSource } from '@angular/material/table';
import { Cliente } from '../../../../models/cliente';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmarEliminar } from '../../../reutilizables/confirmar-eliminar/confirmar-eliminar';

@Component({
  selector: 'app-ver-clientes',
  standalone: false,
  templateUrl: './ver-clientes.html',
  styleUrl: './ver-clientes.css',
})
export class VerClientes {
  dsListaClientes = new MatTableDataSource<Cliente>()
  displayedColumns: string[] = ['cliente_id', 'nombres', 'apellidos', 'ingreso_mensual', 'estado_civil', 'dependientes', 'residencia_actual', 'opciones']

  constructor(private router: Router, private clienteService: ClienteService, private dialog: MatDialog) {}

  ngOnInit() {
    this.cargarLista()
  }

  borrarCliente(id: any) {
    let dialogRef = this.dialog.open(ConfirmarEliminar);
    dialogRef.afterClosed().subscribe(
      opcionSeleccionada => {
        if (opcionSeleccionada==true) {
          this.clienteService.eliminar(id).subscribe({
            next:()=>{
              this.cargarLista();
            },
            error: (err)=>{
              console.log(err);
            }
          })
        }
      }
    )
  }

  cargarLista() {
    this.clienteService.listarTodo().subscribe({
      next: (data: Cliente[]) => {
        this.dsListaClientes = new MatTableDataSource(data)
        console.log(this.dsListaClientes.data)
      },
      error: (err) => {
        console.log(err)
      }
    })
  }

  applyFilter(event: Event) {
    const valor = (event.target as HTMLInputElement).value;
    this.dsListaClientes.filter = valor.trim().toLowerCase();
  }
}
