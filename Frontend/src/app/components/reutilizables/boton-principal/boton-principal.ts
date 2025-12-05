import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-boton-principal',
  standalone: false,
  templateUrl: './boton-principal.html',
  styleUrl: './boton-principal.css',
})
export class BotonPrincipal {
  @Input() texto: string = 'Botón';
  @Input() padding: string = '10px 36px';
  @Input() fondo: string = 'var(--color-azul-principal)';
  @Input() colorTexto: string = 'var(--color-blanco)';
  @Input() disabled: boolean = false;
}