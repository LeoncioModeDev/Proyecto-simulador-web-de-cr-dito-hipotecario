import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-buscador',
  standalone: false,
  templateUrl: './buscador.html',
  styleUrl: './buscador.css',
})
export class Buscador {
  @Input() placeholder: string = 'Buscar...';
  @Output() teclaPresionada = new EventEmitter<Event>();

  onSearch(event: Event) {
    this.teclaPresionada.emit(event);
  }
}
