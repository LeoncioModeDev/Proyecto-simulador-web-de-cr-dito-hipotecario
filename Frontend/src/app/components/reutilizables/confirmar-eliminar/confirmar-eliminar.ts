import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-confirmar-eliminar',
  standalone: false,
  templateUrl: './confirmar-eliminar.html',
  styleUrl: './confirmar-eliminar.css',
})
export class ConfirmarEliminar {

  constructor(private dialogRef: MatDialogRef<ConfirmarEliminar>) {};
  
  cancelar() {
    this.dialogRef.close(false);
  }

  confirmar() {
    this.dialogRef.close(true);
  }
}
