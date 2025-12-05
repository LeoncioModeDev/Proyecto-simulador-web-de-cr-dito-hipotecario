import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatDialogModule } from '@angular/material/dialog';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Registro } from './components/autenticacion/registro/registro';
import { Logeo } from './components/autenticacion/logeo/logeo';
import { MaterialModule } from './modules/material/material-module';
import { BotonPrincipal } from './components/reutilizables/boton-principal/boton-principal';
import { Home } from './components/modulos/home/home';
import { AgregarEditarClientes } from './components/modulos/clientes/agregar-editar-clientes/agregar-editar-clientes';
import { AgregarEditarInmuebles } from './components/modulos/inmuebles/agregar-editar-inmuebles/agregar-editar-inmuebles';
import { VerClientes } from './components/modulos/clientes/ver-clientes/ver-clientes';
import { VerInmuebles } from './components/modulos/inmuebles/ver-inmuebles/ver-inmuebles';
import { Dashboard } from './components/modulos/dashboard/dashboard';
import { SimulacionCredito } from './components/modulos/credito/simulacion-credito/simulacion-credito';
import { Configuracion } from './components/modulos/configuracion/configuracion';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PlanPagos } from './components/modulos/credito/plan-pagos/plan-pagos';
import { ConfirmarEliminar } from './components/reutilizables/confirmar-eliminar/confirmar-eliminar';
import { AgregarEditarIfis } from './components/modulos/ifis/agregar-editar-ifis/agregar-editar-ifis';
import { VerIfis } from './components/modulos/ifis/ver-ifis/ver-ifis';
import { Buscador } from './components/reutilizables/buscador/buscador';
import { jwtInterceptor } from './interceptors/jwt-interceptor';

@NgModule({
  declarations: [
    App,
    Registro,
    Logeo,
    BotonPrincipal,
    Home,
    AgregarEditarClientes,
    AgregarEditarInmuebles,
    VerClientes,
    VerInmuebles,
    Dashboard,
    SimulacionCredito,
    Configuracion,
    PlanPagos,
    ConfirmarEliminar,
    AgregarEditarIfis,
    VerIfis,
    Buscador
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    HttpClientModule,
    MatDialogModule
  ],
  providers: [
    provideHttpClient(
      withInterceptors([
        jwtInterceptor
      ])
    )
  ],
  bootstrap: [App]
})
export class AppModule { }